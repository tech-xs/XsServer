package tech.xs.system.service.impl;

import cn.hutool.core.net.NetUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.AddDomainRecordRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeSubDomainRecordsRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeSubDomainRecordsResponse;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.xs.common.collections.CollectionUtils;
import tech.xs.common.constant.Symbol;
import tech.xs.system.service.SysDomainNameService;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.LinkedHashSet;
import java.util.List;


/**
 * 域名Service实现类
 *
 * @author 沈家文
 * @date 2021/7/23 11:35
 */
@Slf4j
@Service
public class SysDomainNameServiceImpl implements SysDomainNameService {

    @Value("${aliyun.accessKey:}")
    private String accessKey;

    @Value("${aliyun.secretKey:}")
    private String accessSecret;

    @Value("${server.domain.analysis.enable:false}")
    private boolean analysisEnable;


    @Override
    public void setAnalysisIpv6(String rr, String domainName) throws ClientException {
        if (!analysisEnable) {
            log.info("域名解析已禁用");
            return;
        }
        log.info("start 域名解析 rr: " + rr + " domainName:" + domainName);
        String localIpv6 = getLocalIpv6();
        if (localIpv6 == null) {
            log.error("本地IP获取失败");
            return;
        }
        log.info("ipv6: " + localIpv6);
        DescribeSubDomainRecordsResponse.Record record = getAnalysisId(rr, domainName);
        if (record == null) {
            log.info("start 添加域名解析");
            addAnalysis(rr, domainName, localIpv6);
            log.info("end 添加域名解析");
        } else {
            if (localIpv6.equals(record.getValue())) {
                log.info("当前解析值与本地IP一致无需解析");
                return;
            }
            log.info("start 修改域名解析");
            modifyAnalysis(rr, record.getRecordId(), localIpv6);
            log.info("end 修改域名解析");
        }
    }

    private DescribeSubDomainRecordsResponse.Record getAnalysisId(String rr, String domainName) throws ClientException {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKey, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        DescribeSubDomainRecordsRequest request = new DescribeSubDomainRecordsRequest();
        request.setSubDomain(rr + Symbol.POINT + domainName);
        DescribeSubDomainRecordsResponse response = client.getAcsResponse(request);
        List<DescribeSubDomainRecordsResponse.Record> domainRecords = response.getDomainRecords();
        if (CollectionUtils.isEmpty(domainRecords)) {
            return null;
        }
        return domainRecords.get(0);
    }

    private void modifyAnalysis(String rr, String analysisId, String ipv6) throws ClientException {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKey, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        UpdateDomainRecordRequest request = new UpdateDomainRecordRequest();
        request.setRR(rr);
        request.setType("AAAA");
        request.setRecordId(analysisId);
        request.setValue(ipv6);
        client.getAcsResponse(request);
    }

    private void addAnalysis(String rr, String domainName, String ipv6) throws ClientException {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKey, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        AddDomainRecordRequest request = new AddDomainRecordRequest();
        request.setRR(rr);
        request.setDomainName(domainName);
        request.setType("AAAA");
        request.setValue(ipv6);
        client.getAcsResponse(request);
    }

    private String getLocalIpv6() {
        final LinkedHashSet<InetAddress> ipv6Set = NetUtil.localAddressList(t -> t instanceof Inet6Address);
        if (CollectionUtils.isEmpty(ipv6Set)) {
            log.error("添加域名解析失败,获取本地IPV6地址为空");
            return null;
        }

        for (InetAddress ipv6 : ipv6Set) {
            String hostAddress = ipv6.getHostAddress();
            log.info(hostAddress);
            String[] hostAddressSplit = hostAddress.split(Symbol.PERCENT_SIGN);
            if (hostAddressSplit.length < 2) {
                continue;
            }
            if (ipv6.isLinkLocalAddress()) {
                continue;
            }
            if (hostAddressSplit[1].contains("wlan")) {
                return hostAddressSplit[0];
            }
            if (hostAddressSplit[1].contains("ens")) {
                return hostAddressSplit[0];
            }
        }
        return null;
    }
}

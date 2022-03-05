package tech.xs.system.service;

import com.aliyuncs.exceptions.ClientException;

/**
 * 域名管理Service
 *
 * @author 沈家文
 * @date 2021/7/23 11:32
 */
public interface SysDomainNameService {

    /**
     * 设置域名解析为当前Ipv6地址
     *
     * @param rr         域名前缀
     * @param domainName 域名
     * @throws ClientException 异常信息
     */
    void setAnalysisIpv6(String rr, String domainName) throws ClientException;

}

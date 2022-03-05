package tech.xs.framework.core.notice.sms.aliyun;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tech.xs.framework.core.notice.sms.Sms;
import tech.xs.framework.core.notice.sms.SmsNotice;
import tech.xs.framework.core.notice.sms.SmsResult;
import tech.xs.framework.core.notice.sms.SmsResultConstant;

import javax.annotation.Resource;


/**
 * 阿里云短信发送服务
 *
 * @author 沈家文
 * @date 2021/9/5 18:23
 */
@Component
public class AliYunSmsNotice implements SmsNotice {

    @Resource
    private AliYunSmsConfig config;


    @Override
    public SmsResult send(Sms sms) throws Exception {
        AliYunSms aliYunSms = (AliYunSms) sms;
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", config.getSmsAccessKey(), config.getSmsSecretKey());
        IAcsClient client = new DefaultAcsClient(profile);

        SendSmsRequest request = new SendSmsRequest();
        request.setSysRegionId("cn-hangzhou");
        request.setPhoneNumbers(aliYunSms.getPhone());
        request.setSignName("XSTech");
        request.setTemplateCode(aliYunSms.getTemplateCode());
        if (aliYunSms.getData() != null) {
            request.setTemplateParam(JSON.toJSONString(aliYunSms.getData()));
        }
        SendSmsResponse response = client.getAcsResponse(request);
        if ("OK".equals(response.getCode())) {
            return new SmsResult(SmsResultConstant.SUCCESS, SmsResultConstant.SUCCESS_MSG);
        }
        SmsResult result = new SmsResult();
        result.setMsg(response.getCode());
        return result;
    }

}

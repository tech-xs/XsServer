package tech.xs.framework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.xs.framework.constant.NoticeConstant;
import tech.xs.framework.core.notice.mail.AliYunMailEnterpriseNotice;
import tech.xs.framework.core.notice.mail.MailConfig;
import tech.xs.framework.core.notice.mail.MailNotice;
import tech.xs.framework.core.notice.sms.SmsNotice;
import tech.xs.framework.core.notice.sms.aliyun.AliYunSmsNotice;

import javax.annotation.Resource;

/**
 * 通知配置
 *
 * @author 沈家文
 * @date 2021/9/3 20:40
 */
@Configuration
public class NoticeConfig {

    @Value("${notice.sms.type:}")
    private String smsType;

    @Resource
    private AliYunSmsNotice aliYunSmsNotice;

    @Bean
    public SmsNotice smsNotice() {
        switch (smsType) {
            case NoticeConstant.MailType.ALI_YUN_ENTERPRISE: {
                return aliYunSmsNotice;
            }
            default: {
                return aliYunSmsNotice;
            }
        }
    }


}

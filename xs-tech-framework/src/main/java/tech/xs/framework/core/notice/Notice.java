package tech.xs.framework.core.notice;

import org.springframework.stereotype.Component;
import tech.xs.framework.core.notice.mail.Mail;
import tech.xs.framework.core.notice.mail.MailNotice;
import tech.xs.framework.core.notice.mail.MailResult;
import tech.xs.framework.core.notice.sms.Sms;
import tech.xs.framework.core.notice.sms.SmsNotice;
import tech.xs.framework.core.notice.sms.SmsResult;

import javax.annotation.Resource;
import javax.mail.MessagingException;

/**
 * 通知
 *
 * @author 沈家文
 * @date 2021/9/3 20:20
 */
@Component
public class Notice {

    @Resource
    private MailNotice mailNotice;

    @Resource
    private SmsNotice smsNotice;

    public MailResult send(Mail mail) throws Exception {
        return mailNotice.send(mail);
    }

    public SmsResult send(Sms sms) throws Exception {
        return smsNotice.send(sms);
    }

}

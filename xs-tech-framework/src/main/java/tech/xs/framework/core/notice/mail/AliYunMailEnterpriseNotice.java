package tech.xs.framework.core.notice.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tech.xs.common.lang.StringUtils;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 阿里云邮件工具
 *
 * @author 沈家文
 * @date 2021/9/3 20:33
 */
@Component
public class AliYunMailEnterpriseNotice {

    @Resource
    private MailConfig mailConfig;

    public MailResult send(Mail mail) throws MessagingException {
        AliYunMailEnterpriseConfig config = mailConfig.getAliYunMailEnterpriseConfig();
        String userName = config.getUserName();
        String formName = config.getFormName();
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", config.getHost());
        props.setProperty("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props);
        MimeMessage msg = new MimeMessage(session);
        if (StringUtils.isNotBlank(formName)) {
            msg.setFrom(new InternetAddress(formName + "<" + userName + ">"));
        } else {
            msg.setFrom(new InternetAddress(userName));
        }
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(mail.getTo()));
        msg.setSubject(mail.getTitle());
        msg.setText(mail.getContent());
        Transport trans = session.getTransport();
        trans.connect(userName, config.getPassword());
        trans.sendMessage(msg, msg.getAllRecipients());
        return new MailResult(MailResultConstant.SUCCESS, MailResultConstant.SUCCESS_MSG);
    }

}

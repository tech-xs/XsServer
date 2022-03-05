package tech.xs.framework.core.notice.mail;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 网易邮箱邮件发送
 */
@Component
public class NeteaseMailNotice {

    @Resource
    private MailConfig mailConfig;

    public MailResult send(Mail mail) throws MessagingException {
        NeteaseMailConfig config = mailConfig.getNeteaseMailConfig();
        Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "25");
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", "smtp.163.com");
        Session session = Session.getDefaultInstance(props);
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(config.getUserName()));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(mail.getTo()));
        msg.setSubject(mail.getTitle());
        msg.setText(mail.getContent());
        Transport trans = session.getTransport();
        trans.connect(config.getUserName(), config.getPassword());
        trans.sendMessage(msg, msg.getAllRecipients());
        return new MailResult(MailResultConstant.SUCCESS, MailResultConstant.SUCCESS_MSG);
    }

}

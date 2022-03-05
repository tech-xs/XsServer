package tech.xs.framework.core.notice.mail;

import org.springframework.stereotype.Component;
import tech.xs.framework.constant.NoticeConstant;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

/**
 * 邮件通知
 *
 * @author 沈家文
 * @date 2021/9/3 20:23
 */
@Component
public class MailNotice {

    @Resource
    private MailConfig mailConfig;

    @Resource
    private AliYunMailEnterpriseNotice aliYunMailEnterpriseNotice;

    @Resource
    private QqMailNotice qqMailNotice;

    @Resource
    private NeteaseMailNotice neteaseMailNotice;

    /**
     * 邮件发送
     *
     * @param mail 邮件内容
     */
    public MailResult send(Mail mail) throws MessagingException {
        String mailType = mailConfig.getMailType();
        switch (mailType) {
            case NoticeConstant.MailType.ALI_YUN_ENTERPRISE: {
                return aliYunMailEnterpriseNotice.send(mail);
            }
            case NoticeConstant.MailType.QQ: {
                return qqMailNotice.send(mail);
            }
            case NoticeConstant.MailType.NETEASE: {
                return neteaseMailNotice.send(mail);
            }
            default: {
                throw new MessagingException("未指定邮件服务器类型");
            }
        }
    }

    public MailResult test(String to) throws MessagingException {
        Mail mail = new Mail();
        mail.setContent("这是一封测试邮件");
        mail.setTitle("这是一封测试邮件");
        mail.setTo(to);
        return send(mail);
    }

}

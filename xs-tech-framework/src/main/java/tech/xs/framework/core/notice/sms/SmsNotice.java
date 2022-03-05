package tech.xs.framework.core.notice.sms;

/**
 * 短信发送服务
 *
 * @author 沈家文
 * @date 2021/9/5 18:20
 */
public interface SmsNotice {


    /**
     * 发送短信
     *
     * @param sms
     * @return
     * @throws Exception
     */
    SmsResult send(Sms sms) throws Exception;

}

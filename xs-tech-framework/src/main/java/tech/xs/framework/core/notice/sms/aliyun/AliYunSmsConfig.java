package tech.xs.framework.core.notice.sms.aliyun;

/**
 * 阿里云配置短信配置接口
 *
 * @author 沈家文
 * @date 2021/9/10 20:15
 */
public interface AliYunSmsConfig {

    /**
     * 获取短信发送的AccessKey
     *
     * @return
     */
    String getSmsAccessKey();

    /**
     * 获取短信发送的SecretKey
     *
     * @return
     */
    String getSmsSecretKey();

}

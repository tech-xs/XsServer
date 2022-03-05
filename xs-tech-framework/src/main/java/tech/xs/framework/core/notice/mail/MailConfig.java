package tech.xs.framework.core.notice.mail;

public interface MailConfig {

    /**
     * 获取邮件服务器类型
     *
     * @return
     */
    String getMailType();

    /**
     * 获取阿里云邮箱配置
     *
     * @return
     */
    AliYunMailEnterpriseConfig getAliYunMailEnterpriseConfig();

    /**
     * 获取QQ邮箱配置
     *
     * @return
     */
    QqMailConfig getQqMailConfig();

    /**
     * 获取网易邮箱配置
     *
     * @return
     */
    NeteaseMailConfig getNeteaseMailConfig();


}

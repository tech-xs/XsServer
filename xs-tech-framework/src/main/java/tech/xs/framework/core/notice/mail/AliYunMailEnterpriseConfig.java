package tech.xs.framework.core.notice.mail;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AliYunMailEnterpriseConfig {

    private String host;

    /**
     * 登陆邮箱
     */
    private String userName;

    private String password;

    /**
     * 发件人
     */
    private String formName;

}

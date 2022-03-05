package tech.xs.framework.core.notice.mail;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * QQ邮箱配置
 */
@Getter
@Setter
@ToString
public class QqMailConfig {

    /**
     * qq邮箱
     */
    private String userName;

    /**
     * qq邮箱授权码
     */
    private String password;

}

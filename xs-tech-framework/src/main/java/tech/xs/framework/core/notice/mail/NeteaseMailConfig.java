package tech.xs.framework.core.notice.mail;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 网易邮箱配置
 */
@Getter
@Setter
@ToString
public class NeteaseMailConfig {

    /**
     * 网易邮箱
     */
    private String userName;

    /**
     * 网易邮箱授权密码
     */
    private String password;

}

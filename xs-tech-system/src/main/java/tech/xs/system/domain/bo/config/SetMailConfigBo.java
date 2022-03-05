package tech.xs.system.domain.bo.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SetMailConfigBo {

    private String mailType;

    private String host;

    private String mail;

    private String userName;

    private String password;

    private String formName;
}

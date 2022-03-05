package tech.xs.system.domain.bo.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 用户管理中的修改用户密码功能
 */
@Getter
@Setter
@ToString
public class ModifyUserPasswordBo {

    @NotNull
    private Long id;
    
    @NotBlank
    private String password;

}

package tech.xs.system.domain.bo.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import tech.xs.framework.annotation.doc.Param;
import tech.xs.framework.constant.ParamCheckConstant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 通过邮件注册账户
 *
 * @author 沈家文
 * @date 2021/9/6 19:07
 */
@Getter
@Setter
@ToString
public class RegisterEmailBo {

    @Param(title = "邮箱")
    @NotBlank
    private String email;

    @NotBlank
    @Param(title = "验证码")
    @Length(min = ParamCheckConstant.VERIFICATION_CODE_LENGTH, max = ParamCheckConstant.VERIFICATION_CODE_LENGTH)
    private String verificationCode;

    @NotBlank
    @Param(title = "密码")
    private String password;
}

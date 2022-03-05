package tech.xs.system.domain.bo.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import tech.xs.framework.enums.BooleanEnum;
import tech.xs.framework.enums.SexEnum;
import tech.xs.system.constant.SysParamCheckConstant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class AddUserBo {

    @NotBlank
    @Length(max = SysParamCheckConstant.SysUser.USER_NAME_MAX_LENGTH)
    private String userName;
    @NotBlank
    private String password;
    @Length(max = SysParamCheckConstant.SysUser.NICK_NAME_MAX_LENGTH)
    private String nickName;
    @Length(max = SysParamCheckConstant.SysUser.PHONE_MAX_LENGTH)
    private String phone;
    @Length(max = SysParamCheckConstant.SysUser.EMAIL_MAX_LENGTH)
    private String email;
    @NotNull
    private SexEnum sex;
    @NotNull
    private BooleanEnum accountStatus;
    private Long companyId;
    private List<Long> roleIdList;

}

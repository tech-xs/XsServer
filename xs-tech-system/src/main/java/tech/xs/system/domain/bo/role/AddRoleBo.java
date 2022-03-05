package tech.xs.system.domain.bo.role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import tech.xs.framework.enums.BooleanEnum;
import tech.xs.system.constant.SysParamCheckConstant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class AddRoleBo {

    @NotBlank
    @Length(max = SysParamCheckConstant.SysRole.CODE_MAX_LENGTH)
    private String code;
    @NotBlank
    @Length(max = SysParamCheckConstant.SysRole.NAME_MAX_LENGTH)
    private String name;
    @Length(max = SysParamCheckConstant.SysRole.REMARK_MAX_LENGTH)
    private String remark;
    @NotNull
    private BooleanEnum status;
    
}

package tech.xs.system.domain.bo.role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import tech.xs.framework.enums.BooleanEnum;
import tech.xs.system.constant.SysParamCheckConstant;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ModifyRoleBo {
    @NotNull
    private Long id;
    @Length(min = SysParamCheckConstant.SysRole.CODE_MIN_LENGTH, max = SysParamCheckConstant.SysRole.CODE_MAX_LENGTH)
    private String code;
    @Length(min = SysParamCheckConstant.SysRole.NAME_MIN_LENGTH, max = SysParamCheckConstant.SysRole.NAME_MAX_LENGTH)
    private String name;
    @Length(max = SysParamCheckConstant.SysRole.REMARK_MAX_LENGTH)
    private String remark;
    private BooleanEnum status;
}

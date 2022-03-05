package tech.xs.system.domain.bo.page.permission;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import tech.xs.system.constant.SysParamCheckConstant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class AddPagePermissionBo {

    @NotNull
    private Long pageId;
    @NotBlank
    @Length(max = SysParamCheckConstant.SysPagePermission.CODE_MAX_LENGTH)
    private String permissionCode;
    @NotBlank
    @Length(max = SysParamCheckConstant.SysPagePermission.NAME_MAX_LENGTH)
    private String permissionName;
    @Length(max = SysParamCheckConstant.SysPagePermission.REMARK_MAX_LENGTH)
    private String remark;

}

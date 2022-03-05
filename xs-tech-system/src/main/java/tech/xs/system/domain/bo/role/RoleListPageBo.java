package tech.xs.system.domain.bo.role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import tech.xs.framework.base.ListPageBo;
import tech.xs.system.constant.SysParamCheckConstant;
import tech.xs.system.domain.entity.SysRole;


@Getter
@Setter
@ToString
public class RoleListPageBo extends ListPageBo<SysRole> {

    @Length(max = SysParamCheckConstant.SysRole.CODE_MAX_LENGTH)
    private String likeCode;
    @Length(max = SysParamCheckConstant.SysRole.NAME_MAX_LENGTH)
    private String likeName;
}

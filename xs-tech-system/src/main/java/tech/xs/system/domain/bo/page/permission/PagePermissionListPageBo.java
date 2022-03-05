package tech.xs.system.domain.bo.page.permission;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import tech.xs.framework.base.ListPageBo;
import tech.xs.system.constant.SysParamCheckConstant;
import tech.xs.system.domain.entity.SysPagePermission;

/**
 * 页面权限分页查询Bo
 */
@Getter
@Setter
@ToString
public class PagePermissionListPageBo extends ListPageBo<SysPagePermission> {

    private Long pageId;
    @Length(max = SysParamCheckConstant.SysPagePermission.NAME_MAX_LENGTH)
    private String likePermissionName;
    @Length(max = SysParamCheckConstant.SysPagePermission.CODE_MAX_LENGTH)
    private String likePermissionCode;

}

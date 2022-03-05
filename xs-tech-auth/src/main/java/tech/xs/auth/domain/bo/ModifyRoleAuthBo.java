package tech.xs.auth.domain.bo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.system.domain.entity.SysRoleMenuPage;
import tech.xs.system.domain.entity.SysRoleMenuPagePermission;

import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
@Setter
@ToString
public class ModifyRoleAuthBo {

    @NotNull
    private Long roleId;

    /**
     * 该值如果为null则跳过处理
     * 为空列表则清除对应的权限
     */
    private List<Long> menuIdList;

    /**
     * 菜单子页面
     * 该值如果为null则跳过处理
     * 为空列表则清除对应的权限
     * 该请求中实体类中只用到参数[menuId,pageId]
     */
    private List<SysRoleMenuPage> menuPageList;

    /**
     * 菜单页面权限ID
     * 该值如果为null则跳过处理
     * 为空列表则清除对应的权限
     * * 该请求中实体类中只用到参数[menuId,pageId,permissionId]
     */
    private List<SysRoleMenuPagePermission> menuPagePermissionList;

}

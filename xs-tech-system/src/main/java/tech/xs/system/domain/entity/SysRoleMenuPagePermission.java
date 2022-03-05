package tech.xs.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.base.BaseEntity;

@Getter
@Setter
@ToString
@TableName("sys_role_menu_page_permission")
public class SysRoleMenuPagePermission extends BaseEntity {

    /**
     * 角色ID
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 菜单ID
     */
    @TableField("menu_id")
    private Long menuId;

    /**
     * 页面Id
     */
    @TableField("page_id")
    private Long pageId;

    /**
     * 权限ID
     */
    @TableField("permission_id")
    private Long permissionId;

}

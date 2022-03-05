package tech.xs.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.base.BaseEntity;
import tech.xs.system.enmus.MenuSelectType;

import java.util.Objects;

/**
 * 角色菜单关联
 *
 * @author 沈家文
 * @date 2020/12/1 17:42
 */
@Getter
@Setter
@ToString
@TableName("sys_role_menu")
public class SysRoleMenu extends BaseEntity {

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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysRoleMenu roleMenu = (SysRoleMenu) o;
        return Objects.equals(roleId, roleMenu.roleId) && Objects.equals(menuId, roleMenu.menuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, menuId);
    }
}

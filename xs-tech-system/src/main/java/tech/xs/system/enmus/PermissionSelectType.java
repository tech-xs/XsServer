package tech.xs.system.enmus;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.ToString;
import tech.xs.system.constant.RoleMenuConstant;
import tech.xs.system.constant.RolePagePermissionConstant;

/**
 * 角色权限选择类型
 *
 * @author 沈家文
 * @date 2021-47-27 10:47
 */
@Getter
@ToString
public enum PermissionSelectType {

    /**
     * 权限选择类型 1 所有权限 2 当前权限 3 指定页面的所有权限
     */
    ALL(RolePagePermissionConstant.PermissionSelectType.ALL),
    ALL_CHILD(RolePagePermissionConstant.PermissionSelectType.PAGE_ALL),
    CURRENT(RolePagePermissionConstant.PermissionSelectType.CURRENT);

    @EnumValue
    private final int code;

    PermissionSelectType(int code) {
        this.code = code;
    }

}

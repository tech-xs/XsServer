package tech.xs.system.enmus;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.ToString;
import tech.xs.system.constant.RoleMenuConstant;

/**
 * 角色对应的菜单选择类型
 *
 * @author 沈家文
 * @date 2021-27-27 10:27
 */
@Getter
@ToString
public enum MenuSelectType {

    /**
     * 1 全部菜单 2 该菜单下和对应的全部子菜单 3 当前菜单
     */
    ALL(RoleMenuConstant.MenuSelectType.ALL),
    ALL_CHILD(RoleMenuConstant.MenuSelectType.ALL_CHILD),
    CURRENT(RoleMenuConstant.MenuSelectType.CURRENT);

    @EnumValue
    private final int code;

    MenuSelectType(int code) {
        this.code = code;
    }

}

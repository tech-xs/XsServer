package tech.xs.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import tech.xs.framework.domain.model.ApiResult;
import tech.xs.system.domain.bo.role.menu.RoleMenuListAllBo;
import tech.xs.system.domain.entity.SysRoleMenu;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色菜单Controller
 *
 * @author 沈家文
 * @date 2021/5/20 18:09
 */
@Validated
@RestController
@RequestMapping("/sys/role/menu")
public class SysRoleMenuController extends BaseSysController {

    @PostMapping("/modify/roleId")
    public ApiResult<Object> modify(
            @NotNull Long roleId,
            @NotNull List<SysRoleMenu> roleMenuList) {
        if (!sysRoleService.existById(roleId)) {
            return ApiResult.error(1000, "角色不存在", roleId);
        }
        for (SysRoleMenu roleMenuPermission : roleMenuList) {
            if (roleMenuPermission.getMenuId() != null && !sysMenuService.existById(roleMenuPermission.getMenuId())) {
                return ApiResult.error(1002, "菜单不存在", roleMenuPermission.getMenuId());
            }
        }
        sysRoleMenuService.modify(roleId, roleMenuList);
        return ApiResult.success();
    }

    @GetMapping("/list/all")
    public ApiResult<List<SysRoleMenu>> list(@Valid RoleMenuListAllBo bo) {
        return ApiResult.success(sysRoleMenuService.list(Wrappers.<SysRoleMenu>lambdaQuery()
                .eq(bo.getRoleId() != null, SysRoleMenu::getRoleId, bo.getRoleId())));
    }

}

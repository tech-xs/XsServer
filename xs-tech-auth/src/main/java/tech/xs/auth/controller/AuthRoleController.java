package tech.xs.auth.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.xs.auth.domain.bo.ModifyRoleAuthBo;
import tech.xs.auth.service.AuthRoleService;
import tech.xs.common.collections.CollectionUtils;
import tech.xs.framework.annotation.doc.Api;
import tech.xs.framework.context.XsContext;
import tech.xs.framework.domain.model.ApiResult;
import tech.xs.system.domain.entity.SysRole;
import tech.xs.system.domain.entity.SysRoleMenuPage;
import tech.xs.system.domain.entity.SysRoleMenuPagePermission;
import tech.xs.system.service.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 权限Controller
 *
 * @author 沈家文
 * @date 2021/4/14 16:34
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/auth/role")
public class AuthRoleController extends BaseAuthController {

    @Resource
    private AuthRoleService authRoleService;
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysPageService sysPageService;
    @Resource
    private SysRoleMenuPagePermissionService sysRoleMenuPagePermissionService;

    /**
     * 获取当前用户的所有角色列表,包含自身的,继承过来的
     *
     * @return
     */
    @Api(name = "获取当前登录用户的角色信息")
    @GetMapping("/list/current")
    public ApiResult listCurrent() {
        Long currentUserId = XsContext.getUserId();
        List<SysRole> roleList = sysUserRoleService.listRoleByUserId(currentUserId);
        return ApiResult.success(roleList);
    }

    /**
     * 修改角色授权
     *
     * @return
     */
    @PostMapping("/modify/id")
    public ApiResult modifyRoleAuth(@Valid @RequestBody ModifyRoleAuthBo bo) {
        if (!sysRoleService.existById(bo.getRoleId())) {
            return ApiResult.error(1000, "角色不存在");
        }
        List<Long> menuIdList = bo.getMenuIdList();
        if (CollectionUtils.isNotEmpty(menuIdList) && !sysMenuService.existByIds(menuIdList)) {
            return ApiResult.error(1001, "菜单不存在");
        }

        if (CollectionUtils.isNotEmpty(bo.getMenuPageList())) {
            for (SysRoleMenuPage item : bo.getMenuPageList()) {
                if (item.getMenuId() == null) {
                    return ApiResult.error(1002, "[menuPageList]参数中的[menuId]不能为空");
                }
                if (item.getPageId() == null) {
                    return ApiResult.error(1003, "[menuPageList]参数中的[pageId]不能为空");
                }
            }
            Set<Long> pageIdSet = new HashSet<>();
            for (SysRoleMenuPage menuPage : bo.getMenuPageList()) {
                pageIdSet.add(menuPage.getPageId());
            }
            if (!sysPageService.existByIds(pageIdSet)) {
                return ApiResult.error(1004, "页面不存在");
            }
        }
        if (CollectionUtils.isNotEmpty(bo.getMenuPagePermissionList())) {
            List<Long> permissionIdList = new ArrayList<>();
            for (SysRoleMenuPagePermission permission : bo.getMenuPagePermissionList()) {
                if (permission.getPermissionId() == null) {
                    return ApiResult.error(1005, "[permissionId]不能为空");
                }
                if (permission.getPageId() == null) {
                    return ApiResult.error(1006, "[pageId]不能为空");
                }
                if (permission.getMenuId() == null) {
                    return ApiResult.error(1006, "[menuId]不能为空");
                }
                permissionIdList.add(permission.getPermissionId());
            }
            if (!permissionIdList.isEmpty() && !sysRoleMenuPagePermissionService.existByIds(permissionIdList)) {
                return ApiResult.error(1007, "页面权限不存在");
            }
        }
        authRoleService.modifyRoleAuth(bo);
        return ApiResult.success();
    }


}

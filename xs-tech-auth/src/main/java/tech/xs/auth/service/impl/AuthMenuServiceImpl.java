package tech.xs.auth.service.impl;

import tech.xs.auth.service.AuthRoleService;
import tech.xs.auth.service.AuthorizationService;
import tech.xs.common.collections.CollectionUtils;
import tech.xs.framework.context.XsContext;
import tech.xs.framework.enums.BooleanEnum;
import tech.xs.system.constant.SysRoleConstant;
import tech.xs.system.domain.entity.SysPage;
import tech.xs.system.domain.entity.SysRole;
import tech.xs.system.service.SysMenuService;
import tech.xs.system.service.SysPageService;
import tech.xs.system.service.SysRoleMenuService;
import org.springframework.stereotype.Service;
import tech.xs.auth.service.AuthMenuService;
import tech.xs.system.domain.entity.SysMenu;
import tech.xs.system.service.SysUserRoleService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 授权菜单Service
 *
 * @author 沈家文
 * @date 2021/4/15 16:39
 */
@Service
public class AuthMenuServiceImpl implements AuthMenuService {
    @Resource
    private AuthorizationService authorizationService;
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysPageService sysPageService;
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @Override
    public List<SysMenu> webTreeCurrMenu() {
        List<SysMenu> menuTree = new ArrayList<>();
        Long userId = XsContext.getUserId();
        if (authorizationService.isSuperAdminAuthorization(userId)) {
            menuTree = sysMenuService.tree();
        } else {
            List<Long> roleIdList = sysUserRoleService.listRoleIdByUserId(userId);
            if (CollectionUtils.isEmpty(roleIdList)) {
                return menuTree;
            }
            menuTree = sysRoleMenuService.menuTree(roleIdList);
        }
        List<SysPage> pageTree = sysPageService.tree();
        List<SysMenu> menuList = new ArrayList<>();
        menuList.addAll(menuTree);
        for (int i = 0; i < menuList.size(); i++) {
            SysMenu menu = menuList.get(i);
            if (CollectionUtils.isNotEmpty(menu.getChildList())) {
                menuList.addAll(menu.getChildList());
            }
            if (menu.getPageId() != null) {
                for (SysPage page : pageTree) {
                    if (menu.getPageId().equals(page.getId())) {
                        menu.setPage(page);
                    }
                }
            }
        }
        return menuTree;
    }

}

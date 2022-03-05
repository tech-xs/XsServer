package tech.xs.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.xs.auth.domain.bo.ModifyRoleAuthBo;
import tech.xs.auth.service.AuthRoleService;
import tech.xs.system.domain.entity.*;
import tech.xs.system.service.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 授权角色实现类
 *
 * @author 沈家文
 * @date 2021/2/25 11:28
 */
@Service
public class AuthRoleServiceImpl implements AuthRoleService {

    @Resource
    private SysRoleMenuService sysRoleMenuService;
    @Resource
    private SysRoleApiService sysRoleApiService;
    @Resource
    private SysRoleMenuPageService sysRoleMenuPageService;
    @Resource
    private SysRoleMenuPagePermissionService sysRoleMenuPagePermissionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyRoleAuth(ModifyRoleAuthBo bo) {
        Long roleId = bo.getRoleId();
        // 保存角色菜单
        List<Long> menuIdList = bo.getMenuIdList();
        if (menuIdList != null) {
            sysRoleMenuService.delete(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getRoleId, bo.getRoleId()));
            List<SysRoleMenu> roleMenuList = new ArrayList<>();
            for (Long menuId : bo.getMenuIdList()) {
                SysRoleMenu roleMenu = new SysRoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menuId);
                roleMenuList.add(roleMenu);
            }
            if (!roleMenuList.isEmpty()) {
                sysRoleMenuService.add(roleMenuList);
            }

            // 保存菜单子页面
            List<SysRoleMenuPage> menuPageIdList = bo.getMenuPageList();
            if (menuPageIdList != null) {
                sysRoleMenuPageService.delete(Wrappers.<SysRoleMenuPage>lambdaQuery()
                        .eq(SysRoleMenuPage::getRoleId, roleId));
                List<SysRoleMenuPage> roleMenuPageList = new ArrayList<>();
                for (SysRoleMenuPage item : menuPageIdList) {
                    SysRoleMenuPage roleMenuPage = new SysRoleMenuPage();
                    roleMenuPage.setRoleId(roleId);
                    roleMenuPage.setMenuId(item.getMenuId());
                    roleMenuPage.setPageId(item.getPageId());
                    roleMenuPageList.add(roleMenuPage);
                }
                if (!roleMenuPageList.isEmpty()) {
                    sysRoleMenuPageService.add(roleMenuPageList);
                }
            }
        }
        if (bo.getMenuPagePermissionList() != null) {
            sysRoleMenuPagePermissionService.delete(Wrappers.<SysRoleMenuPagePermission>lambdaQuery()
                    .eq(SysRoleMenuPagePermission::getRoleId, roleId));
            List<SysRoleMenuPagePermission> permissionList = bo.getMenuPagePermissionList();
            for (SysRoleMenuPagePermission permission : permissionList) {
                permission.setRoleId(roleId);
            }
            if (!permissionList.isEmpty()) {
                sysRoleMenuPagePermissionService.add(permissionList);
            }
        }
        sysRoleApiService.clearCacheRoleUri();
    }

}

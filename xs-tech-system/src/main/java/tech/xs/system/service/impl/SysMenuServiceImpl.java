package tech.xs.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import tech.xs.common.collections.CollectionUtils;
import tech.xs.common.lang.StringUtils;
import tech.xs.system.domain.bo.menu.DeleteMenuBo;
import tech.xs.system.domain.bo.menu.ModifyMenuBo;
import tech.xs.system.domain.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.xs.framework.base.BaseEntity;
import tech.xs.system.dao.SysMenuDao;
import tech.xs.system.service.SysMenuService;

import javax.annotation.Resource;
import java.util.*;

/**
 * 菜单Service实现了
 *
 * @author 沈家文
 * @date 2020/10/22
 */
@Service
public class SysMenuServiceImpl extends BaseSysServiceImpl<SysMenuDao, SysMenu> implements SysMenuService {

    @Resource
    private SysMenuDao sysMenuDao;

    @Override
    public void deleteById(Long id) {
        sysRoleMenuService.delete(Wrappers.<SysRoleMenu>lambdaQuery()
                .eq(SysRoleMenu::getMenuId, id));
        sysMenuDao.deleteById(id);
    }

    @Override
    public List<SysMenu> tree() {
        List<SysMenu> menuList = sysMenuDao.selectList(Wrappers.<SysMenu>lambdaQuery().orderByAsc(SysMenu::getSort));
        return buildMenuTree(menuList);
    }

    @Override
    public List<SysMenu> tree(Collection<Long> menuIds) {
        if (CollectionUtils.isEmpty(menuIds)) {
            return new ArrayList<>();
        }
        List<SysMenu> menuList = sysMenuDao.selectList(Wrappers.<SysMenu>lambdaQuery().in(BaseEntity::getId, menuIds).orderByAsc(SysMenu::getSort));
        return buildMenuTree(menuList);
    }

    @Override
    public List<SysMenu> listDepthChild(Collection<Long> menuIds) {
        List<SysMenu> menuList = sysMenuDao.selectList(Wrappers.<SysMenu>lambdaQuery().in(SysMenu::getFatherId, menuIds));
        if (CollectionUtils.isEmpty(menuList)) {
            return menuList;
        }
        Set<Long> ids = new HashSet<>();
        for (SysMenu item : menuList) {
            ids.add(item.getId());
        }
        menuList.addAll(listDepthChild(ids));
        return menuList;
    }

    @Override
    public List<SysMenu> listDepthChild(Long menuId) {
        List<Long> ids = new ArrayList<>();
        ids.add(menuId);
        return listDepthChild(ids);
    }


    private List<SysMenu> buildMenuTree(List<SysMenu> menuList) {
        List<SysMenu> tree = new ArrayList<>();
        for (SysMenu menu : menuList) {
            if (menu.getFatherId() == null) {
                tree.add(menu);
            }
            List<SysMenu> childList = new ArrayList<>();
            for (SysMenu child : menuList) {
                if (child.getFatherId() == null) {
                    continue;
                }
                if (child.getFatherId().equals(menu.getId())) {
                    childList.add(child);
                }
            }
            if (CollectionUtils.isNotEmpty(childList)) {
                menu.setChildList(childList);
            }
        }
        return tree;
    }

    @Override
    public boolean isChild(Long fatherId, Long childId) {
        List<SysMenu> sysMenus = sysMenuDao.selectList(Wrappers.<SysMenu>lambdaQuery().select(BaseEntity::getId).eq(SysMenu::getFatherId, fatherId));
        if (CollectionUtils.isEmpty(sysMenus)) {
            return false;
        }
        for (SysMenu menu : sysMenus) {
            if (menu.getId().equals(childId)) {
                return true;
            }
        }
        for (SysMenu menu : sysMenus) {
            if (isChild(menu.getId(), childId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(ModifyMenuBo bo) {
        // 情况1: 如果菜单绑定的页面被修改
        // 情况2: 如果之前菜单绑定了页面,当前修改成未绑定页面
        // 以上情况需要删除之前页面及其子页面对应的所有权限
        SysMenu dbMenu = sysMenuDao.selectById(bo.getId());
        if ((dbMenu.getPageId() != null && bo.getPageId() != null && !dbMenu.getPageId().equals(bo.getPageId())) ||
                (dbMenu.getPageId() != null && bo.getPageId() == null) ||
                (dbMenu.getFatherId() == null && bo.getFatherId() != null) ||
                (dbMenu.getFatherId() != null && bo.getFatherId() != null && !dbMenu.getFatherId().equals(bo.getFatherId())) ||
                (dbMenu.getFatherId() != null && bo.getFatherId() == null)) {
            // 删除所有权限
            sysRoleMenuPagePermissionService.delete(Wrappers.<SysRoleMenuPagePermission>lambdaQuery().in(SysRoleMenuPagePermission::getMenuId, dbMenu.getId()));
            sysRoleMenuPageService.delete(Wrappers.<SysRoleMenuPage>lambdaQuery().in(SysRoleMenuPage::getMenuId, dbMenu.getId()));
        }
        // 情况1: 之前菜单没有父页面,当前增加了父菜单
        // 情况2: 之前菜单有父菜单,但是与修改之后的父菜单不相同
        // 情况3: 之前菜单有父菜单,修改之后没有父菜单
        // 以上情况需要删除菜单,及其子菜单绑定的所有页面及其子页面对应的所有权限
        if ((dbMenu.getFatherId() == null && bo.getFatherId() != null) ||
                (dbMenu.getFatherId() != null && bo.getFatherId() != null && !dbMenu.getFatherId().equals(bo.getFatherId())) ||
                (dbMenu.getFatherId() != null && bo.getFatherId() == null)) {
            HashSet<Long> ids = new HashSet<>();
            List<SysMenu> menuList = listDepthChild(bo.getId());
            for (SysMenu item : menuList) {
                ids.add(item.getId());
            }
            ids.add(bo.getId());
            sysRoleMenuPagePermissionService.delete(Wrappers.<SysRoleMenuPagePermission>lambdaQuery().in(SysRoleMenuPagePermission::getMenuId, ids));
            sysRoleMenuPageService.delete(Wrappers.<SysRoleMenuPage>lambdaQuery().in(SysRoleMenuPage::getMenuId, ids));
        }
        sysMenuDao.update(new SysMenu(), Wrappers.<SysMenu>lambdaUpdate()
                .set(StringUtils.isNotBlank(bo.getName()), SysMenu::getName, bo.getName())
                .set(StringUtils.isNotBlank(bo.getCode()), SysMenu::getCode, bo.getCode())
                .set(SysMenu::getSort, bo.getSort())
                .set(SysMenu::getFatherId, bo.getFatherId())
                .set(SysMenu::getPageId, bo.getPageId())
                .set(StringUtils.isNotBlank(bo.getIcon()), SysMenu::getIcon, bo.getIcon())
                .eq(BaseEntity::getId, bo.getId()));
        sysRoleApiService.clearCacheRoleUri();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDetails(DeleteMenuBo bo) {
        sysRoleMenuService.delete(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getMenuId, bo.getId()));
        sysMenuDao.deleteById(bo.getId());
        sysRoleMenuPagePermissionService.delete(Wrappers.<SysRoleMenuPagePermission>lambdaQuery().eq(SysRoleMenuPagePermission::getMenuId, bo.getId()));
        sysRoleMenuPageService.delete(Wrappers.<SysRoleMenuPage>lambdaQuery().eq(SysRoleMenuPage::getMenuId, bo.getId()));
        sysMenuDao.update(new SysMenu(), Wrappers.<SysMenu>lambdaUpdate()
                .set(SysMenu::getFatherId, null)
                .eq(SysMenu::getFatherId, bo.getId()));
        sysRoleApiService.clearCacheRoleUri();
    }


}

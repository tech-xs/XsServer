package tech.xs.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import tech.xs.common.constant.Symbol;
import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.core.cache.Cache;
import tech.xs.framework.util.RedisUtil;
import tech.xs.system.constant.SysCacheConstant;
import tech.xs.system.constant.SysRoleConstant;
import tech.xs.system.domain.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import tech.xs.system.dao.SysApiDao;
import tech.xs.system.enmus.MenuSelectType;
import tech.xs.system.service.SysApiService;
import tech.xs.system.service.SysRoleService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 沈家文
 * @date 2021/03/02 11:32
 */
@Slf4j
@Service
public class SysApiServiceImpl extends BaseSysServiceImpl<SysApiDao, SysApi> implements SysApiService {

    @Resource
    private Cache cache;

    @Override
    public List<SysApi> listUriByRoleId(Long roleId) {
        List<SysRoleMenu> roleMenus = sysRoleMenuService.list(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getRoleId, roleId));
        if (CollectionUtils.isEmpty(roleMenus)) {
            return new ArrayList<>();
        }

        if (sysRoleService.exist(Wrappers.<SysRole>lambdaQuery()
                .eq(BaseEntity::getId, equals(roleId))
                .eq(SysRole::getCode, SysRoleConstant.Code.SUPER_ADMIN))) {
            return sysApiService.list(Wrappers.<SysApi>lambdaQuery());
        }
        // 查询角色拥有的菜单
        List<SysMenu> menuList = null;
        if (menuList == null) {
            ArrayList<Long> menuIdList = new ArrayList<>();
            for (SysRoleMenu roleMenu : roleMenus) {
                menuIdList.add(roleMenu.getMenuId());
            }
            menuList = sysMenuService.list(Wrappers.<SysMenu>lambdaQuery().in(BaseEntity::getId, menuIdList));
        }

        if (CollectionUtils.isEmpty(menuList)) {
            return new ArrayList<>();
        }
        Set<Long> pageIdSet = new HashSet<>();
        for (SysMenu menu : menuList) {
            if (menu.getPageId() != null) {
                pageIdSet.add(menu.getPageId());
            }
        }
        // 查询角色拥有的菜单子页面
        List<SysRoleMenuPage> roleMenuPageList = sysRoleMenuPageService.list(Wrappers.<SysRoleMenuPage>lambdaQuery().eq(SysRoleMenuPage::getRoleId, roleId));
        for (SysRoleMenuPage roleMenuPage : roleMenuPageList) {
            pageIdSet.add(roleMenuPage.getPageId());
        }
        List<SysPage> pageList = sysPageService.listDepthChild(pageIdSet);
        for (SysPage page : pageList) {
            pageIdSet.add(page.getId());
        }
        if (CollectionUtils.isEmpty(pageIdSet)) {
            return new ArrayList<>();
        }
        // 查询菜单绑定的页面以及页面绑定的子页面对应的Api列表,这些该角色都是具有可访问权限的列表,但是不包含按钮权限对应的Api
        List<SysPageApi> pageApiList = sysPageApiService.list(Wrappers.<SysPageApi>lambdaQuery().in(SysPageApi::getPageId, pageIdSet)
                .isNull(SysPageApi::getPermissionId));
        if (CollectionUtils.isEmpty(pageApiList)) {
            return new ArrayList<>();
        }
        Set<Long> resourcesIdSet = new HashSet<>();
        for (SysPageApi pageApi : pageApiList) {
            resourcesIdSet.add(pageApi.getApiId());
        }
        // 查询按钮级别对应的Api列表
        List<SysRoleMenuPagePermission> roleMenuPagePermissionList = sysRoleMenuPagePermissionService.list(Wrappers.<SysRoleMenuPagePermission>lambdaQuery().eq(SysRoleMenuPagePermission::getRoleId, roleId));
        Set<Long> permissionIdSet = new HashSet<>();
        for (SysRoleMenuPagePermission sysRoleMenuPagePermission : roleMenuPagePermissionList) {
            permissionIdSet.add(sysRoleMenuPagePermission.getPermissionId());
        }
        pageApiList = sysPageApiService.list(Wrappers.<SysPageApi>lambdaQuery().in(SysPageApi::getPermissionId, permissionIdSet));
        for (SysPageApi pageApi : pageApiList) {
            resourcesIdSet.add(pageApi.getApiId());
        }
        return sysApiService.list(Wrappers.<SysApi>lambdaQuery().in(BaseEntity::getId, resourcesIdSet));
    }

    @Override
    public Set<String> listUriStrByRoleId(Long roleId) {
        Set<String> cacheValue = getCacheUriStrByRoleId(roleId);
        if (cacheValue != null) {
            return cacheValue;
        }

        List<SysApi> apiList = listUriByRoleId(roleId);
        Set<String> resourceStrSet = new HashSet<>();
        if (CollectionUtils.isNotEmpty(apiList)) {
            apiList.forEach(item -> {
                resourceStrSet.add(item.getMethod().getValue() + Symbol.COLON + item.getUri());
            });
        }
        setCacheUriStrByRoleId(roleId, resourceStrSet);
        return resourceStrSet;
    }

    @Override
    public Set<String> listUriStrByRoleIdList(List<Long> roleIdList) {
        if (CollectionUtils.isEmpty(roleIdList)) {
            return new HashSet<>();
        }
        HashSet<String> res = new HashSet<>();
        for (Long roleId : roleIdList) {
            Set<String> uriStr = listUriStrByRoleId(roleId);
            if (CollectionUtils.isNotEmpty(uriStr)) {
                res.addAll(uriStr);
            }
        }
        return res;
    }

    @Override
    public Set<String> getCacheUriStrByRoleId(Long roleId) {
        return cache.get(RedisUtil.splicingKey(SysCacheConstant.ROLE_URI_RESOURCE, roleId), HashSet.class);
    }

    @Override
    public void setCacheUriStrByRoleId(Long roleId, Set<String> data) {
        cache.set(RedisUtil.splicingKey(SysCacheConstant.ROLE_URI_RESOURCE, roleId), data);
    }

}

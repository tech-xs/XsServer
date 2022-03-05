package tech.xs.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import tech.xs.system.domain.entity.SysMenu;
import tech.xs.system.domain.entity.SysPage;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.xs.system.dao.SysRoleMenuDao;
import tech.xs.system.domain.entity.SysRoleMenu;
import tech.xs.system.service.SysRoleMenuService;

import javax.annotation.Resource;
import java.util.*;

/**
 * 角色菜单Service
 *
 * @author 沈家文
 * @date 2020/12/1 17:48
 */
@Service
public class SysRoleMenServiceImpl extends BaseSysServiceImpl<SysRoleMenuDao, SysRoleMenu> implements SysRoleMenuService {

    @Resource
    private SysRoleMenuDao sysRoleMenuDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByRoleId(Long roleId) {
        sysRoleMenuDao.delete(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getRoleId, roleId));
        sysRoleApiService.clearCacheRoleUri();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modify(Long roleId, List<SysRoleMenu> roleMenuPermissionList) {
        sysRoleMenuDao.delete(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getRoleId, roleId));
        for (SysRoleMenu item : roleMenuPermissionList) {
            item.setRoleId(roleId);
            sysRoleMenuDao.insert(item);
        }
        return roleMenuPermissionList.size();
    }

    @Override
    public List<SysMenu> menuTree(Collection<Long> roleIds) {
        List<SysRoleMenu> roleMenuList = sysRoleMenuDao.selectList(Wrappers.<SysRoleMenu>lambdaQuery().in(SysRoleMenu::getRoleId, roleIds));
        Set<Long> menuIds = new HashSet<>();
        for (SysRoleMenu roleMenu : roleMenuList) {
            menuIds.add(roleMenu.getMenuId());
        }
        return sysMenuService.tree(menuIds);
    }
}

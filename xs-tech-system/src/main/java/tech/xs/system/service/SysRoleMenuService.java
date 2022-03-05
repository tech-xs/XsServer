package tech.xs.system.service;

import tech.xs.system.domain.entity.SysMenu;
import tech.xs.system.domain.entity.SysRoleMenu;

import java.util.Collection;
import java.util.List;

/**
 * 角色菜单关联Service
 * <p>
 * 如果角色拥有菜单中的某个子菜单,那么数据库中必然存在该子菜单的父菜单
 *
 * @author 沈家文
 * @date 2020/12/1 17:47
 */
public interface SysRoleMenuService extends BaseSysService<SysRoleMenu> {

    /**
     * 根据角色ID删除
     *
     * @param roleId
     */
    void deleteByRoleId(Long roleId);

    /**
     * 修改角色菜单
     *
     * @param roleId
     * @param entityList
     * @return
     */
    int modify(Long roleId, List<SysRoleMenu> entityList);

    /**
     * 根据角色ID查询菜单树
     *
     * @param roleIds
     * @return
     */
    List<SysMenu> menuTree(Collection<Long> roleIds);

}

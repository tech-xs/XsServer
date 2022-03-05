package tech.xs.system.service;


import tech.xs.system.domain.bo.role.DeleteRoleBo;
import tech.xs.system.domain.entity.SysRole;
import tech.xs.system.domain.entity.SysRoleMenu;

import java.util.List;

/**
 * 角色Service
 *
 * @author 沈家文
 * @date 2020/9/2
 */
public interface SysRoleService extends BaseSysService<SysRole> {

    /**
     * 判断角色是否被使用
     * 通常用于删除之前的判断
     *
     * @param id
     * @return
     */
    boolean isUseRole(Long id);

    void deleteDetails(DeleteRoleBo bo);

    /**
     * 根据角色Code查询角色
     *
     * @param code
     * @return
     */
    SysRole getByCode(String code);

}

package tech.xs.system.service;


import tech.xs.system.domain.entity.SysRole;
import tech.xs.system.domain.entity.SysUserRole;

import java.util.List;

/**
 * 用户角色关联Service
 *
 * @author 沈家文
 * @date 2020/9/2
 */
public interface SysUserRoleService extends BaseSysService<SysUserRole> {

    /**
     * 根据用户ID从 用户角色关联表中获取角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> listRoleByUserId(Long userId);


    /**
     * 根据用户ID从 用户角色关联表中获取角色ID列表
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<Long> listRoleIdByUserId(Long userId);

    /**
     * 根据用户ID删除用户角色
     *
     * @param userId 用户ID
     */
    void deleteByUserId(Long userId);

    /**
     * 根据用户ID删除用户角色
     *
     * @param userIds 用户ID列表
     */
    void deleteByUserId(List<Long> userIds);

    /**
     * 添加用户角色
     *
     * @param userId  用户ID
     * @param roleIds 角色ID列表
     */
    void add(Long userId, List<Long> roleIds);

}

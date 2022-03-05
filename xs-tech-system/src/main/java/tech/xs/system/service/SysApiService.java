package tech.xs.system.service;

import tech.xs.system.domain.entity.SysApi;

import java.util.List;
import java.util.Set;

/**
 * @author 沈家文
 * @date 2021/03/02 11:32
 */
public interface SysApiService extends BaseSysService<SysApi> {

    /**
     * 查询角色拥有的资源列表
     *
     * @param roleId
     * @return
     */
    List<SysApi> listUriByRoleId(Long roleId);

    /**
     * 根据角色ID获取URI资源列表
     *
     * @param roleId
     * @return
     */
    Set<String> listUriStrByRoleId(Long roleId);

    /**
     * 根据角色ID列表或对应URI资源列表
     *
     * @param roleIdList
     * @return
     */
    Set<String> listUriStrByRoleIdList(List<Long> roleIdList);

    /**
     * 从缓存中根据角色ID获取URI资源列表
     *
     * @param roleId
     * @return
     */
    Set<String> getCacheUriStrByRoleId(Long roleId);

    /**
     * 设置缓存中对应角色的URI资源列表
     *
     * @param roleId 角色ID
     * @param data   角色对应的数据
     */
    void setCacheUriStrByRoleId(Long roleId, Set<String> data);

}

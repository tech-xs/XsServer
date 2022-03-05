package tech.xs.system.service;

import tech.xs.system.domain.entity.SysPageApi;

/**
 * @author 沈家文
 * @date 2021/6/3 17:49
 */
public interface SysPageApiService extends BaseSysService<SysPageApi> {

    /**
     * 根据ID更新页面资源
     * 如果权限id为空则将对应的权限ID字段也设置为空
     *
     * @param entity
     */
    void updateByIdAndSetPermissionId(SysPageApi entity);

}

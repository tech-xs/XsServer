package tech.xs.system.service;

import tech.xs.framework.base.BaseService;

public interface SysRoleApiService extends BaseService {

    /**
     * 清理角色对应的URI缓存
     */
    void clearCacheRoleUri();

}

package tech.xs.system.service.impl;

import org.springframework.stereotype.Service;
import tech.xs.framework.core.cache.Cache;
import tech.xs.framework.util.RedisUtil;
import tech.xs.system.constant.SysCacheConstant;
import tech.xs.system.domain.entity.SysRole;
import tech.xs.system.service.SysRoleApiService;
import tech.xs.system.service.SysRoleService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysRoleApiServiceImpl implements SysRoleApiService {

    @Resource
    private Cache cache;

    @Resource
    private SysRoleService sysRoleService;

    @Override
    public void clearCacheRoleUri() {
        cache.deleteByPrefix(SysCacheConstant.ROLE_URI_RESOURCE);
    }

}

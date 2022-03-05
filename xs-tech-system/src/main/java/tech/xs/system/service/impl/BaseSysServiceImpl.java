package tech.xs.system.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.base.impl.CrudServiceImpl;
import tech.xs.system.service.*;

import javax.annotation.Resource;

/**
 * SysService 基类
 *
 * @author 沈家文
 * @date 2020/8/13
 */
public abstract class BaseSysServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends CrudServiceImpl<M, T> implements BaseSysService<T> {

    @Resource
    protected SysRoleMenuService sysRoleMenuService;
    @Resource
    protected SysUserRoleService sysUserRoleService;
    @Resource
    protected SysRoleService sysRoleService;
    @Resource
    protected SysDictValueService sysDictValueService;
    @Resource
    protected SysPagePermissionService sysPagePermissionService;
    @Resource
    protected SysApiService sysApiService;
    @Resource
    protected SysPageService sysPageService;
    @Resource
    protected SysMenuService sysMenuService;
    @Resource
    protected SysPageApiService sysPageApiService;
    @Resource
    protected SysConfigService sysConfigService;
    @Resource
    protected SysRoleMenuPagePermissionService sysRoleMenuPagePermissionService;
    @Resource
    protected SysRoleApiService sysRoleApiService;
    @Resource
    protected SysRoleMenuPageService sysRoleMenuPageService;
}

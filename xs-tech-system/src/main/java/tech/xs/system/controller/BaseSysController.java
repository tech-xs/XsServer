package tech.xs.system.controller;

import tech.xs.framework.core.cache.Cache;
import tech.xs.system.service.*;

import javax.annotation.Resource;

/**
 * StsController 基类
 *
 * @author 沈家文
 * @date 2020/8/13
 */
public class BaseSysController {

    @Resource
    protected Cache cache;

    @Resource
    protected SysService sysService;
    @Resource
    protected SysUserService sysUserService;
    @Resource
    protected SysRoleService sysRoleService;
    @Resource
    protected SysUserRoleService sysUserRoleService;
    @Resource
    protected SysDictService sysDictService;
    @Resource
    protected SysDictValueService sysDictValueService;
    @Resource
    protected SysMenuService sysMenuService;
    @Resource
    protected SysPageService sysPageService;
    @Resource
    protected SysRoleMenuService sysRoleMenuService;
    @Resource
    protected SysOrganizationService sysOrganizationService;
    @Resource
    protected SysPagePermissionService sysPagePermissionService;
    @Resource
    protected SysApiService sysApiService;
    @Resource
    protected SysPageApiService sysPageApiService;
    @Resource
    protected SysCompanyService sysCompanyService;
    @Resource
    protected SysConfigService sysConfigService;
    @Resource
    protected SysOsInfoService sysOsInfoService;
    @Resource
    protected SysRoleApiService sysRoleApiService;
    @Resource
    protected SysRoleMenuPageService sysRoleMenuPageService;
    @Resource
    protected SysRoleMenuPagePermissionService sysRoleMenuPagePermissionService;

}

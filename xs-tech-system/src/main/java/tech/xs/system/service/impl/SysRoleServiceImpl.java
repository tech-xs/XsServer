package tech.xs.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import tech.xs.framework.base.BaseEntity;
import tech.xs.system.domain.bo.role.DeleteRoleBo;
import tech.xs.system.domain.entity.*;
import org.springframework.stereotype.Service;
import tech.xs.system.dao.SysRoleDao;
import tech.xs.system.service.SysRoleService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 角色Service实现类
 *
 * @author 沈家文
 * @date 2020/9/2
 */
@Service
public class SysRoleServiceImpl extends BaseSysServiceImpl<SysRoleDao, SysRole> implements SysRoleService {

    @Resource
    private SysRoleDao sysRoleDao;

    @Override
    public boolean isUseRole(Long id) {
        long count = sysUserRoleService.count(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getRoleId, id));
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDetails(DeleteRoleBo bo) {
        sysRoleMenuPagePermissionService.delete(Wrappers.<SysRoleMenuPagePermission>lambdaQuery().in(SysRoleMenuPagePermission::getRoleId, bo.getIdList()));
        sysRoleMenuService.delete(Wrappers.<SysRoleMenu>lambdaQuery().in(SysRoleMenu::getRoleId, bo.getIdList()));
        sysRoleDao.delete(Wrappers.<SysRole>lambdaQuery().in(BaseEntity::getId, bo.getIdList()));
        sysRoleMenuPageService.delete(Wrappers.<SysRoleMenuPage>lambdaQuery().in(SysRoleMenuPage::getRoleId, bo.getIdList()));
        sysRoleApiService.clearCacheRoleUri();
    }


    @Override
    public SysRole getByCode(String code) {
        return sysRoleDao.selectOne(Wrappers.<SysRole>lambdaQuery()
                .eq(SysRole::getCode, code));
    }

}

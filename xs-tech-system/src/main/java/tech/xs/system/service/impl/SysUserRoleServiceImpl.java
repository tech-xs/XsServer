package tech.xs.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tech.xs.framework.base.BaseEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.xs.system.constant.SysRoleConstant;
import tech.xs.system.domain.entity.SysRole;
import tech.xs.system.domain.entity.SysUserRole;
import tech.xs.system.dao.SysRoleDao;
import tech.xs.system.dao.SysUserRoleDao;
import tech.xs.system.service.SysUserRoleService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户角色关联Service实现类
 *
 * @author 沈家文
 * @date 2020/9/2
 */
@Service
public class SysUserRoleServiceImpl extends BaseSysServiceImpl<SysUserRoleDao, SysUserRole> implements SysUserRoleService {

    @Resource
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public List<SysRole> listRoleByUserId(Long userId) {
        List<SysUserRole> sysUserRoles = sysUserRoleDao.selectList(Wrappers.<SysUserRole>lambdaQuery()
                .eq(SysUserRole::getUserId, userId));
        if (CollectionUtils.isEmpty(sysUserRoles)) {
            return new ArrayList<SysRole>();
        }
        Set<Long> roleIds = new HashSet<>();
        for (SysUserRole userRole : sysUserRoles) {
            roleIds.add(userRole.getRoleId());
        }
        return sysRoleService.list(Wrappers.<SysRole>lambdaQuery().in(BaseEntity::getId, roleIds));
    }

    @Override
    public List<Long> listRoleIdByUserId(Long userId) {
        List<SysUserRole> sysUserRoles = sysUserRoleDao.selectList(Wrappers.<SysUserRole>lambdaQuery()
                .eq(SysUserRole::getUserId, userId));
        if (CollectionUtils.isEmpty(sysUserRoles)) {
            return new ArrayList<>();
        }
        List<Long> roleIds = new ArrayList<>();
        sysUserRoles.forEach((userRole) -> {
            roleIds.add(userRole.getRoleId());
        });
        return roleIds;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByUserId(Long userId) {
        sysUserRoleDao.delete(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, userId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByUserId(List<Long> userIds) {
        sysUserRoleDao.delete(Wrappers.<SysUserRole>lambdaQuery().in(SysUserRole::getUserId, userIds));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Long userId, List<Long> roleIds) {
        if (CollectionUtils.isNotEmpty(roleIds)) {
            roleIds.forEach(roleId -> {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                sysUserRoleService.add(userRole);
            });
        }
    }

}

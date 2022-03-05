package tech.xs.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.transaction.annotation.Transactional;
import tech.xs.framework.base.BaseEntity;
import tech.xs.system.dao.SysPagePermissionDao;
import tech.xs.system.domain.bo.page.permission.DeletePagePermissionBo;
import tech.xs.system.domain.entity.SysPageApi;
import tech.xs.system.domain.entity.SysPagePermission;
import tech.xs.system.domain.entity.SysRoleMenuPagePermission;
import tech.xs.system.service.SysPagePermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 页面中的权限Service
 *
 * @author 沈家文
 * @date 2021/5/10 19:48
 */
@Service
public class SysPagePermissionServiceImpl extends BaseSysServiceImpl<SysPagePermissionDao, SysPagePermission> implements SysPagePermissionService {

    @Resource
    private SysPagePermissionDao sysPagePermissionDao;

    @Override
    public List<SysPagePermission> listByPageId(Long pageId) {
        return sysPagePermissionDao.selectList(Wrappers.<SysPagePermission>lambdaQuery()
                .eq(SysPagePermission::getPageId, pageId)
                .orderByDesc(BaseEntity::getCreateTime));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDetails(DeletePagePermissionBo bo) {
        sysPagePermissionDao.delete(Wrappers.<SysPagePermission>lambdaQuery()
                .in(BaseEntity::getId, bo.getIdList()));
        sysPageApiService.delete(Wrappers.<SysPageApi>lambdaQuery()
                .in(SysPageApi::getPermissionId, bo.getIdList()));
        sysRoleMenuPagePermissionService.delete(Wrappers.<SysRoleMenuPagePermission>lambdaQuery()
                .in(SysRoleMenuPagePermission::getPermissionId, bo.getIdList()));
        sysRoleApiService.clearCacheRoleUri();
    }

}

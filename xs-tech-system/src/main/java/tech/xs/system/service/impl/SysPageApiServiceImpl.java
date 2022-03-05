package tech.xs.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import tech.xs.framework.base.BaseEntity;
import tech.xs.system.dao.SysPageApiDao;
import tech.xs.system.domain.entity.SysPageApi;
import tech.xs.system.service.SysPageApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author 沈家文
 * @date 2021/6/3 17:47
 */
@Slf4j
@Service
public class SysPageApiServiceImpl extends BaseSysServiceImpl<SysPageApiDao, SysPageApi> implements SysPageApiService {

    @Resource
    private SysPageApiDao sysPageApiDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateByIdAndSetPermissionId(SysPageApi entity) {
        sysPageApiDao.updateById(entity);
        if (entity.getPermissionId() == null) {
            sysPageApiDao.update(null, Wrappers.<SysPageApi>lambdaUpdate()
                    .set(SysPageApi::getPermissionId, null)
                    .eq(BaseEntity::getId, entity.getId()));
        }
        sysRoleApiService.clearCacheRoleUri();
    }

}

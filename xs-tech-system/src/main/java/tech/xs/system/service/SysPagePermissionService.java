package tech.xs.system.service;

import tech.xs.system.domain.bo.page.permission.DeletePagePermissionBo;
import tech.xs.system.domain.entity.SysPagePermission;

import java.util.List;

/**
 * 页面权限Service
 *
 * @author 沈家文
 * @date 2021/5/10 19:46
 */
public interface SysPagePermissionService extends BaseSysService<SysPagePermission> {

    /**
     * 根据页面Id查询
     *
     * @param pageId
     * @return
     */
    List<SysPagePermission> listByPageId(Long pageId);

    void deleteDetails(DeletePagePermissionBo bo);


}

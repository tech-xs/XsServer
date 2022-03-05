package tech.xs.auth.service;

import tech.xs.auth.domain.bo.ModifyRoleAuthBo;
import tech.xs.framework.base.BaseService;
import tech.xs.system.domain.entity.SysRole;

import java.util.List;

/**
 * 授权角色Service
 *
 * @author 沈家文
 * @date 2021/2/25 11:27
 */
public interface AuthRoleService extends BaseService {

    /**
     * 修改角色授权
     *
     * @param bo
     */
    void modifyRoleAuth(ModifyRoleAuthBo bo);

}

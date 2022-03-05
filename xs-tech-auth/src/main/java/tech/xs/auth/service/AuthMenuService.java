package tech.xs.auth.service;

import tech.xs.framework.base.BaseService;
import tech.xs.system.domain.entity.SysMenu;

import java.util.List;


/**
 * 授权菜单Service
 *
 * @author 沈家文
 * @date 2021/2/25 11:27
 */
public interface AuthMenuService extends BaseService {

    /**
     * 根据用户ID获取该用户的菜单
     *
     * @param userId
     * @return
     */
    List<SysMenu> webTreeCurrMenu();

}

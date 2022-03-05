package tech.xs.auth.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import tech.xs.auth.domain.entity.AuthToken;
import tech.xs.auth.enums.AccountType;
import tech.xs.common.constant.http.HttpMethod;
import tech.xs.framework.base.BaseService;
import tech.xs.framework.domain.model.ApiResult;
import tech.xs.framework.enums.HttpMethodEnum;
import tech.xs.system.constant.SysRoleConstant;
import tech.xs.system.domain.entity.SysRole;
import tech.xs.system.domain.entity.SysUserRole;

/**
 * 授权Service
 *
 * @author imsjw
 * Create Time: 2020/8/5
 */
public interface AuthorizationService extends BaseService {

    /**
     * 授权认证[用户名密码]
     *
     * @param clientTypeId 登陆的客户端类型ID
     * @param account      登陆用户名
     * @param password     经过前端加密后的密码
     * @param accountType
     * @return
     * @throws CloneNotSupportedException
     */
    ApiResult accountPasswordAuthorization(Long clientTypeId, String account, String password, AccountType accountType) throws CloneNotSupportedException;

    /**
     * 授权认证[refreshToken]
     *
     * @param refreshToken
     * @return
     */
    ApiResult<AuthToken> tokenAuthorization(String refreshToken);

    /**
     * 退出登陆
     */
    void logout();


    /**
     * 判断用户是否拥有超级管理员授权
     *
     * @param userId
     * @return
     */
    boolean isSuperAdminAuthorization(Long userId);

}

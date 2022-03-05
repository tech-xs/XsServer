package tech.xs.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import tech.xs.auth.context.AuthContext;
import tech.xs.auth.dao.AuthClientTypeDao;
import tech.xs.auth.dao.AuthTokenDao;
import tech.xs.auth.dao.LoginUserDao;
import tech.xs.auth.domain.dto.AccountLoginDto;
import tech.xs.auth.domain.entity.AuthClientType;
import tech.xs.auth.domain.entity.AuthToken;
import tech.xs.auth.domain.entity.LoginUser;
import tech.xs.auth.enums.AccountType;
import tech.xs.auth.service.AuthTokenService;
import tech.xs.auth.service.AuthorizationService;
import tech.xs.common.constant.http.HttpMethod;
import tech.xs.framework.domain.model.ApiResult;
import tech.xs.framework.enums.BooleanEnum;
import tech.xs.framework.enums.HttpMethodEnum;
import tech.xs.system.constant.SysRoleConstant;
import tech.xs.system.domain.entity.SysRole;
import tech.xs.system.domain.entity.SysUserRole;
import tech.xs.system.service.SysRoleService;
import tech.xs.system.service.SysUserRoleService;
import tech.xs.system.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * @author imsjw
 * Create Time: 2020/8/5
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Resource
    private LoginUserDao loginUserDao;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private AuthTokenService authTokenService;
    @Resource
    private AuthClientTypeDao authClientTypeDao;
    @Resource
    private AuthTokenDao authTokenDao;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysUserRoleService sysUserRoleService;

    @Override
    public ApiResult<AccountLoginDto> accountPasswordAuthorization(Long clientTypeId, String account, String password, AccountType accountType) {
        LoginUser loginUser = null;
        if (AccountType.USER_NAME == accountType) {
            loginUser = loginUserDao.selectOne(Wrappers.<LoginUser>lambdaQuery()
                    .eq(LoginUser::getUserName, account));
        } else if (AccountType.PHONE == accountType) {
            loginUser = loginUserDao.selectOne(Wrappers.<LoginUser>lambdaQuery()
                    .eq(LoginUser::getPhone, account));
        } else if (AccountType.EMAIL == accountType) {
            loginUser = loginUserDao.selectOne(Wrappers.<LoginUser>lambdaQuery()
                    .eq(LoginUser::getEmail, account));
        }
        if (loginUser == null) {
            return ApiResult.error(1000, "[" + account + "]用户不存在");
        }
        password = sysUserService.loginPasswordDecode(password);
        if (password == null) {
            return ApiResult.error(1001, "解密错误");
        }

        String encodePassword = sysUserService.loginPasswordEncode(password);

        if (!encodePassword.equals(loginUser.getLoginPassword())) {
            return ApiResult.error(1002, "[password]不正确");
        }
        if (loginUser.getAccountStatus() == BooleanEnum.FALSE) {
            return ApiResult.error(1003, "[" + account + "]账号已禁用");
        }
        AuthClientType clientType = authClientTypeDao.selectById(clientTypeId);
        if (clientType == null) {
            return ApiResult.error(1004, "[clientId]错误");
        }
        AuthToken authToken = authTokenService.createAuthToken(loginUser, clientType);
        AuthContext.setAuthToken(authToken, loginUser.getCompanyId());

        AccountLoginDto accountLoginDto = new AccountLoginDto();
        BeanUtils.copyProperties(authToken, accountLoginDto);
        return ApiResult.success(accountLoginDto);
    }

    @Override
    public ApiResult<AuthToken> tokenAuthorization(String refreshToken) {
        AuthToken authToken = authTokenDao.selectOne(Wrappers.<AuthToken>lambdaQuery().eq(AuthToken::getRefreshToken, refreshToken));
        if (authToken == null) {
            return ApiResult.error(1000, "refreshToken不存在");
        }
        if (authToken.getRefreshTokenInvalidTime() < System.currentTimeMillis()) {
            return ApiResult.error(1001, "refreshToken已失效");
        }
        LoginUser user = loginUserDao.selectById(authToken.getUserId());
        if (user == null) {
            return ApiResult.error(1002, "用户不存在");
        }
        if (user.getAccountStatus() == BooleanEnum.FALSE) {
            return ApiResult.error(1002, "[" + user.getUserName() + "]账号已禁用");
        }
        authTokenService.refreshAuthToken(authToken);
        return ApiResult.success(authToken);
    }

    @Override
    public void logout() {
        AuthToken authToken = AuthContext.getAuthToken();
        if (authToken == null) {
            return;
        }
        authTokenService.deleteCacheAuthToken(authToken.getAccessToken());
        AuthToken updateToken = new AuthToken();
        updateToken.setId(authToken.getId());
        updateToken.setAccessToken("");
        updateToken.setAccessTokenInvalidTime(0L);
        authTokenDao.updateById(updateToken);
        AuthContext.setAuthToken(null, null);
    }

    @Override
    public boolean isSuperAdminAuthorization(Long userId) {
        SysRole role = sysRoleService.getOne(Wrappers.<SysRole>lambdaQuery()
                .eq(SysRole::getCode, SysRoleConstant.Code.SUPER_ADMIN)
                .eq(SysRole::getStatus, BooleanEnum.TRUE));
        if (role == null) {
            return false;
        }
        return sysUserRoleService.exist(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, userId)
                .eq(SysUserRole::getRoleId, role.getId()));
    }

}

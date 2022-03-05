package tech.xs.auth.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import tech.xs.auth.dao.AuthTokenDao;
import tech.xs.common.lang.StringUtils;
import tech.xs.framework.core.cache.Cache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.xs.auth.constant.AuthConstant;
import tech.xs.auth.constant.AuthCacheConstant;
import tech.xs.auth.domain.entity.AuthClientType;
import tech.xs.auth.domain.entity.AuthToken;
import tech.xs.auth.domain.entity.LoginUser;
import tech.xs.auth.service.AuthTokenService;
import tech.xs.framework.enums.BooleanEnum;
import tech.xs.framework.util.RedisUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author imsjw
 * Create Time: 2020/8/13
 */
@Service
public class AuthTokenServiceImpl extends BaseAuthServiceImpl<AuthTokenDao, AuthToken> implements AuthTokenService {

    @Resource
    private Cache cache;

    @Override
    public AuthToken getAuthToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        AuthToken authToken = getCacheAuthToken(token);
        if (authToken != null) {
            return authToken;
        }
        authToken = authTokenDao.selectOne(Wrappers.<AuthToken>lambdaQuery()
                .eq(AuthToken::getAccessToken, token));
        if (authToken != null) {
            setCacheAuthToken(authToken);
        }
        return authToken;
    }

    @Override
    public AuthToken getAuthToken(HttpServletRequest request) {
        String token = getRequestTokenStr(request);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return authTokenService.getAuthToken(token);
    }

    @Override
    public boolean verifyToken(AuthToken authToken) {
        AuthClientType authClient = authClientTypeDao.selectById(authToken.getClientTypeId());
        if (authClient == null) {
            return false;
        }
        if (authToken.getAccessTokenInvalidTime() < System.currentTimeMillis()) {
            return false;
        }
        if (BooleanEnum.FALSE == authClient.getStatus()) {
            return false;
        }
        return true;
    }


    @Override
    public void setCacheAuthToken(AuthToken authToken) {
        cache.set(RedisUtil.splicingKey(AuthCacheConstant.TOKEN, authToken.getAccessToken()), authToken, AuthCacheConstant.CACHE_TIME_TOKEN);
    }

    @Override
    public AuthToken getCacheAuthToken(String token) {
        return cache.get(RedisUtil.splicingKey(AuthCacheConstant.TOKEN, token), AuthToken.class);
    }

    @Override
    public void deleteCacheAuthToken(String token) {
        cache.delete(RedisUtil.splicingKey(AuthCacheConstant.TOKEN, token));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AuthToken createAuthToken(LoginUser loginUser, AuthClientType clientType) {
        Long userId = loginUser.getUserId();
        String accessToken = IdUtil.simpleUUID();
        String refreshToken = IdUtil.simpleUUID();
        long accessTokenInvalidTime = System.currentTimeMillis() + clientType.getAccessTokenValidity();
        long refreshTokenInvalidTime = System.currentTimeMillis() + clientType.getRefreshTokenValidity();

        AuthToken dbToken = authTokenDao.selectOne(Wrappers.<AuthToken>lambdaQuery()
                .eq(AuthToken::getUserId, loginUser.getUserId())
                .eq(AuthToken::getClientTypeId, clientType.getId()));

        AuthToken authToken = new AuthToken();
        authToken.setUserId(userId);
        authToken.setClientTypeId(clientType.getId());
        authToken.setAccessToken(accessToken);
        authToken.setRefreshToken(refreshToken);
        authToken.setAccessTokenInvalidTime(accessTokenInvalidTime);
        authToken.setRefreshTokenInvalidTime(refreshTokenInvalidTime);
        authToken.setUpdateUser(userId + "");

        if (dbToken != null) {
            deleteCacheAuthToken(dbToken.getAccessToken());
            authToken.setId(dbToken.getId());
            authTokenDao.updateById(authToken);
        } else {
            authToken.setCreateUser(userId + "");
            authTokenDao.insert(authToken);
        }
        setCacheAuthToken(authToken);
        return authToken;
    }

    @Override
    public AuthToken refreshAuthToken(AuthToken authToken) {
        AuthClientType clientType = authClientTypeDao.selectById(authToken.getClientTypeId());
        String accessToken = IdUtil.simpleUUID();
        String refreshToken = IdUtil.simpleUUID();
        long accessTokenInvalidTime = System.currentTimeMillis() + clientType.getAccessTokenValidity();
        long refreshTokenInvalidTime = System.currentTimeMillis() + clientType.getRefreshTokenValidity();

        AuthToken newAuthToken = new AuthToken();
        newAuthToken.setAccessToken(accessToken);
        newAuthToken.setRefreshToken(refreshToken);
        newAuthToken.setAccessTokenInvalidTime(accessTokenInvalidTime);
        newAuthToken.setRefreshTokenInvalidTime(refreshTokenInvalidTime);
        newAuthToken.setId(authToken.getId());

        deleteCacheAuthToken(authToken.getAccessToken());
        authTokenDao.updateById(newAuthToken);

        authToken = authTokenDao.selectById(authToken.getId());
        setCacheAuthToken(authToken);
        return authToken;
    }

    @Override
    public String getRequestTokenStr(HttpServletRequest request) {
        return request.getHeader(AuthConstant.HEADER_TOKEN_KEY);
    }

    @Override
    public void deleteTokenByAccessToken(String token) {
        deleteCacheAuthToken(token);
        authTokenDao.delete(Wrappers.<AuthToken>lambdaQuery()
                .eq(AuthToken::getAccessToken, token));
    }


    @Override
    public void resetAccessTokenInvalidTime(AuthToken authToken, AuthClientType authClientType) {
        AuthToken updateToken = new AuthToken();
        long newInvalidTime = System.currentTimeMillis() + authClientType.getAccessTokenRefreshInterval();
        updateToken.setAccessTokenInvalidTime(newInvalidTime);
        authToken.setAccessTokenInvalidTime(newInvalidTime);
        setCacheAuthToken(authToken);
        authTokenDao.update(updateToken, Wrappers.<AuthToken>lambdaQuery()
                .eq(AuthToken::getUserId, authToken.getUserId())
                .eq(AuthToken::getClientTypeId, authToken.getClientTypeId()));
    }

}

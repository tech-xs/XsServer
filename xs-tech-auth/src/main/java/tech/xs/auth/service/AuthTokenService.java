package tech.xs.auth.service;

import tech.xs.auth.domain.entity.AuthClientType;
import tech.xs.auth.domain.entity.AuthToken;
import tech.xs.auth.domain.entity.LoginUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @author imsjw
 * Create Time: 2020/8/13
 */
public interface AuthTokenService extends BaseAuthService<AuthToken> {

    /**
     * 获取当前用户授权Token信息
     *
     * @param request Http请求
     * @return 返回Token信息
     */
    AuthToken getAuthToken(HttpServletRequest request);

    /**
     * 根据token字符串获取授权Token
     * 先从缓存中查询token,如果未找到则从数据库中查询token
     *
     * @param token Token字符串
     * @return 返回Token信息
     */
    AuthToken getAuthToken(String token);

    /**
     * 验证令牌是否有效
     *
     * @param authToken Token信息
     */
    boolean verifyToken(AuthToken authToken);

    /**
     * 设置缓存中的Token
     *
     * @param authToken 授权Token
     */
    void setCacheAuthToken(AuthToken authToken);

    /**
     * 获取缓存中的AuthToken
     *
     * @param token Token信息
     * @return 返回Token信息
     */
    AuthToken getCacheAuthToken(String token);

    /**
     * 删除缓存中的AuthToken
     *
     * @param token Token字符串
     */
    void deleteCacheAuthToken(String token);

    /**
     * 创建授权Token
     * 授权Token会直接插入到数据库中
     *
     * @param loginUser 登陆用户
     * @param client    客户端类型
     * @return 返回创建成功的Token信息
     */
    AuthToken createAuthToken(LoginUser loginUser, AuthClientType client);

    /**
     * 刷新授权token 会重新设置AccessToken 和 refreshToken 并重置有效期
     *
     * @param authToken 授权token
     * @return 返回新的授权token
     */
    AuthToken refreshAuthToken(AuthToken authToken);

    /**
     * 重置AccessToken失效时间
     *
     * @param authToken  授权token
     * @param authClient 授权客户端
     */
    void resetAccessTokenInvalidTime(AuthToken authToken, AuthClientType authClient);

    /**
     * 从Request中获取token字符串
     *
     * @param request http请求
     * @return 返回token字符串
     */
    String getRequestTokenStr(HttpServletRequest request);

    /**
     * 根据Token字符串删除Token
     *
     * @param token Token字符串
     */
    void deleteTokenByAccessToken(String token);
}

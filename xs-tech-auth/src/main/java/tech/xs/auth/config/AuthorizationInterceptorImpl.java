package tech.xs.auth.config;

import tech.xs.auth.constant.ClientTypeConstant;
import tech.xs.auth.constant.LogConstant;
import tech.xs.auth.context.AuthContext;
import tech.xs.auth.domain.entity.AuthClientType;
import tech.xs.auth.domain.entity.AuthToken;
import tech.xs.auth.service.AuthClientTypeService;
import tech.xs.auth.service.AuthUriService;
import tech.xs.auth.service.AuthorizationService;
import tech.xs.common.constant.Symbol;
import tech.xs.common.constant.http.HttpMethod;
import tech.xs.common.lang.StringUtils;
import tech.xs.framework.enums.BooleanEnum;
import tech.xs.framework.enums.HttpMethodEnum;
import tech.xs.framework.exception.AuthException;
import tech.xs.auth.security.filter.CorsFilter;
import tech.xs.auth.service.AuthTokenService;
import tech.xs.framework.auth.interceptor.AuthorizationInterceptor;
import tech.xs.framework.context.XsContext;
import tech.xs.framework.enums.ResultEnum;
import tech.xs.system.domain.entity.SysUser;
import tech.xs.system.service.SysApiService;
import tech.xs.system.service.SysUserRoleService;
import tech.xs.system.service.SysUserService;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 认证授权拦截器
 *
 * @author 沈家文
 * @date 2020/12/3 14:38
 */
@Component
public class AuthorizationInterceptorImpl extends HandlerInterceptorAdapter implements AuthorizationInterceptor {

    private static final Set<String> BROWSER_COMMON_URI = new HashSet<>();

    static {
        BROWSER_COMMON_URI.add("GET:/auth/menu/web/tree/current");
        BROWSER_COMMON_URI.add("GET:/sys/user/current");
        BROWSER_COMMON_URI.add("POST:/sys/user/current/modify/password");

        BROWSER_COMMON_URI.add("GET:/auth/role/list/current");
        BROWSER_COMMON_URI.add("GET:/auth/permission/list/current");
        BROWSER_COMMON_URI.add("GET:/sys/dictValue/list/byDictCode");
        BROWSER_COMMON_URI.add("POST:/sys/user/current/modify/avatar");
    }

    @Lazy
    @Resource
    private SysUserService sysUserService;
    @Lazy
    @Resource
    private SysApiService sysApiService;
    @Lazy
    @Resource
    private SysUserRoleService sysUserRoleService;

    @Lazy
    @Resource
    private AuthTokenService authTokenService;
    @Lazy
    @Resource
    private AuthClientTypeService authClientTypeService;
    @Lazy
    @Resource
    private AuthorizationService authorizationService;
    @Lazy
    @Resource
    private AuthUriService authUriService;

    @Lazy
    @Resource
    private CorsFilter corsFilter;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!corsFilter.filter(request, response)) {
            return false;
        }
        AuthContext.setAuthToken(null, null);
        AuthToken authToken = authTokenService.getAuthToken(request);
        if (authToken != null) {
            MDC.put(LogConstant.MDC_KEY, authToken.getId() + "");
            if (!authTokenService.verifyToken(authToken)) {
                throw new AuthException(ResultEnum.loginInvalid);
            }
            autoRefreshAccessTokenInvalidTime(authToken);
            SysUser user = sysUserService.getById(authToken.getUserId());
            if (user != null) {
                if (BooleanEnum.FALSE.equals(user.getAccountStatus())) {
                    throw new AuthException("账户已禁用", false);
                }
                AuthContext.setAuthToken(authToken, user.getCompanyId());
                if (authorizationService.isSuperAdminAuthorization(user.getId())) {
                    return true;
                }
            } else {
                authTokenService.deleteTokenByAccessToken(authToken.getAccessToken());
                if (isWhiteSet(request)) {
                    return true;
                } else {
                    throw new AuthException(ResultEnum.noLogin, false);
                }
            }
            if (isWhiteSet(request)) {
                return true;
            }
            if (ClientTypeConstant.BROWSER == authToken.getClientTypeId()) {
                String requestUri = request.getRequestURI();
                String method = request.getMethod();
                if (requestUri == null || "".equals(requestUri) || method == null || "".equals(method)) {
                    throw new AuthException(ResultEnum.permissionDenied);
                }
                method = method.toUpperCase();
                String checkStr = method + Symbol.COLON + requestUri;
                if (BROWSER_COMMON_URI.contains(checkStr)) {
                    return true;
                } else if (isAuthUri(authToken, checkStr)) {
                    return true;
                } else {
                    throw new AuthException(ResultEnum.permissionDenied);
                }
            }
            return false;
        } else {
            MDC.put(LogConstant.MDC_KEY, "");
            if (isWhiteSet(request)) {
                return true;
            }
            throw new AuthException(ResultEnum.noLogin, false);
        }
    }

    private void autoRefreshAccessTokenInvalidTime(AuthToken authToken) {
        AuthClientType authClient = authClientTypeService.getById(authToken.getClientTypeId());
        if (authToken.getAccessTokenInvalidTime() - System.currentTimeMillis() <= authClient.getAccessTokenRefreshInterval()) {
            authTokenService.resetAccessTokenInvalidTime(authToken, authClient);
        }
    }


    /**
     * 是否是授权可访问的Uri
     *
     * @param checkStr
     * @return
     */
    private boolean isAuthUri(AuthToken authToken, String checkStr) {
        List<Long> roleIdList = sysUserRoleService.listRoleIdByUserId(authToken.getUserId());
        Set<String> uriSet = sysApiService.listUriStrByRoleIdList(roleIdList);
        return uriSet.contains(checkStr);
    }

    /**
     * 判断是否是白名单中的URL
     *
     * @param request
     * @return
     */
    public boolean isWhiteSet(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        if (requestUri == null) {
            return false;
        }
        Set<String> uriWhite = authUriService.getUriWhite(HttpMethodEnum.ALL);
        for (String uri : uriWhite) {
            if (requestUri.startsWith(uri)) {
                return true;
            }
        }
        uriWhite = authUriService.getUriWhite(HttpMethodEnum.getByCode(request.getMethod()));
        for (String uri : uriWhite) {
            if (requestUri.startsWith(uri)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        XsContext.remove();
    }
}

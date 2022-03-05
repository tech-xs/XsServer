package tech.xs.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import tech.xs.auth.constant.AuthGrantTypeConstant;
import tech.xs.auth.constant.ClientTypeConstant;
import tech.xs.auth.context.AuthContext;
import tech.xs.auth.domain.bo.CheckAccessTokenBo;
import tech.xs.auth.domain.bo.LoginBo;
import tech.xs.auth.domain.entity.AuthToken;
import tech.xs.common.lang.StringUtils;
import tech.xs.framework.domain.model.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * 授权认证Controller
 *
 * @author 沈家文
 * @date 2020/10/12
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/auth")
@Api(tags = "认证授权")
public class AuthorizationController extends BaseAuthController {


    /**
     * 用户登陆
     */
    @PostMapping("/login")
    @ApiOperation(value="登陆认证",notes = "登陆认证")
    public ApiResult token(@Valid @RequestBody LoginBo bo) throws Exception {
        //设置默认值
        switch (bo.getGrantType()) {
            case AuthGrantTypeConstant.PASSWORD: {
                if (StringUtils.isBlank(bo.getAccount())) {
                    return ApiResult.invalidParameter("account");
                }
                if (StringUtils.isBlank(bo.getPassword())) {
                    return ApiResult.invalidParameter("password");
                }
                if (bo.getAccountType() == null) {
                    return ApiResult.invalidParameter("accountType");
                }
                return authorizationService.accountPasswordAuthorization(bo.getClientTypeId(), bo.getAccount(), bo.getPassword(), bo.getAccountType());
            }
            case AuthGrantTypeConstant.TOKEN: {
                if (StringUtils.isBlank(bo.getRefreshToken())) {
                    return ApiResult.invalidParameter("refreshToken");
                }
                return authorizationService.tokenAuthorization(bo.getRefreshToken());
            }
            default: {
                return ApiResult.invalidParameter("grantType");
            }
        }
    }

    @PostMapping("/check/accessToken")
    public ApiResult checkAccessToken(@RequestBody @Valid CheckAccessTokenBo bo) {
        AuthToken token = authTokenService.getAuthToken(bo.getAccessToken());
        if (token != null && authTokenService.verifyToken(token)) {
            return ApiResult.success();
        } else {
            return ApiResult.error(1000, "token invalid");
        }
    }

    /**
     * 退出登陆
     *
     * @return
     */
    @PostMapping("/logout")
    public ApiResult logout() {
        AuthToken authToken = AuthContext.getAuthToken();
        if (authToken == null) {
            return ApiResult.success();
        }
        if (ClientTypeConstant.BROWSER != authToken.getClientTypeId()) {
            return ApiResult.error(1000, "客户端类型错误");
        }
        authorizationService.logout();
        return ApiResult.success();
    }

}

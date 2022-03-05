package tech.xs.auth.controller;

import tech.xs.auth.domain.dto.GetCurrTokenDto;
import tech.xs.auth.domain.entity.AuthToken;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.xs.auth.context.AuthContext;
import tech.xs.framework.domain.model.ApiResult;

/**
 * Token Controller
 *
 * @author 沈家文
 * @date 2020/8/26
 */
@Validated
@RestController
@RequestMapping("/auth/token")
public class AuthTokenController extends BaseAuthController {

    /**
     * 获取当前用户Token
     *
     * @return 当前用户Token信息
     */
    @GetMapping("/current")
    public ApiResult getCurrToken() {
        AuthToken authToken = AuthContext.getAuthToken();
        GetCurrTokenDto res = new GetCurrTokenDto();
        BeanUtils.copyProperties(authToken, res);
        return ApiResult.success(res);
    }

}

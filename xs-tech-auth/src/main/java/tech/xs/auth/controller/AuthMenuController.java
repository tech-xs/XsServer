package tech.xs.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.xs.auth.service.AuthMenuService;
import tech.xs.framework.annotation.doc.Api;
import tech.xs.framework.context.XsContext;
import tech.xs.framework.domain.model.ApiResult;

import javax.annotation.Resource;

/**
 * Auth对应的菜单Controller
 *
 * @author 沈家文
 * @date 2021/4/15 16:37
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/auth/menu")
public class AuthMenuController {

    @Resource
    private AuthMenuService authMenuService;

    @Api(name = "获取当前用户的菜单树")
    @GetMapping("/web/tree/current")
    public ApiResult webTreeCurrMenu() {
        return ApiResult.success(authMenuService.webTreeCurrMenu());
    }

}

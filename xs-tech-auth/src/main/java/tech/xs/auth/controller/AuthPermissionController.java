package tech.xs.auth.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.xs.framework.domain.model.ApiResult;

import java.util.ArrayList;

/**
 * 权限Controller
 *
 * @author 沈家文
 * @date 2021/4/14 16:34
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/auth/permission")
public class AuthPermissionController extends BaseAuthController {

    @GetMapping("/list/current")
    public ApiResult listCurrent() {
        return ApiResult.success(new ArrayList<>());
    }

}

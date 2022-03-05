package tech.xs.auth.controller;

import org.springframework.stereotype.Controller;
import tech.xs.auth.service.AuthClientTypeService;
import tech.xs.auth.service.AuthTokenService;
import tech.xs.auth.service.AuthorizationService;

import javax.annotation.Resource;

/**
 * 授权Controller基类
 *
 * @author 沈家文
 * @date 2020/10/15
 */
public class BaseAuthController {

    @Resource
    protected AuthClientTypeService authClientService;

    @Resource
    protected AuthTokenService authTokenService;

    @Resource
    protected AuthorizationService authorizationService;

}

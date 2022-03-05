package tech.xs.auth.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import tech.xs.auth.dao.AuthClientTypeDao;
import tech.xs.auth.dao.AuthTokenDao;
import tech.xs.auth.service.AuthClientTypeService;
import tech.xs.auth.service.AuthTokenService;
import tech.xs.auth.service.BaseAuthService;
import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.base.impl.BaseServiceImpl;
import tech.xs.framework.base.impl.CrudServiceImpl;

import javax.annotation.Resource;

/**
 * 授权Service基类
 *
 * @author imsjw
 * Create Time: 2020/7/30
 */
public abstract class BaseAuthServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends CrudServiceImpl<M, T> implements BaseAuthService<T> {

    @Resource
    protected AuthTokenDao authTokenDao;
    @Resource
    protected AuthClientTypeDao authClientTypeDao;
    @Resource
    protected AuthTokenService authTokenService;

}

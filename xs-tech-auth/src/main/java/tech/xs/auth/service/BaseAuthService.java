package tech.xs.auth.service;

import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.base.BaseService;
import tech.xs.framework.base.CrudService;

/**
 * 授权Service基类
 *
 * @author imsjw
 * Create Time: 2020/10/15
 */
public interface BaseAuthService<T extends BaseEntity> extends CrudService<T> {
}

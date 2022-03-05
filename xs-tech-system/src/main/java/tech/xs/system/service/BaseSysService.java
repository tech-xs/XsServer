package tech.xs.system.service;

import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.base.BaseService;
import tech.xs.framework.base.CrudService;

/**
 * SysService基类
 *
 * @author 沈家文
 * @date 2020/10/15
 */
public interface BaseSysService<T extends BaseEntity> extends CrudService<T> {
}

package tech.xs.generator.service;

import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.base.CrudService;

/**
 * 生成代码的Service基类
 *
 * @author imsjw
 * Create Time: 2020/7/31
 */
public interface BaseGeneratorService<T extends BaseEntity> extends CrudService<T> {
}

package tech.xs.doc.service;


import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.base.CrudService;

/**
 * 文档Service基类
 *
 * @author 沈家文
 * @date 2021/7/8 17:09
 */
public interface BaseDocService<T extends BaseEntity> extends CrudService<T> {
}

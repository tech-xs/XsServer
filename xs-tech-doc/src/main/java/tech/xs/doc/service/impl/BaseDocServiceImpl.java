package tech.xs.doc.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import tech.xs.doc.service.*;
import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.base.impl.CrudServiceImpl;


import javax.annotation.Resource;

/**
 * 文档基类实现类
 *
 * @author 沈家文
 * @date 2021/7/8 17:09
 */
public class BaseDocServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends CrudServiceImpl<M, T> implements BaseDocService<T> {

    @Resource
    protected DocApiParmService docApiParmService;

    @Resource
    protected DocApiParmExampleService docApiParmExampleService;

    @Resource
    protected DocApiBodyService docApiBodyService;

    @Resource
    protected DocApiBodyExampleService docApiBodyExampleService;

    @Resource
    protected DocApiResponseCodeService docApiResponseCodeService;

    @Resource
    protected DocApiParmConstraintService docApiParmConstraintService;

    @Resource
    protected DocConstService docConstService;

    @Resource
    protected DocConstValueService docConstValueService;

    @Resource
    protected DocApiParmConstService docApiParmConstService;

}

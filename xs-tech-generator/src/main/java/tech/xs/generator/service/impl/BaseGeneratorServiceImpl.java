package tech.xs.generator.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import tech.xs.framework.base.BaseEntity;
import org.springframework.beans.factory.annotation.Value;
import tech.xs.framework.base.impl.CrudServiceImpl;
import tech.xs.generator.dao.GeneratorColumnDao;
import tech.xs.generator.dao.GeneratorTableDao;
import tech.xs.generator.service.BaseGeneratorService;
import tech.xs.generator.service.DatabaseService;

import javax.annotation.Resource;

/**
 * @author imsjw
 * Create Time: 2020/7/31
 */
public abstract class BaseGeneratorServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends CrudServiceImpl<M, T> implements BaseGeneratorService<T> {

    @Value("${generator.author:author}")
    protected String generatorJavaFileAuthor;

    @Resource
    protected GeneratorColumnDao generatorColumnDao;

    @Resource(name = "${generator.db-type:default}")
    protected DatabaseService databaseService;

    @Resource
    protected GeneratorColumnDao generatorColumnMapper;

    @Resource
    protected GeneratorTableDao generatorTableMapper;

}

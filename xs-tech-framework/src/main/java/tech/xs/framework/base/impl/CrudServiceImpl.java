package tech.xs.framework.base.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tech.xs.framework.base.BaseEntity;
import tech.xs.framework.base.CrudService;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * Service 基类
 *
 * @author 沈家文
 * @date 2020/9/2
 */
public class CrudServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> implements CrudService<T> {

    protected Log log = LogFactory.getLog(getClass());

    @Autowired
    private M baseMapper;

    private Class<T> entityClass;

    private Class<M> mapperClass = currentMapperClass();

    @PostConstruct
    private void init() {
        try {
            Type[] actualTypeArguments = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
            entityClass = (Class<T>) actualTypeArguments[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(T entity) {
        baseMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(List<T> entityList) {
        String sqlStatement = getSqlStatement(SqlMethod.INSERT_ONE);
        executeBatch(entityList, DEFAULT_BATCH_SIZE, (sqlSession, entity) -> sqlSession.insert(sqlStatement, entity));
    }

    @Override
    public void deleteById(Long id) {
        baseMapper.deleteById(id);
    }

    @Override
    public void deleteByIdList(Collection<Long> idList) {
        baseMapper.delete(Wrappers.<T>lambdaQuery().setEntityClass(entityClass).in(T::getId, idList));
    }

    @Override
    public void delete(Wrapper<T> queryWrapper) {
        baseMapper.delete(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Collection<T> entityList) {
        Set<Long> idSet = new HashSet<>();
        for (T t : entityList) {
            idSet.add(t.getId());
        }
        if (idSet.isEmpty()) {
            return;
        }
        baseMapper.delete(Wrappers.<T>lambdaQuery().setEntityClass(entityClass).in(T::getId, idSet));
    }


    @Override
    public void updateById(T entity) {
        baseMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(Collection<T> entityList) {
        for (T entity : entityList) {
            baseMapper.updateById(entity);
        }
    }

    @Override
    public void update(T entity, Wrapper<T> queryWrapper) {
        baseMapper.update(entity, queryWrapper);
    }

    @Override
    public List<T> list() {
        return baseMapper.selectList(Wrappers.<T>lambdaQuery());
    }

    @Override
    public List<T> list(Wrapper<T> queryWrapper) {
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<T> listByIds(List<Long> idList) {
        return baseMapper.selectBatchIds(idList);
    }

    @Override
    public Page<T> page(Page<T> page, Wrapper<T> queryWrapper) {
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public T getById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public T getOne(Wrapper<T> queryWrapper) {
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean existById(Long id) {
        Long count = baseMapper.selectCount(Wrappers.<T>lambdaQuery().setEntityClass(entityClass).eq(T::getId, id));
        return count > 0;
    }

    @Override
    public boolean existByIds(Collection<Long> ids) {
        ids = new HashSet<Long>(ids);
        Long count = baseMapper.selectCount(Wrappers.<T>lambdaQuery().setEntityClass(entityClass).in(T::getId, ids));
        return count.intValue() == ids.size();
    }

    @Override
    public boolean exist(Wrapper<T> queryWrapper) {
        Long count = baseMapper.selectCount(queryWrapper);
        return count > 0;
    }

    @Override
    public long count(Wrapper<T> queryWrapper) {
        return baseMapper.selectCount(queryWrapper);
    }


    private <E> boolean executeBatch(Collection<E> list, int batchSize, BiConsumer<SqlSession, E> consumer) {
        return SqlHelper.executeBatch(this.entityClass, this.log, list, batchSize, consumer);
    }

    private String getSqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.getSqlStatement(mapperClass, sqlMethod);
    }

    private Class<M> currentMapperClass() {
        return (Class<M>) ReflectionKit.getSuperClassGenericType(this.getClass(), CrudServiceImpl.class, 0);
    }

}

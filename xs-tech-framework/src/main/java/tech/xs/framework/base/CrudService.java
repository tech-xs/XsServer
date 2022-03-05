package tech.xs.framework.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Collection;
import java.util.List;

/**
 * Crud Service基类
 *
 * @author 沈家文
 * @date 2020/10/15
 */
public interface CrudService<T extends BaseEntity> extends BaseService {

    int DEFAULT_BATCH_SIZE = 1000;

    /**
     * 添加
     *
     * @param entity 实体类
     * @return
     */
    void add(T entity);

    /**
     * 添加
     *
     * @param entityList 实体类列表
     * @return
     */
    void add(List<T> entityList);

    /**
     * 根据ID删除
     *
     * @param id 实体类id
     */
    void deleteById(Long id);

    /**
     * 根据id删除
     *
     * @param idList 实体类id列表
     */
    void deleteByIdList(Collection<Long> idList);

    /**
     * 删除
     *
     * @param queryWrapper 删除条件
     */
    void delete(Wrapper<T> queryWrapper);

    /**
     * 根据ID删除
     *
     * @param entityList 根据ID删除
     */
    void deleteById(Collection<T> entityList);

    /**
     * 根据ID更新
     *
     * @param entity 更新的实体类
     */
    void updateById(T entity);

    /**
     * 根据ID更新
     *
     * @param entityList 需要更新的列表
     */
    void updateById(Collection<T> entityList);

    /**
     * 更新
     *
     * @param queryWrapper 更新的条件
     * @param entity       更新的实体类
     */
    void update(T entity, Wrapper<T> queryWrapper);

    /**
     * 查询全部
     *
     * @return 返回查询到的结果集
     */
    List<T> list();

    /**
     * 查询列表
     *
     * @param queryWrapper 查询条件
     * @return 返回查询到的结果集
     */
    List<T> list(Wrapper<T> queryWrapper);

    /**
     * 根据ID查询
     *
     * @param idList id列表
     * @return 返回查询到的结果集
     */
    List<T> listByIds(List<Long> idList);

    /**
     * 分页查询
     *
     * @param page
     * @param queryWrapper
     * @return
     */
    Page<T> page(Page<T> page, Wrapper<T> queryWrapper);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    T getById(Long id);

    /**
     * 查询第一个
     *
     * @param queryWrapper
     * @return
     */
    T getOne(Wrapper<T> queryWrapper);


    /**
     * 根据ID判断是否存在
     *
     * @param id
     * @return
     */
    boolean existById(Long id);

    /**
     * 判断列表中的Id是否存在,只要有一个不存在则返回false
     *
     * @param ids
     * @return
     */
    boolean existByIds(Collection<Long> ids);

    /**
     * 判断是否存在
     *
     * @param queryWrapper
     * @return
     */
    boolean exist(Wrapper<T> queryWrapper);

    /**
     * 计数
     *
     * @param queryWrapper
     * @return
     */
    long count(Wrapper<T> queryWrapper);


}

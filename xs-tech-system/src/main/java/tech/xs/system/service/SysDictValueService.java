package tech.xs.system.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tech.xs.system.domain.entity.SysDict;
import tech.xs.system.domain.entity.SysDictValue;

import java.util.List;

/**
 * 字典值Service
 *
 * @author 沈家文
 * @date 2020/10/21
 */
public interface SysDictValueService extends BaseSysService<SysDictValue> {

    /**
     * 根据字段
     *
     * @param dictId
     * @return
     */
    List<SysDictValue> tree(Long dictId);

    /**
     * 构建树形结构
     *
     * @param nodeList
     */
    void buildTree(List<SysDictValue> nodeList);

    /**
     * 根据ID更新字典,并设置对应的falterId
     * 如果fatherId为空则将数据库中的fatherId字段也设置为空
     *
     * @param dictValue
     */
    void updateByIdSetFatherId(SysDictValue dictValue);

}

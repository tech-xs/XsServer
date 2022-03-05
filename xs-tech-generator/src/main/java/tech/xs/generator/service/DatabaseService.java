package tech.xs.generator.service;

import tech.xs.framework.base.BaseService;
import tech.xs.generator.domain.entity.GeneratorColumn;
import tech.xs.generator.domain.entity.GeneratorTable;

import java.util.List;

/**
 * DatabaseService
 *
 * @author imsjw
 * Create Time: 2021/1/4
 */
public interface DatabaseService extends BaseService {

    /**
     * 获取当前数据名
     *
     * @return
     */
    String getCurrSchemaName();

    /**
     * 查询指定数据库中表信息
     *
     * @param schemaName
     * @return
     */
    List<GeneratorTable> getTableList(String schemaName);

    /**
     * 获取指定数据库中指定表的字段信息
     *
     * @param schemaName
     * @param tableName
     * @return
     */
    List<GeneratorColumn> getColumnList(String schemaName, String tableName);

    /**
     * 1.清空当前表结构信息表结构信息
     * 2.将当前表结构信息存放到数据库中
     */
    void resetTableInfo();

    /**
     * 字段类型转换
     *
     * @param fieldType
     * @return
     */
    String fieldTypeConvert(String fieldType);

}

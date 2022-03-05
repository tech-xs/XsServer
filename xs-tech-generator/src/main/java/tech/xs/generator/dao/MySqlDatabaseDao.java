package tech.xs.generator.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tech.xs.generator.domain.entity.GeneratorColumn;
import tech.xs.generator.domain.entity.GeneratorTable;

import java.util.List;

/**
 * MySQL数据库Dao
 *
 * @author imsjw
 * Create Time: 2021/2/15
 */
public interface MySqlDatabaseDao {

    /**
     * 查询指定数据库的表信息
     *
     * @param schemaName
     * @return
     */
    List<GeneratorTable> listGeneratorTable(String schemaName);

    /**
     * 查询指定数据库的列信息
     *
     * @param schemaName
     * @param tableName
     * @return
     */
    List<GeneratorColumn> listGeneratorColumn(
            @Param("schemaName") String schemaName,
            @Param("tableName") String tableName);

}

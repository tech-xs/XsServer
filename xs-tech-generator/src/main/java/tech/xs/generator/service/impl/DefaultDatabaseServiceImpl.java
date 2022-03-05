package tech.xs.generator.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.xs.common.lang.StringUtils;
import tech.xs.framework.constant.DefaultConstant;
import tech.xs.generator.constant.DataBaseTypeConstant;
import tech.xs.generator.constant.FieldTypeConstant;
import tech.xs.generator.domain.entity.GeneratorColumn;
import tech.xs.generator.domain.entity.GeneratorTable;
import tech.xs.generator.service.DatabaseService;
import tech.xs.generator.service.GeneratorColumnService;
import tech.xs.generator.service.GeneratorTableService;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseService实现类
 *
 * @author imsjw
 * Create Time: 2021/1/4
 */
@Slf4j
@Service(DataBaseTypeConstant.DEFAULT)
public class DefaultDatabaseServiceImpl implements DatabaseService {

    @Resource
    private DataSource dataSource;

    @Resource
    private GeneratorTableService generatorTableService;
    @Resource
    private GeneratorColumnService generatorColumnService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetTableInfo() {
        generatorTableService.delete(Wrappers.lambdaQuery());
        generatorColumnService.delete(Wrappers.lambdaQuery());
        String dbName = getCurrSchemaName();
        List<GeneratorTable> tableList = getTableList(dbName);
        if (CollectionUtils.isEmpty(tableList)) {
            return;
        }
        for (GeneratorTable table : tableList) {
            if (table.getName().endsWith("_delete")) {
                continue;
            }
            table.setCreateUser(DefaultConstant.SYS_USER);
            table.setUpdateUser(DefaultConstant.SYS_USER);
            generatorTableService.add(table);
            List<GeneratorColumn> columnList = getColumnList(dbName, table.getName());
            for (GeneratorColumn column : columnList) {
                column.setFieldType(fieldTypeConvert(column.getFieldType()));
                column.setTableId(table.getId());
                column.setCreateUser(DefaultConstant.SYS_USER);
                column.setUpdateUser(DefaultConstant.SYS_USER);
                generatorColumnService.add(column);
            }
        }
    }

    @Override
    public String fieldTypeConvert(String fieldType) {
        switch (fieldType) {
            case "BIT":
                return FieldTypeConstant.BIT;
            case "INT":
                return FieldTypeConstant.INT;
            case "BIGINT":
                return FieldTypeConstant.BIGINT;
            case "DATETIME":
                return FieldTypeConstant.DATETIME;
            case "CHAR":
                return FieldTypeConstant.CHAR;
            case "VARCHAR":
                return FieldTypeConstant.VARCHAR;
            case "TEXT":
                return FieldTypeConstant.TEXT;
            default:
                return null;
        }
    }

    @Override
    public List<GeneratorTable> getTableList(String schemaName) {
        if (StringUtils.isBlank(schemaName)) {
            log.error("获取数据库[" + schemaName + "]中的表失败 数据库名不能为空");
            return null;
        }
        List<GeneratorTable> tableList = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            if (metaData == null) {
                log.error("获取数据库[" + schemaName + "]中的表失败");
                return tableList;
            }
            ResultSet tableSet = metaData.getTables(null, schemaName, null, new String[]{"TABLE"});
            while (tableSet.next()) {
                GeneratorTable table = new GeneratorTable();
                String tableName = tableSet.getString("TABLE_NAME");
                String tableRemarks = tableSet.getString("REMARKS");
                table.setName(tableName);
                table.setRemark(tableRemarks);
                tableList.add(table);
            }
            tableSet.close();
            connection.close();
        } catch (Exception e) {
            log.error("获取数据库[" + schemaName + "]中的表失败");
            e.printStackTrace();
        }
        return tableList;
    }

    @Override
    public List<GeneratorColumn> getColumnList(String schemaName, String tableName) {
        List<GeneratorColumn> columnList = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            if (metaData == null) {
                log.error("获取数据库[" + schemaName + "]表[" + tableName + "]中的列失败");
                return columnList;
            }
            ResultSet columnSet = metaData.getColumns(null, schemaName, tableName, null);
            while (columnSet.next()) {
                GeneratorColumn column = new GeneratorColumn();
                column.setName(columnSet.getString("COLUMN_NAME"));
                column.setFieldType(columnSet.getString("TYPE_NAME"));
                column.setFieldLength(columnSet.getLong("COLUMN_SIZE"));
                column.setFieldRemark(columnSet.getString("REMARKS"));
                columnList.add(column);
            }
            columnSet.close();
            connection.close();
        } catch (Exception e) {
            log.error("获取数据库[" + schemaName + "]表[" + tableName + "]中的列失败");
            e.printStackTrace();
        }
        return columnList;
    }

    @Override
    public String getCurrSchemaName() {
        try {
            DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
            if (metaData == null) {
                log.error("获取数据库名称失败");
                return null;
            }
            String url = metaData.getURL();
            int endIndex = url.indexOf("?");
            if (endIndex > 0) {
                url = url.substring(0, endIndex);
            }
            int startIndex = url.lastIndexOf("/");
            if (startIndex < 0) {
                log.error("获取数据库名称失败");
                return null;
            }
            String schemaName = url.substring(startIndex + 1);
            return schemaName;
        } catch (Exception e) {
            log.error("获取数据库名称失败");
            e.printStackTrace();
        }
        return null;
    }

    private void printResultSet(ResultSet resultSet) {
        try {
            log.info("-------------------------------");
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String name = metaData.getColumnName(i);
                String value = resultSet.getString(i);
                log.info("key:" + name + " value:" + value);
            }
            log.info("-------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

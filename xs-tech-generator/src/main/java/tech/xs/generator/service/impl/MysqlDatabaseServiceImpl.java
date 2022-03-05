package tech.xs.generator.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.xs.generator.constant.DataBaseTypeConstant;
import tech.xs.generator.constant.FieldTypeConstant;
import tech.xs.generator.dao.MySqlDatabaseDao;
import tech.xs.generator.domain.entity.GeneratorColumn;
import tech.xs.generator.domain.entity.GeneratorTable;
import tech.xs.generator.service.DatabaseService;

import javax.annotation.Resource;
import java.util.List;


/**
 * MySql 数据库信息获取实现类
 *
 * @author imsjw
 * Create Time: 2021/1/10
 */
@Slf4j
@Service(DataBaseTypeConstant.MYSQL)
public class MysqlDatabaseServiceImpl extends DefaultDatabaseServiceImpl implements DatabaseService {

    @Resource
    private MySqlDatabaseDao mySqlDatabaseDao;

    @Override
    public List<GeneratorTable> getTableList(String schemaName) {
        return mySqlDatabaseDao.listGeneratorTable(schemaName);
    }


    @Override
    public List<GeneratorColumn> getColumnList(String schemaName, String tableName) {
        return mySqlDatabaseDao.listGeneratorColumn(schemaName, tableName);
    }

    @Override
    public String fieldTypeConvert(String fieldType) {
        switch (fieldType) {
            case "bit":
            case "tinyint": {
                return FieldTypeConstant.BIT;
            }
            case "int": {
                return FieldTypeConstant.INT;
            }
            case "bigint": {
                return FieldTypeConstant.BIGINT;
            }
            case "double": {
                return FieldTypeConstant.DOUBLE;
            }
            case "timestamp":
            case "datetime": {
                return FieldTypeConstant.DATETIME;
            }
            case "char": {
                return FieldTypeConstant.CHAR;
            }
            case "varchar": {
                return FieldTypeConstant.VARCHAR;
            }
            case "text":
            case "longtext": {
                return FieldTypeConstant.TEXT;
            }
            case "longblob": {
                return FieldTypeConstant.BYTE_ARRAY;
            }
            default:
                return null;
        }
    }
}


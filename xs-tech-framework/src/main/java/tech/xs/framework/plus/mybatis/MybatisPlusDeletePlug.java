package tech.xs.framework.plus.mybatis;


import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.delete.Delete;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tech.xs.common.constant.DataBaseConstant;
import tech.xs.common.constant.Symbol;
import tech.xs.common.lang.StringUtils;
import tech.xs.framework.util.SpringUtil;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义Mybatis Plus删除插件
 *
 * @author 沈家文
 * @date 2020/10/20
 */
@Component
@Slf4j
public class MybatisPlusDeletePlug implements InnerInterceptor {

    @Value("${mybatis-plus.delete-plus.ignore:}")
    private String ignoreYmlConfigStr;

    private static final Set<String> ignoreTableSet = new HashSet<>();

    @PostConstruct
    public void init() {
        if (StringUtils.isNotBlank(ignoreYmlConfigStr)) {
            String[] split = ignoreYmlConfigStr.trim().split(",");
            for (String item : split) {
                if (StringUtils.isNotBlank(item)) {
                    ignoreTableSet.add(item);
                }
            }
        }
        String content = SpringUtil.getResourceStringContent("mybatis-delete.txt");
        if (StringUtils.isNotBlank(content)) {
            log.info("mybatis-delete.txt文件读取成功");
            String[] contentList = content.split(Symbol.LINEFEED);
            for (String item : contentList) {
                item = item.trim();
                if (StringUtils.isNotBlank(item)) {
                    ignoreTableSet.add(item);
                }
            }
        } else {
            log.info("mybatis-delete.txt文件读取为空");
        }
    }

    @SneakyThrows
    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        PluginUtils.MPStatementHandler mpSh = PluginUtils.mpStatementHandler(sh);
        MappedStatement ms = mpSh.mappedStatement();
        SqlCommandType sct = ms.getSqlCommandType();
        if (sct != SqlCommandType.DELETE) {
            return;
        }
        BoundSql deleteBoundSql = sh.getBoundSql();
        String sql = deleteBoundSql.getSql();
        Delete delete = (Delete) CCJSqlParserUtil.parse(sql);
        if (ignoreTableSet.contains(delete.getTable().getName().replace(DataBaseConstant.MY_SQL_ESCAPE_CHARACTER, ""))) {
            return;
        }
        //构建sql
        String installSqlStr = builderInstallSql(delete, sql);
        BoundSql installSql = new BoundSql(ms.getConfiguration(), installSqlStr, deleteBoundSql.getParameterMappings(), deleteBoundSql.getParameterObject());

        //构建MappedStatement
        MappedStatement installMs = builderInstallMappedStatement(ms, installSql);
        mpSh.executor().update(installMs, deleteBoundSql.getParameterObject());
    }

    /**
     * 构建MappedStatement
     *
     * @param ms
     * @param boundSql
     * @return
     */
    private MappedStatement builderInstallMappedStatement(MappedStatement ms, BoundSql boundSql) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId() + "_installDelete", new SqlSource() {
            @Override
            public BoundSql getBoundSql(Object parameterObject) {
                return boundSql;
            }
        }, SqlCommandType.INSERT);
        builder.resource(ms.getResource());
        builder.statementType(StatementType.PREPARED);
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.flushCacheRequired(true);
        return builder.build();
    }

    /**
     * 构建SQL
     *
     * @param deleteSql 删除sql
     * @return 返回sql
     */
    private String builderInstallSql(Delete delete, String deleteSql) {
        String installSql = deleteSql.replaceFirst("DELETE", "SELECT *");
        String installTableName = delete.getTable().getName().replace("`", "") + "_delete";
        installSql = "INSERT INTO " + installTableName + " " + installSql;
        return installSql;
    }

}

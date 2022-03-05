package tech.xs.admin.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import tech.xs.framework.util.SpringUtil;


import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

/**
 * 数据初始化
 *
 * @author 沈家文
 * @date 2021-58-25 11:58
 */
@Component
@Slf4j
@Order(10000)
public class InitDataBase implements ApplicationRunner {

    @Resource
    private DataSource dataSource;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始初始化数据结构");
        initDatabase();
        log.info("数据库结构初始化完毕");
    }

    private void initDatabase() throws SQLException, IOException {
        log.info("开始初始化数据库");
        Reader reader = SpringUtil.getResourceFileReader("sql/mysql/ddl.sql");
        if (reader == null) {
            log.info("未查询到DDL文件");
            return;
        }
        ScriptRunner runner = new ScriptRunner(dataSource.getConnection());
        runner.setAutoCommit(true);
        runner.setStopOnError(true);
        runner.runScript(reader);
        log.info("数据库初始化完毕");
    }


}

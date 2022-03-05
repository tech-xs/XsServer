package tech.xs.generator.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import tech.xs.generator.service.DatabaseService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;


/**
 * 事件监听器
 *
 * @author imsjw
 * @Date Created in 15:36 2018/7/13
 */
@Slf4j
@Configuration
public class ApplicationInit {

    @Value("${generator.enabled:false}")
    private boolean enableGenerator;

    @Resource(name = "${generator.db-type:default}")
    private DatabaseService databaseService;

    @PostConstruct
    public void initTableInfo() {
        if (!enableGenerator) {
            return;
        }
        log.info("com.leanin.intelligence.generator.config ApplicationInit initTableInfo start");
        databaseService.resetTableInfo();
        log.info("com.leanin.intelligence.generator.config ApplicationInit initTableInfo end");
    }

}

package tech.xs.doc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import tech.xs.doc.service.DocApiService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;

/**
 * 初始化接口文档
 *
 * @author 沈家文
 * @date 2021-59-12 11:59
 */
@Component
@Slf4j
@Order(10500)
public class InitDoc implements ApplicationRunner {

    @Resource
    private DocApiService docInterfaceService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始初始化文档");
        docInterfaceService.generate();
        log.info("文档初始化完毕");
    }

}

package tech.xs.examples;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tech.xs.framework.constant.ConfigConstant;

/**
 * 应用程序启动类
 *
 * @author imsjw
 * Create Time: 2020/10/15
 */
@MapperScan({ConfigConstant.MYBATIS_SCAN_DAO})
@SpringBootApplication(scanBasePackages = ConfigConstant.ROOT_PACKAGE, proxyBeanMethods = false)
public class ExamplesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamplesApplication.class, args);
    }

}

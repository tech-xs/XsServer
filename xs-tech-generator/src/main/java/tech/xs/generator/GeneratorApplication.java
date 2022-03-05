package tech.xs.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tech.xs.framework.constant.ConfigConstant;

/**
 * 应用程序启动类
 *
 * @author imsjw
 * Create Time: 2020/10/15
 */
@SpringBootApplication(scanBasePackages = ConfigConstant.ROOT_PACKAGE, proxyBeanMethods = false)
public class GeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class, args);
    }

}

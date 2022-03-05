package tech.xs.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tech.xs.framework.constant.ConfigConstant;

/**
 * 授权应用主类
 *
 * @author 沈家文
 * @date 2021/3/4 9:44
 */
@SpringBootApplication(scanBasePackages = ConfigConstant.ROOT_PACKAGE, proxyBeanMethods = false)
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}

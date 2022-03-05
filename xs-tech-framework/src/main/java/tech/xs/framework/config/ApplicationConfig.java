package tech.xs.framework.config;

import tech.xs.framework.constant.ConfigConstant;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * EnableAspectJAutoProxy 表示通过aop框架暴露该代理对象,AopContext能够访问
 * MapperScan 指定要扫描的Mapper类的包的路径
 *
 * @author 沈家文
 * @date 2020/10/12
 */
@Configuration
@EnableRetry
@EnableAsync
@EnableScheduling
@ServletComponentScan
@EnableAspectJAutoProxy(exposeProxy = true)
@ComponentScan(ConfigConstant.ROOT_PACKAGE)
@EnableAutoConfiguration
public class ApplicationConfig {

}

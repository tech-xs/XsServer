package tech.xs.framework.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import tech.xs.framework.constant.ConfigConstant;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.xs.framework.plus.mybatis.MybatisPlusDeletePlug;

import javax.annotation.Resource;

/**
 * Mybatis配置
 *
 * @author 沈家文
 * @date 2020/10/21
 */
@Configuration
public class MyBatisConfig {

    @Resource
    private MybatisPlusDeletePlug mybatisPlusDeletePlug;

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(mybatisPlusDeletePlug);
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

}

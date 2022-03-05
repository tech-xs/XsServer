package tech.xs.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.xs.auth.security.filter.CorsFilter;
import tech.xs.auth.security.filter.cors.AllCorsFilter;
import tech.xs.auth.security.filter.cors.OriginCorsFilter;

/**
 * 跨域配置
 *
 * @author 沈家文
 * @date 2020/12/17 13:11
 */
@Configuration
public class CorsConfig {

    @Value("${auth.cors.type:all}")
    private String type;

    @Value("${auth.cors.origin:}")
    private String webList;

    @Bean
    public CorsFilter corsFilter() {
        switch (type) {
            case "all": {
                return new AllCorsFilter();
            }
            case "origin": {
                return new OriginCorsFilter(webList);
            }
            default:
                return new AllCorsFilter();
        }
    }

}

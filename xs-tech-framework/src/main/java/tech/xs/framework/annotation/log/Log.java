package tech.xs.framework.annotation.log;

import java.lang.annotation.*;

/**
 * 自定义日志记录注解
 *
 * @author 沈家文
 * @date 2020/8/14
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 模块
     */
    public String title() default "";

}

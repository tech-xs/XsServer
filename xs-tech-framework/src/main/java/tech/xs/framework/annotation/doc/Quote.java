package tech.xs.framework.annotation.doc;

import java.lang.annotation.*;

/**
 * 常量
 *
 * @author 沈家文
 * @date 2021-24-17 11:24
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Quote {

    String title();

}

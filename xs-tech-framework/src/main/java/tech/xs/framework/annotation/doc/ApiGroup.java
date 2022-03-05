package tech.xs.framework.annotation.doc;

import java.lang.annotation.*;

/**
 * Api分组
 *
 * @author 沈家文
 * @date 2021-15-23 18:15
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiGroup {

    /**
     * 组名
     *
     * @return 组名
     */
    String name() default "";

}

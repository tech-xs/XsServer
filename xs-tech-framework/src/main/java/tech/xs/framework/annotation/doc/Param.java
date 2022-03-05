package tech.xs.framework.annotation.doc;

import java.lang.annotation.*;

/**
 * @author 沈家文
 * @date 2021-16-13 16:16
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Param {

    /**
     * 标题
     *
     * @return 返回标题
     */
    String title();

    /**
     * 参数名
     *
     * @return 参数名
     */
    String name() default "";

    /**
     * 描述
     *
     * @return 返回描述信息
     */
    String[] describe() default {};

    /**
     * 引用 参考
     *
     * @return 返回引用
     */
    Class<?>[] quote() default {};

    /**
     * 参数约束
     *
     * @return 返回约束列表
     */
    String[] constraint() default {};

    /**
     * 参数示例
     *
     * @return 参数示例
     */
    ParamExample[] example() default {};

}

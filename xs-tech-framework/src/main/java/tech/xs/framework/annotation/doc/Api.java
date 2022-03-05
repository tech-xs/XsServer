package tech.xs.framework.annotation.doc;

import java.lang.annotation.*;

/**
 * 文档注解
 *
 * @author 沈家文
 * @date 2021-31-13 9:31
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Api {

    /**
     * 接口标题
     *
     * @return 接口标题
     */
    String name();

    /**
     * 接口描述
     *
     * @return 接口描述
     */
    String describe() default "";

    /**
     * 示例列表
     *
     * @return 示例列表
     */
    ApiBody[] body() default {};

    /**
     * 响应参数
     *
     * @return 响应参数
     */
    Param[] respParam() default {};

    /**
     * 响应
     *
     * @return 返回响应接口文档
     */
    Response[] resp() default {};
}

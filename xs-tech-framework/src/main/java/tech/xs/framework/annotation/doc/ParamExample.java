package tech.xs.framework.annotation.doc;


import tech.xs.framework.enums.TextFormatEnum;

/**
 * 参数示例
 *
 * @author 沈家文
 * @date 2021-52-20 17:52
 */
public @interface ParamExample {

    TextFormatEnum format() default TextFormatEnum.TEXT;

    String value();

}

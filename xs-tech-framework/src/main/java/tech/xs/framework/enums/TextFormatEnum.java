package tech.xs.framework.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.ToString;

/**
 * 示例格式
 *
 * @author 沈家文
 * @date 2021-54-20 17:54
 */
@Getter
@ToString
public enum TextFormatEnum {

    /**
     * 文本
     */
    TEXT(1),

    /**
     * JSON
     */
    JSON(2),

    /**
     * XML
     */
    XML(3);

    @EnumValue
    private final int code;

    TextFormatEnum(int code) {
        this.code = code;
    }

}

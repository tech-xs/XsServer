package tech.xs.doc.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 参数类型常量
 *
 * @author 沈家文
 * @date 2021/5/25 17:18
 */
@Getter
public enum ParameterType {

    /**
     * 参数类型 1 Integer 2 Long 3 Double 4 String 5 Date 6 Array 7 Object 8 File-Image
     */
    INTEGER(1),
    LONG(2),
    DOUBLE(3),
    STRING(4),
    DATE(5),
    ARRAY(6),
    OBJECT(7),
    FILE_IMAGE(8);

    @EnumValue
    @JsonValue
    private final int code;

    ParameterType(int code) {
        this.code = code;
    }

    @JsonCreator
    public static ParameterType getItem(int code) {
        for (ParameterType item : values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }

}

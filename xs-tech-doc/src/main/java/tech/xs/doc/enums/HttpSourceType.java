package tech.xs.doc.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * body类型常量
 *
 * @author 沈家文
 * @date 2021/5/26 15:49
 */
@Getter
public enum HttpSourceType {

    /**
     * 1 请求 2 响应
     */
    REQUEST(1),

    RESPONSE(2);

    @EnumValue
    @JsonValue
    private final int code;

    HttpSourceType(int code) {
        this.code = code;
    }

    @JsonCreator
    public static HttpSourceType getItem(int code) {
        for (HttpSourceType item : values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }

}

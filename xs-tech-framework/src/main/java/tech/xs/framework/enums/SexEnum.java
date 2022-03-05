package tech.xs.framework.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.ToString;
import tech.xs.framework.base.BaseEnum;
import tech.xs.framework.constant.SexConstant;

/**
 * 性别枚举
 *
 * @author 沈家文
 * @date 2021-26-25 16:26
 */
@Getter
@ToString
public enum SexEnum implements BaseEnum {

    /**
     * 男
     */
    MAN(SexConstant.MAN),
    /**
     * 女
     */
    WOMAN(SexConstant.WOMAN),
    /**
     * 未知
     */
    UNKNOWN(SexConstant.UNKNOWN);

    @EnumValue
    private final int code;

    SexEnum(int code) {
        this.code = code;
    }

    @Override
    @JsonValue
    public Integer getCode() {
        return code;
    }

    @JsonCreator
    public static SexEnum getByCode(Integer code) {
        for (SexEnum item : SexEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

}

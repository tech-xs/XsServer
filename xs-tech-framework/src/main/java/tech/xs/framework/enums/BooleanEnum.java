package tech.xs.framework.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.ToString;
import tech.xs.framework.base.BaseEnum;
import tech.xs.framework.constant.BooleanConstant;

/**
 * 布尔类型枚举
 *
 * @author 沈家文
 * @date 2021-01-24 17:01
 */
public enum BooleanEnum implements BaseEnum {

    /**
     * True
     * 启用
     * 存在
     */
    TRUE(BooleanConstant.TRUE),

    /**
     * False
     * 禁用
     * 不存在
     */
    FALSE(BooleanConstant.FALSE);


    @EnumValue
    private final int code;

    BooleanEnum(int code) {
        this.code = code;
    }

    @Override
    @JsonValue
    public Integer getCode() {
        return code;
    }

    @JsonCreator
    public static BooleanEnum getByCode(Integer code) {
        for (BooleanEnum item : BooleanEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

    public static BooleanEnum getByCode(String code) {
        code = code.toLowerCase();
        switch (code) {
            case "enable":
            case "true": {
                return TRUE;
            }
            case "disable":
            case "false": {
                return FALSE;
            }
            default: {
                return null;
            }
        }
    }

}

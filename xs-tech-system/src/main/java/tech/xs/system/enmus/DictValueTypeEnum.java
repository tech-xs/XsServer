package tech.xs.system.enmus;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import tech.xs.framework.base.BaseEnum;
import tech.xs.system.constant.DictConstant;

/**
 * 字典值类型枚举
 */
public enum DictValueTypeEnum implements BaseEnum {
    /**
     * 字符串
     */
    STRING(DictConstant.ValueType.STRING),
    /**
     * 数值
     */
    INT(DictConstant.ValueType.INT);

    @EnumValue
    private final int code;

    private static final String intStr = "int";
    private static final String stringStr = "string";

    DictValueTypeEnum(int code) {
        this.code = code;
    }

    @Override
    @JsonValue
    public Integer getCode() {
        return code;
    }

    @JsonCreator
    public static DictValueTypeEnum getByCode(Integer code) {
        for (DictValueTypeEnum item : DictValueTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

    public static DictValueTypeEnum getByCode(String code) {
        code = code.toLowerCase();
        switch (code) {
            case intStr: {
                return INT;
            }
            case stringStr: {
                return STRING;
            }
            default: {
                return null;
            }
        }
    }

}

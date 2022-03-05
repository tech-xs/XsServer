package tech.xs.system.enmus;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import tech.xs.framework.base.BaseEnum;
import tech.xs.system.constant.DictConstant;

/**
 * 字典数据类型枚举
 */
public enum DictStructureTypeEnum implements BaseEnum {

    /**
     * 列表
     */
    LIST(DictConstant.StructureType.DATA_TYPE_LIST),

    /**
     * 树
     */
    TREE(DictConstant.StructureType.DATA_TYPE_TREE);

    private static final String treeTypeStr = "tree";
    private static final String listTypeStr = "list";

    @EnumValue
    private final int code;

    DictStructureTypeEnum(int code) {
        this.code = code;
    }

    @Override
    @JsonValue
    public Integer getCode() {
        return code;
    }

    @JsonCreator
    public static DictStructureTypeEnum getByCode(Integer code) {
        for (DictStructureTypeEnum item : DictStructureTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

    public static DictStructureTypeEnum getByCode(String code) {
        code = code.toLowerCase();
        switch (code) {
            case treeTypeStr: {
                return TREE;
            }
            case listTypeStr: {
                return LIST;
            }
            default: {
                return null;
            }
        }
    }

}

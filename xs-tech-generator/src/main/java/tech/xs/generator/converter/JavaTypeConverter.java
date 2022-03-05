package tech.xs.generator.converter;


import tech.xs.generator.constant.FieldTypeConstant;
import tech.xs.generator.constant.JavaTypeConstant;

/**
 * 类型转换器
 *
 * @author imsjw
 * Create Time: 2021/1/19
 */
public class JavaTypeConverter {

    public static String converter(String type) {
        switch (type) {
            case FieldTypeConstant.BIT:
            case FieldTypeConstant.INT:
                return JavaTypeConstant.INTEGER;
            case FieldTypeConstant.BIGINT:
                return JavaTypeConstant.LONG;
            case FieldTypeConstant.DOUBLE:
                return JavaTypeConstant.DOUBLE;
            case FieldTypeConstant.DATETIME:
                return JavaTypeConstant.DATE;
            case FieldTypeConstant.CHAR:
            case FieldTypeConstant.VARCHAR:
            case FieldTypeConstant.TEXT:
                return JavaTypeConstant.STRING;
            case FieldTypeConstant.BYTE_ARRAY:
                return JavaTypeConstant.BYTE_ARRAY;
            default:
                return null;
        }
    }

}

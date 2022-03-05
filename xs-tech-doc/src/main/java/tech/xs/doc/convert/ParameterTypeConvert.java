package tech.xs.doc.convert;


import tech.xs.doc.enums.ParameterType;

import java.util.Date;
import java.util.List;

/**
 * 参数类型转换
 *
 * @author 沈家文
 * @date 2021-23-16 10:23
 */
public class ParameterTypeConvert {

    public static ParameterType getType(Class<?> c) {
        if (Integer.class.equals(c)) {
            return ParameterType.INTEGER;
        } else if (Long.class.equals(c)) {
            return ParameterType.LONG;
        } else if (Double.class.equals(c)) {
            return ParameterType.DOUBLE;
        } else if (String.class.equals(c)) {
            return ParameterType.STRING;
        } else if (Date.class.equals(c)) {
            return ParameterType.DATE;
        } else if (c.isArray() || List.class.equals(c)) {
            return ParameterType.ARRAY;
        }
        return ParameterType.OBJECT;
    }

}

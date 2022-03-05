package tech.xs.framework.config.converter;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.core.convert.converter.Converter;
import tech.xs.framework.base.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Integer 转枚举类型转换器
 *
 * @author 沈家文
 * @date 2021-51-27 11:51
 */
public class IntegerToEnumConverter<T extends BaseEnum> implements Converter<Integer, T> {

    private Map<Integer, T> enumMap = new HashMap<>();

    public IntegerToEnumConverter(Class<T> enumType) {
        T[] enums = enumType.getEnumConstants();
        for (T e : enums) {
            enumMap.put(e.getCode(), e);
        }
    }

    @Override
    public T convert(Integer source) {
        T t = enumMap.get(source);
        if (ObjectUtil.isNull(t)) {
            throw new IllegalArgumentException("无法匹配对应的枚举类型");
        }
        return t;
    }


}

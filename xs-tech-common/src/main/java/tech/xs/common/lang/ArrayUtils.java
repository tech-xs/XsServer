package tech.xs.common.lang;

import java.lang.reflect.Array;

/**
 * 数组工具
 *
 * @author 沈家文
 * @date 2021/7/22 14:29
 */
public class ArrayUtils {

    public static int getLength(final Object array) {
        if (array == null) {
            return 0;
        }
        return Array.getLength(array);
    }

    public static boolean isEmpty(final Object[] array) {
        return getLength(array) == 0;
    }

    public static <T> boolean isNotEmpty(final T[] array) {
        return !isEmpty(array);
    }

}

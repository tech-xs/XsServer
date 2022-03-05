package tech.xs.common.collections;

import java.util.Collection;

/**
 * 列表工具
 *
 * @author 沈家文
 * @date 2021/7/22 14:20
 */
public class CollectionUtils {

    public static boolean isEmpty(final Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean isNotEmpty(final Collection<?> coll) {
        return !isEmpty(coll);
    }

}

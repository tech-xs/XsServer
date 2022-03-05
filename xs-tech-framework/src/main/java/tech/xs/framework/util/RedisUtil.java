package tech.xs.framework.util;

import org.apache.commons.lang3.ArrayUtils;
import tech.xs.common.constant.Symbol;

/**
 * Redis工具
 *
 * @author 沈家文
 * @date 2020/10/23
 */
public class RedisUtil {

    /**
     * Redis Key拼接
     *
     * @param keys
     * @return
     */
    public static String splicingKey(Object... keys) {
        if (ArrayUtils.isEmpty(keys)) {
            return null;
        }
        if (keys.length == 1) {
            return keys[0].toString();
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < keys.length - 1; i++) {
            buffer.append(keys[i]);
            buffer.append(Symbol.COLON);
            buffer.append(Symbol.COLON);
        }
        buffer.append(keys[keys.length - 1]);
        return buffer.toString();
    }

}

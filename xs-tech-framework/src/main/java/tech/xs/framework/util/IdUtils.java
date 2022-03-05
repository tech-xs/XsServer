package tech.xs.framework.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * ID工具
 *
 * @author 沈家文
 * @date 2021/5/21 17:40
 */
public class IdUtils {

    private static Snowflake snowflake;

    static {
        snowflake = IdUtil.createSnowflake(1, 1);
    }

    public static Long getId() {
        return snowflake.nextId();
    }

}

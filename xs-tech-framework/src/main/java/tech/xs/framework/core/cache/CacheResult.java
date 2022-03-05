package tech.xs.framework.core.cache;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 缓存结果
 *
 * @author 沈家文
 * @date 2021/7/13 10:10
 */
@Getter
@Setter
@ToString
public class CacheResult<T> {

    /**
     * 缓存键
     */
    public String key;

    /**
     * 缓存值
     */
    private T value;

}

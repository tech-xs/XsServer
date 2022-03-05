package tech.xs.framework.core.cache;

/**
 * 缓存抽象类
 *
 * @author 沈家文
 * @date 2021/7/8 17:08
 */
public abstract class AbstractCache implements Cache {

    @Override
    public String getString(String key) {
        return get(key, String.class);
    }

    @Override
    public Long getLong(String key) {
        return get(key, Long.class);
    }

    @Override
    public Integer getInteger(String key) {
        return get(key, Integer.class);
    }
}

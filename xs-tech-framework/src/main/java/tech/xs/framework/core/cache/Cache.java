package tech.xs.framework.core.cache;

/**
 * 缓存接口
 *
 * @author 沈家文
 * @date 2021/5/26 11:39
 */
public interface Cache {

    /**
     * 设置缓存值
     *
     * @param key   缓存key
     * @param value 缓存值
     */
    void set(String key, String value);

    /**
     * 设置缓存值
     *
     * @param key   缓存key
     * @param value 缓存值
     */
    void set(String key, Object value);

    /**
     * 设置缓存值
     *
     * @param key   缓存key
     * @param value 缓存值
     * @param time  时长(毫秒)
     */
    void set(String key, String value, long time);

    /**
     * 设置缓存值
     *
     * @param key   缓存key
     * @param value 缓存值
     * @param time  时长(毫秒)
     */
    void set(String key, Object value, long time);

    /**
     * 根据key删除缓存
     *
     * @param key 缓存key
     */
    void delete(String key);

    /**
     * 根据key前缀删除缓存
     *
     * @param prefix 缓存key前缀
     */
    void deleteByPrefix(String prefix);

    /**
     * 获取缓存
     *
     * @param key      缓存key
     * @param objClass 缓存类型
     * @param <T>      缓存类型泛型
     * @return 返回反序列化好的缓存值
     */
    <T> T get(String key, Class<T> objClass);

    /**
     * 获取String
     *
     * @param key
     * @return
     */
    String getString(String key);

    /**
     * 获取Long
     *
     * @param key
     * @return
     */
    Long getLong(String key);

    /**
     * 获取Intenger
     *
     * @param key
     * @return
     */
    Integer getInteger(String key);
}

package tech.xs.framework.core.storage;

/**
 * 存储配置
 */
public interface StorageConfig {

    /**
     * 获取存储类型
     *
     * @return
     */
    String getStorageType();

    /**
     * 获取Minio配置
     *
     * @return
     */
    MinioConfig getMinioConfig();

}

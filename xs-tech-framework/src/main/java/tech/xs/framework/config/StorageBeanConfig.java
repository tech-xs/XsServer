package tech.xs.framework.config;

import tech.xs.framework.constant.StorageConstant;
import tech.xs.framework.core.storage.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 可持久化存储配置
 *
 * @author 沈家文
 * @date 2021/6/21 16:12
 */
@Configuration
public class StorageBeanConfig {

    @Resource
    private StorageConfig storageConfig;

    @Resource
    private MinioStorage minioStorage;

    @Resource
    private LocalStorage localStorage;

    @Resource
    private OssStorage ossStorage;

    @Bean
    public Storage storage() {
        String storageType = null;
        try {
            storageType = storageConfig.getStorageType();
        } catch (Exception e) {
            storageType = StorageConstant.MINIO_TYPE;
        }
        switch (storageType) {
            case StorageConstant.MINIO_TYPE: {
                return minioStorage;
            }
            case StorageConstant.OSS_TYPE: {
                return ossStorage;
            }
            default: {
                return localStorage;
            }
        }
    }

}

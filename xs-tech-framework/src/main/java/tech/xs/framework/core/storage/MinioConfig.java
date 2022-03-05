package tech.xs.framework.core.storage;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * minio配置
 */
@Getter
@Setter
@ToString
public class MinioConfig {

    private String accessKey;

    private String secretKey;

    private String endpoint;

    private String bucketName;

}

package tech.xs.framework.core.storage;

import tech.xs.common.lang.StringUtils;
import tech.xs.framework.constant.StorageConstant;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件Service
 *
 * @author 沈家文
 * @date 2021/3/4 14:54
 */
@Slf4j
@Component("minio")
public class MinioStorage extends AbstractStorage implements Storage {

    private String storageType;

    @Resource
    private StorageConfig storageConfig;

    private String accessKey;

    private String secretKey;

    private String endpoint;

    private String bucketName;

    public MinioStorage() {
    }

    public MinioStorage(String endpoint, String accessKey, String secretKey, String bucketName) {
        this.storageType = StorageConstant.MINIO_TYPE;
        this.endpoint = endpoint;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucketName = bucketName;
    }

    @PostConstruct
    private void init() throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        MinioConfig minioConfig = null;
        try {
            storageType = storageConfig.getStorageType();
            minioConfig = storageConfig.getMinioConfig();
        } catch (Exception e) {
            storageType = StorageConstant.MINIO_TYPE;
        }
        if (minioConfig == null) {
            return;
        }
        accessKey = minioConfig.getAccessKey();
        secretKey = minioConfig.getSecretKey();
        endpoint = minioConfig.getEndpoint();
        bucketName = minioConfig.getBucketName();

        if (!StorageConstant.MINIO_TYPE.equals(storageType)) {
            return;
        }
        MinioClient client = getClient();
        if (client == null) {
            return;
        }
        if (StringUtils.isBlank(bucketName)) {
            return;
        }
        boolean isExit = client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (isExit) {
            return;
        }
        log.info("create minio bucket bucketName:" + bucketName);
        client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
    }

    @Override
    public void upload(String path, InputStream inputStream) throws Exception {
        MinioClient client = getClient();

        client.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(path)
                .stream(inputStream, inputStream.available(), -1)
                .build());
    }

    @Override
    public byte[] getBytes(String path) throws Exception {
        MinioClient client = getClient();
        GetObjectArgs build = GetObjectArgs.builder()
                .bucket(bucketName)
                .object(path)
                .build();
        byte[] bytes = null;
        try (GetObjectResponse response = client.getObject(build);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] tempBytes = new byte[1024];
            int len = -1;
            while ((len = response.read(tempBytes)) != -1) {
                out.write(tempBytes, 0, len);
            }
            bytes = out.toByteArray();
        } catch (Exception e) {
            throw e;
        }
        return bytes;
    }


    @Override
    public void delete(String path) {
        MinioClient client = getClient();
        try {
            client.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(path).build());
        } catch (Exception e) {
            log.error("文件删除失败 path:" + path);
            e.printStackTrace();
        }
    }

    @Override
    public List<String> list(String prefix, boolean recursive, int maxNum) {
        MinioClient client = getClient();
        ListObjectsArgs.Builder args = ListObjectsArgs.builder().recursive(recursive).bucket(bucketName);
        if (maxNum > 0) {
            args.maxKeys(maxNum);
        }
        if (StringUtils.isNotBlank(prefix)) {
            args.prefix(prefix);
        }
        List<String> res = new ArrayList<>();
        Iterable<Result<Item>> fileList = client.listObjects(args.build());
        fileList.forEach(item -> {
            try {
                Item obj = item.get();
                if (obj.isDir()) {
                    return;
                }
                res.add(obj.objectName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return res;
    }

    public MinioClient getClient() {
        try {
            if (StringUtils.isAnyBlank(endpoint, accessKey, secretKey)) {
                log.error("minio 配置错误");
                return null;
            }
            return MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(accessKey, secretKey)
                    .build();
        } catch (Exception e) {
            log.error("minio 配置异常");
            e.printStackTrace();
        }
        return null;
    }

}

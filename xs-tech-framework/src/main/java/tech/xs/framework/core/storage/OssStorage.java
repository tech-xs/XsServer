package tech.xs.framework.core.storage;

import cn.hutool.core.io.IoUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import tech.xs.common.constant.Symbol;
import tech.xs.common.lang.StringUtils;
import tech.xs.framework.constant.StorageConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 阿里云OSS存储
 *
 * @author 沈家文
 * @date 2021/6/21 15:48
 */
@Slf4j
@Component("oss")
public class OssStorage extends AbstractStorage implements Storage {

    public static final int MAX_NUM = 200;

    @Value("${storage.type:}")
    private String storageType;

    @Value("${storage.oss.accessKey:}")
    private String accessKey;

    @Value("${storage.oss.secretKey:}")
    private String secretKey;

    @Value("${storage.oss.endpoint:}")
    private String endpoint;

    @Value("${storage.oss.bucketName:}")
    private String bucketName;

    public OssStorage() {
    }

    public OssStorage(String endpoint, String accessKey, String secretKey, String bucketName) {
        this.storageType = StorageConstant.OSS_TYPE;
        this.endpoint = endpoint;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucketName = bucketName;
    }

    @PostConstruct
    private void init() {
        if (!StorageConstant.OSS_TYPE.equals(storageType)) {
            return;
        }
        if (StringUtils.isBlank(bucketName)) {
            return;
        }
        OSS client = getClient();
        boolean isExit = client.doesBucketExist(bucketName);
        if (!isExit) {
            log.error("oss bucket not exit, bucketName: " + bucketName);
        }
        client.shutdown();
    }

    @Override
    public void upload(String path, InputStream inputStream) throws Exception {
        OSS client = getClient();
        if (StringUtils.isBlank(path)) {
            return;
        }
        path = correctPath(path);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, path, inputStream);
        client.putObject(putObjectRequest);
        client.shutdown();
    }

    @Override
    public byte[] getBytes(String path) throws Exception {
        OSS client = getClient();
        if (StringUtils.isBlank(path)) {
            return null;
        }
        path = correctPath(path);
        OSSObject object = client.getObject(bucketName, path);
        return IoUtil.readBytes(object.getObjectContent());
    }

    @Override
    public void delete(String path) {
        OSS client = getClient();
        if (StringUtils.isBlank(path)) {
            return;
        }
        path = correctPath(path);
        client.deleteObject(bucketName, path);
        client.shutdown();
    }

    @Override
    public List<String> list(String prefix, boolean recursive, int maxNum) {
        OSS client = getClient();
        ListObjectsRequest request = new ListObjectsRequest(bucketName);
        if (maxNum > 0) {
            request.withMaxKeys(maxNum);
        }
        if (StringUtils.isNotBlank(prefix)) {
            if (prefix.startsWith(Symbol.SLASH)) {
                prefix = prefix.substring(1);
            }
            request.withPrefix(prefix);
        }
        if (recursive) {
            request.withMaxKeys(MAX_NUM);
        }
        List<String> res = new ArrayList<>();

        ObjectListing objectList;
        do {
            objectList = client.listObjects(request);
            List<OSSObjectSummary> sums = objectList.getObjectSummaries();
            for (OSSObjectSummary s : sums) {
                res.add(s.getKey());
                if (maxNum > 0 && res.size() >= maxNum) {
                    return res;
                }
            }
        } while (recursive && objectList.isTruncated());

        return res;
    }

    public OSS getClient() {
        return new OSSClientBuilder().build(endpoint, accessKey, secretKey);
    }

    private String correctPath(String path) {
        if (path.startsWith(Symbol.SLASH)) {
            return path.substring(1);
        }
        return path;
    }
}

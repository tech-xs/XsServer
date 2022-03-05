package tech.xs.framework.core.storage;


import java.io.InputStream;
import java.util.List;

/**
 * 存储接口
 *
 * @author 沈家文
 * @date 2021/7/8 17:10
 */
public interface Storage {

    /**
     * 上传文件
     *
     * @param path
     * @param inputStream
     * @throws Exception 文件上传异常
     */
    void upload(String path, InputStream inputStream) throws Exception;

    /**
     * 上传文件
     *
     * @param path
     * @param data
     * @throws Exception 文件上传异常
     */
    void upload(String path, byte[] data) throws Exception;

    /**
     * 根据路径获取文件
     *
     * @param path
     * @return
     * @throws Exception
     */
    byte[] getBytes(String path) throws Exception;

    /**
     * 删除文件
     *
     * @param path
     */
    void delete(String path);

    /**
     * 查询文件列表
     *
     * @param prefix    前缀 可以为 null
     * @param recursive 是否递归 默认不递归
     * @param maxNum    最大数量 0为 没有限制
     * @return
     */
    List<String> list(String prefix, boolean recursive, int maxNum);

}

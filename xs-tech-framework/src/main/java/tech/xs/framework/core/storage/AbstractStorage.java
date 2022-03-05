package tech.xs.framework.core.storage;

import java.io.ByteArrayInputStream;

/**
 * 存储抽象类
 *
 * @author 沈家文
 * @date 2021/7/8 17:08
 */
public abstract class AbstractStorage implements Storage {

    @Override
    public void upload(String path, byte[] data) throws Exception {
        upload(path, new ByteArrayInputStream(data));
    }

}

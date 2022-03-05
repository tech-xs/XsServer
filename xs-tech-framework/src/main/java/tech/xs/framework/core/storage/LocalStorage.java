package tech.xs.framework.core.storage;

import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

/**
 * 文件Service
 *
 * @author 沈家文
 * @date 2021/3/4 14:54
 */
@Component
public class LocalStorage extends AbstractStorage implements Storage {

    @Override
    public void upload(String path, InputStream inputStream) {

    }

    @Override
    public byte[] getBytes(String path) throws Exception {
        return new byte[0];
    }


    @Override
    public void delete(String path) {

    }

    @Override
    public List<String> list(String prefix, boolean recursive, int maxNum) {
        return null;
    }

}

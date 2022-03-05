package tech.xs.auth.service;

import tech.xs.framework.base.BaseService;
import tech.xs.framework.enums.HttpMethodEnum;

import java.util.Set;

public interface AuthUriService extends BaseService {

    /**
     * 添加网络请求白名单
     *
     * @param method
     * @param uri
     */
    void addUriWhite(HttpMethodEnum method, String uri);

    /**
     * 获取网络请求白名单
     *
     * @return
     */
    Set<String> getUriWhite(HttpMethodEnum method);

}

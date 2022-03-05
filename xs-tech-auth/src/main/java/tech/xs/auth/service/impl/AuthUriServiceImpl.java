package tech.xs.auth.service.impl;

import org.springframework.stereotype.Service;
import tech.xs.auth.service.AuthUriService;
import tech.xs.framework.enums.HttpMethodEnum;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class AuthUriServiceImpl implements AuthUriService {

    /**
     * 白名单
     */
    private static final Map<HttpMethodEnum, Set<String>> WHITE_LIST_MAP = new HashMap<>();

    static {
        Set<String> all = new HashSet<>();
        all.add("/sys/health");
        all.add("/sys/config/public/init/web");
        all.add("/auth/check/accessToken");
        all.add("/sys/user/register/get/email/code");
        all.add("/sys/user/register/email");
        all.add("/sys/user/password/reset/get/email/code");
        all.add("/sys/user/password/reset/email");
        all.add("/auth/login");
        all.add("/auth/logout");

        // swagger
        all.add("/swagger");
        all.add("/webjars");
        all.add("/v3");

        WHITE_LIST_MAP.put(HttpMethodEnum.ALL, all);
        WHITE_LIST_MAP.put(HttpMethodEnum.GET, new HashSet<>());
        WHITE_LIST_MAP.put(HttpMethodEnum.HEAD, new HashSet<>());
        WHITE_LIST_MAP.put(HttpMethodEnum.POST, new HashSet<>());
        WHITE_LIST_MAP.put(HttpMethodEnum.PUT, new HashSet<>());
        WHITE_LIST_MAP.put(HttpMethodEnum.DELETE, new HashSet<>());
        WHITE_LIST_MAP.put(HttpMethodEnum.OPTIONS, new HashSet<>());
        WHITE_LIST_MAP.put(HttpMethodEnum.TRACE, new HashSet<>());
    }

    @Override
    public void addUriWhite(HttpMethodEnum method, String uri) {
        WHITE_LIST_MAP.get(method).add(uri);
    }

    @Override
    public Set<String> getUriWhite(HttpMethodEnum method) {
        return WHITE_LIST_MAP.get(method);
    }


}

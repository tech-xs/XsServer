package tech.xs.framework.util;

import tech.xs.common.constant.http.HttpMethod;
import tech.xs.common.lang.StringUtils;
import tech.xs.framework.constant.HttpMethodConstant;

/**
 * Http请求方式工具
 *
 * @author 沈家文
 * @date 2021-53-12 18:53
 */
public class HttpMethodUtil {

    public static Integer convert(String method) {
        if (StringUtils.isBlank(method)) {
            return null;
        }
        method = method.toUpperCase();
        switch (method) {
            case HttpMethod.GET: {
                return HttpMethodConstant.GET;
            }
            case HttpMethod.POST: {
                return HttpMethodConstant.POST;
            }
            case HttpMethod.HEAD: {
                return HttpMethodConstant.HEAD;
            }
            case HttpMethod.PUT: {
                return HttpMethodConstant.PUT;
            }
            case HttpMethod.DELETE: {
                return HttpMethodConstant.DELETE;
            }
            case HttpMethod.CONNECT: {
                return HttpMethodConstant.CONNECT;
            }
            case HttpMethod.OPTIONS: {
                return HttpMethodConstant.OPTIONS;
            }
            case HttpMethod.TRACE: {
                return HttpMethodConstant.TRACE;
            }
            default: {
                return null;
            }
        }
    }
}

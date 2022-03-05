package tech.xs.auth.security.filter;

import tech.xs.framework.base.BaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 跨域Service
 *
 * @author 沈家文
 * @date 2020/12/17 10:00
 */
public interface CorsFilter {

    /**
     * 跨域过滤
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @author imsjw
     * Create Time: 2020/12/17 10:05
     */
    boolean filter(HttpServletRequest request, HttpServletResponse response) throws IOException;

}

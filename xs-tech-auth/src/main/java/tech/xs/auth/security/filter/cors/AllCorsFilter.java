package tech.xs.auth.security.filter.cors;

import org.springframework.http.HttpMethod;
import tech.xs.auth.security.filter.CorsFilter;
import tech.xs.common.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 不进行跨域过滤
 *
 * @author 沈家文
 * @date 2020/12/17 10:04
 */
public class AllCorsFilter implements CorsFilter {

    @Override
    public boolean filter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String origin = request.getHeader("Origin");
        if (StringUtils.isNotBlank(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
            response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,PUT,PATCH,TRACE,CONNECT");
        }
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("ok");
            return false;
        }
        return true;
    }

}

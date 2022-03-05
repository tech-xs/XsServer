package tech.xs.auth.security.filter.cors;

import org.springframework.http.HttpMethod;
import tech.xs.auth.security.filter.CorsFilter;
import tech.xs.common.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 源站跨域校验
 *
 * @author 沈家文
 * @date 2020/12/17 13:44
 */
public class OriginCorsFilter implements CorsFilter {

    private List<String> whiteList = new ArrayList<>();

    public OriginCorsFilter(String urlStrList) {
        String[] urlList = urlStrList.split(",");
        if (urlList == null) {
            return;
        }
        for (String item : urlList) {
            if (StringUtils.isNotBlank(item.trim())) {
                whiteList.add(item);
            }
        }
    }

    @Override
    public boolean filter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String origin = request.getHeader("Origin");
        if (StringUtils.isNotBlank(origin)) {
            if (!isWhiteList(origin)) {
                return false;
            }
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

    public boolean isWhiteList(String origin) {
        for (String item : whiteList) {
            if (item.equals(origin)) {
                return true;
            }
        }
        return false;
    }
}

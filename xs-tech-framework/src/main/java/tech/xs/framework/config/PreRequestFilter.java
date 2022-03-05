package tech.xs.framework.config;

import tech.xs.common.constant.http.HttpHeader;
import tech.xs.common.constant.http.header.ContentType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 防止IO重复读取出现错误
 *
 * @author 沈家文
 * @date 2020/10/12
 */
@WebFilter
public class PreRequestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String contentType = httpRequest.getHeader(HttpHeader.CONTENT_TYPE);
        if (contentType != null && contentType.startsWith(ContentType.MULTIPART_FORM_DATA)) {
            chain.doFilter(request, response);
            return;
        }
        RequestWrapper requestWrapper = new RequestWrapper(httpRequest);
        chain.doFilter(requestWrapper, response);
    }
}

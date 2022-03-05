package tech.xs.framework.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import tech.xs.common.lang.StringUtils;
import tech.xs.framework.base.BaseEnum;
import tech.xs.framework.exception.ArgumentTypeException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 自定义参数解析器
 *
 * @author 沈家文
 * @date 2020/10/12
 */
@Component
public class ArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Annotation[] methodAnnotations = parameter.getMethodAnnotations();
        for (Annotation item : methodAnnotations) {
            if (item.annotationType().equals(GetMapping.class)) {
                return false;
            }
            if (item.annotationType().equals(DeleteMapping.class)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest req = (HttpServletRequest) webRequest.getNativeRequest();
        String contentType = req.getContentType();
        if (StringUtils.isBlank(contentType)) {
            return null;
        }
        if (contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
            return jsonResolveArgument(parameter, req);
        }
        return null;
    }


    private Object jsonResolveArgument(MethodParameter parameter, HttpServletRequest req) throws IOException {
        String json = IOUtils.toString(req.getReader());
        if (StringUtils.isBlank(json)) {
            return null;
        }

        String parameterName = parameter.getParameterName();
        Class<?> parameterType = parameter.getParameterType();

        JSONObject jsonObj = JSON.parseObject(json);
        Object res = null;
        try {
            if (parameterType.equals(List.class)) {
                JSONArray jsonArray = jsonObj.getJSONArray(parameterName);
                if (jsonArray == null) {
                    return null;
                }
                ParameterizedType genericParameterType = (ParameterizedType) parameter.getGenericParameterType();
                parameterType = (Class<?>) genericParameterType.getActualTypeArguments()[0];
                res = jsonArray.toJavaList(parameterType);
            } else if (parameterType.isEnum()) {
                return getBaseEnum((Class<BaseEnum>) parameterType, jsonObj.getInteger(parameterName));
            } else {
                res = jsonObj.getObject(parameterName, parameterType);
            }
        } catch (Exception e) {
            String msg = "参数类型错误";
            if (parameterType.isEnum()) {
                msg += " 枚举应实现 BaseEnum";
            }
            throw new ArgumentTypeException(parameterName, msg);
        }
        return res;
    }

    public static <T extends BaseEnum> Object getBaseEnum(Class<T> targerType, Integer source) {
        for (T enumObj : targerType.getEnumConstants()) {
            if (source.equals(enumObj.getCode())) {
                return enumObj;
            }
        }
        return null;
    }


}

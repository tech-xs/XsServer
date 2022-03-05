package tech.xs.framework.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import tech.xs.common.constant.http.HttpMethod;
import tech.xs.framework.base.BaseEnum;
import tech.xs.framework.constant.HttpMethodConstant;

import java.util.Locale;

/**
 * Http请求方式枚举
 * 1: get 2: head 3: post 4: put 5: delete 6: connect 7: options 8: trace
 *
 * @author 沈家文
 * @date 2021/6/16 9:36
 */
public enum HttpMethodEnum implements BaseEnum {


    /**
     * @see HttpMethodConstant
     */
    GET(HttpMethodConstant.GET, HttpMethod.GET),
    HEAD(HttpMethodConstant.HEAD, HttpMethod.HEAD),
    POST(HttpMethodConstant.POST, HttpMethod.POST),
    PUT(HttpMethodConstant.PUT, HttpMethod.PUT),
    DELETE(HttpMethodConstant.DELETE, HttpMethod.DELETE),
    CONNECT(HttpMethodConstant.CONNECT, HttpMethod.CONNECT),
    OPTIONS(HttpMethodConstant.OPTIONS, HttpMethod.OPTIONS),
    TRACE(HttpMethodConstant.TRACE, HttpMethod.TRACE),
    ALL(HttpMethodConstant.ALL, null);

    /**
     * 对应的int常量
     */
    @EnumValue
    private final int code;

    /**
     * 对应的Http请求方式
     */
    private final String value;

    HttpMethodEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    @JsonValue
    public Integer getCode() {
        return code;
    }

    @JsonCreator
    public static HttpMethodEnum getByCode(Integer code) {
        for (HttpMethodEnum item : HttpMethodEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

    public static HttpMethodEnum getByCode(String code) {
        String value = code.toUpperCase();
        switch (value) {
            case HttpMethod.GET: {
                return GET;
            }
            case HttpMethod.HEAD: {
                return HEAD;
            }
            case HttpMethod.POST: {
                return POST;
            }
            case HttpMethod.PUT: {
                return PUT;
            }
            case HttpMethod.DELETE: {
                return DELETE;
            }
            case HttpMethod.CONNECT: {
                return CONNECT;
            }
            case HttpMethod.OPTIONS: {
                return OPTIONS;
            }
            case HttpMethod.TRACE: {
                return TRACE;
            }
            default: {
                return null;
            }
        }
    }

    public String getValue() {
        return value;
    }

    public static Integer getCode(String value) {
        value = value.toUpperCase();
        switch (value) {
            case HttpMethod.GET: {
                return GET.code;
            }
            case HttpMethod.HEAD: {
                return HEAD.code;
            }
            case HttpMethod.POST: {
                return POST.code;
            }
            case HttpMethod.PUT: {
                return PUT.code;
            }
            case HttpMethod.DELETE: {
                return DELETE.code;
            }
            case HttpMethod.CONNECT: {
                return CONNECT.code;
            }
            case HttpMethod.OPTIONS: {
                return OPTIONS.code;
            }
            case HttpMethod.TRACE: {
                return TRACE.code;
            }
            default: {
                return null;
            }
        }
    }

    public String getValue(int code) {
        switch (code) {
            case HttpMethodConstant.GET: {
                return GET.value;
            }
            case HttpMethodConstant.HEAD: {
                return HEAD.value;
            }
            case HttpMethodConstant.POST: {
                return POST.value;
            }
            case HttpMethodConstant.PUT: {
                return PUT.value;
            }
            case HttpMethodConstant.DELETE: {
                return DELETE.value;
            }
            case HttpMethodConstant.CONNECT: {
                return CONNECT.value;
            }
            case HttpMethodConstant.OPTIONS: {
                return OPTIONS.value;
            }
            case HttpMethodConstant.TRACE: {
                return TRACE.value;
            }
            default: {
                return null;
            }
        }
    }
}

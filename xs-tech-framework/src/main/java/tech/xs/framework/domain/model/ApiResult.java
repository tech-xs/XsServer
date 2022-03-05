package tech.xs.framework.domain.model;

import tech.xs.common.lang.StringUtils;
import tech.xs.framework.enums.ResultEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Result基类
 *
 * @author 沈家文
 * @date 2020/7/27
 */
@Getter
@Setter
@ToString
public class ApiResult<T> implements Serializable {

    /**
     * 返回code
     * 通用状态请参考 {@link ResultEnum}
     */
    private Integer code;

    /**
     * 返回信息
     * 通用消息请参考 {@link ResultEnum}
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 初始化一个新创建的 BaseResult 对象，使其表示一个空消息。
     */
    public ApiResult() {
    }

    public ApiResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ApiResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ApiResult(ResultEnum resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public ApiResult(ResultEnum resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

    /**
     * 返回成功 Result
     *
     * @return 成功 Result
     */
    public static <T> ApiResult<T> success() {
        return new ApiResult<>(ResultEnum.success);
    }

    /**
     * @param data 返回数据
     * @return 成功 Result
     */
    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(ResultEnum.success, data);
    }

    /**
     * @return 未知错误消息
     */
    public static <T> ExceptionResult<T> unknownError() {
        return new ExceptionResult<>(ResultEnum.unknownError);
    }

    /**
     * @param msg  提示内容
     * @param data 数据
     * @return 未知错误消息
     */
    public static <T> ExceptionResult<T> unknownError(String msg, T data) {
        return new ExceptionResult<>(ResultEnum.unknownError.getCode(), msg, data);
    }

    /**
     * 构建错误ApiResult
     *
     * @param code 错误code
     * @param msg  错误信息
     * @param <T>  错误数据类型
     * @return 返回构建好的ApiResult
     */
    public static <T> ApiResult<T> error(Integer code, String msg) {
        return new ApiResult<>(code, msg, null);
    }

    /**
     * @param code code
     * @param msg  消息
     * @param data 数据
     * @param <T>  数据类型
     * @return 结果集
     */
    public static <T> ApiResult<T> error(Integer code, String msg, T data) {
        return new ApiResult<>(code, msg, data);
    }

    public static <T> ApiResult<T> error(ResultEnum resultEnum) {
        return new ApiResult<>(resultEnum.getCode(), resultEnum.getMsg(), null);
    }

    public static <T> ApiResult<T> error(ResultEnum resultEnum, T data) {
        return new ApiResult<>(resultEnum.getCode(), resultEnum.getMsg(), data);
    }

    /**
     * 构建无效参数返回结果
     *
     * @param fieldName 字段名称
     * @param errMsg    错误信息
     * @return 无效参数返回结果
     */
    public static <T> ApiResult<T> invalidParameter(String fieldName, String errMsg) {
        if (StringUtils.isNotBlank(fieldName)) {
            return new ApiResult<>(ResultEnum.invalidParameter.getCode(), "[" + fieldName + "] " + errMsg, null);
        } else {
            return new ApiResult<>(ResultEnum.invalidParameter.getCode(), errMsg, null);
        }
    }

    /**
     * 构建无效参数返回结果
     *
     * @param fieldName 字段名称
     * @return 无效参数结果
     */
    public static <T> ApiResult<T> invalidParameter(String fieldName) {
        return new ApiResult<>(ResultEnum.invalidParameter.getCode(), "[" + fieldName + "] " + ResultEnum.invalidParameter.getMsg(), null);
    }

    /**
     * 构建无效参数返回结果
     *
     * @return 无效参数结果
     */
    public static <T> ApiResult<T> invalidParameter() {
        return new ApiResult<>(ResultEnum.invalidParameter, null);
    }

}

package tech.xs.framework.enums;

import tech.xs.framework.constant.ResultConstant;

/**
 * 通用Result code 和 msg
 *
 * @author 沈家文
 * @date 2020/8/13
 */
public enum ResultEnum {

    /**
     * 成功
     */
    success(ResultConstant.SUCCESS_CODE, ResultConstant.SUCCESS_MSG),
    /**
     * 未知错误
     */
    unknownError(ResultConstant.UNKNOWN_ERROR_CODE, ResultConstant.UNKNOWN_ERROR_MSG),

    /**
     * 无效参数
     */
    invalidParameter(ResultConstant.INVALID_PARAMETER_CODE, ResultConstant.INVALID_PARAMETER_MSG),

    /**
     * 账号未登录
     */
    noLogin(ResultConstant.NO_LOGIN_CODE, ResultConstant.NO_LOGIN_MSG),

    /**
     * 登陆已经失效
     */
    loginInvalid(ResultConstant.LOGIN_INVALID_CODE, ResultConstant.LOGIN_INVALID_MSG),

    /**
     * 权限不足
     */
    permissionDenied(ResultConstant.PERMISSION_DENIED_CODE, ResultConstant.PERMISSION_DENIED_MSG),

    /**
     * http消息读取错误
     */
    httpMessageReadError(501, "http message read error");

    private final int code;
    private final String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

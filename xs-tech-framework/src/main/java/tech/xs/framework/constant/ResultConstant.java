package tech.xs.framework.constant;

/**
 * 返回结果常量
 *
 * @author 沈家文
 * @date 2021/4/28 11:35
 */
public class ResultConstant {

    /**
     * 成功
     */
    public static final int SUCCESS_CODE = 200;
    public static final String SUCCESS_MSG = "success";

    /**
     * 未知错误
     */
    public static final int UNKNOWN_ERROR_CODE = 500;
    public static final String UNKNOWN_ERROR_MSG = "unknown error";

    /**
     * 无效参数
     */
    public static final int INVALID_PARAMETER_CODE = 400;
    public static final String INVALID_PARAMETER_MSG = "invalid parameter";

    /**
     * 权限不足
     */
    public static final int PERMISSION_DENIED_CODE = 403;
    public static final String PERMISSION_DENIED_MSG = "permission denied";

    /**
     * 账号未登录
     */
    public static final int NO_LOGIN_CODE = 401;
    public static final String NO_LOGIN_MSG = "no login";

    /**
     * 登陆失效
     */
    public static final int LOGIN_INVALID_CODE = 402;
    public static final String LOGIN_INVALID_MSG = "login invalid";

}

package tech.xs.system.constant;

/**
 * 缓存常量
 *
 * @author 沈家文
 * @date 2021/9/3 20:13
 */
public class CacheConstant {

    /**
     * 账号注册时,邮箱验证码的缓存Key
     */
    public static final String ACCOUNT_REGISTER_EMAIL_CODE = "account:register:email:code:";

    /**
     * 账号注册时邮箱验证码的缓存有效期
     */
    public static final long ACCOUNT_REGISTER_EMAIL_COD_TIME = 5 * 60 * 1000;

    /**
     * 账号注册时,验证码输入错误
     */
    public static final String ACCOUNT_REGISTER_EMAIL_CODE_ERROR_NUM = "account:register:email:code:error:num:";


    /**
     * 账号注册时,手机号验证码的缓存Key
     */
    public static final String ACCOUNT_REGISTER_PHONE_CODE = "account:register:email:code";
    /**
     * 账号注册时手机号验证码的缓存有效期
     */
    public static final long ACCOUNT_REGISTER_PHONE_COD_TIME = 5 * 60 * 1000;

    /**
     * 重置密码是,邮箱验证码的缓存Key
     */
    public static final String PASSWORD_RESET_EMAIL_CODE = "password:reset:email:code:";

    /**
     * 重置密码时时邮箱验证码的缓存有效期
     */
    public static final long PASSWORD_RESET_EMAIL_COD_TIME = 5 * 60 * 1000;
    /**
     * 重置密码时,验证码输入错误
     */
    public static final String PASSWORD_RESET_EMAIL_CODE_ERROR_NUM = "password:reset:email:code:error:num:";

}

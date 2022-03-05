package tech.xs.auth.constant;

import tech.xs.framework.annotation.doc.Quote;
import tech.xs.framework.annotation.doc.QuoteValue;

/**
 * 登陆类型常量
 *
 * @author 沈家文
 * @date 2020/8/13
 */
@Quote(title = "认证授权类型")
public class AuthGrantTypeConstant {

    @QuoteValue(title = "账号密码登陆")
    public static final int PASSWORD = 1;

    @QuoteValue(title = "Token登陆")
    public static final int TOKEN = 2;
}

package tech.xs.auth.constant;

import tech.xs.framework.annotation.doc.Quote;
import tech.xs.framework.annotation.doc.QuoteValue;

/**
 * 客户端类型常量
 *
 * @author 沈家文
 * @date 2021/6/1 9:19
 */
@Quote(title = "登陆客户端类型")
public class ClientTypeConstant {


    @QuoteValue(title = "浏览器")
    public static final long BROWSER = 1;

    @QuoteValue(title = "服务端")
    public static final long SERVER = 2;

    @QuoteValue(title = "安卓端")
    public static final long ANDROID = 3;

    @QuoteValue(title = "IOS")
    public static final long IOS = 4;

}

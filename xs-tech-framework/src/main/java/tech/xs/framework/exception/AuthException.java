package tech.xs.framework.exception;

import tech.xs.framework.base.BaseException;
import tech.xs.framework.enums.ResultEnum;

/**
 * @author 沈家文
 * @date 2020/12/3 14:01
 */
public class AuthException extends BaseException {

    public AuthException(String msg) {
        super(msg);
    }

    public AuthException(String msg, boolean isPrint) {
        super(msg, isPrint);
    }

    public AuthException(Integer code, String msg) {
        super(code, msg);
    }

    public AuthException(Integer code, String msg, boolean isPrint) {
        super(code, msg, isPrint);
    }

    public AuthException(ResultEnum resultEnum) {
        super(resultEnum);
    }

    public AuthException(ResultEnum resultEnum, boolean isPrint) {
        super(resultEnum, isPrint);
    }

}

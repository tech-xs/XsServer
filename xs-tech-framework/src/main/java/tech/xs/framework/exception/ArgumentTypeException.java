package tech.xs.framework.exception;

import lombok.Getter;
import lombok.Setter;
import tech.xs.framework.base.BaseException;

/**
 * 参数类型错误异常
 *
 * @author 沈家文
 * @date 2020/10/14
 */
public class ArgumentTypeException extends BaseException {

    /**
     * 参数名
     */
    @Getter
    @Setter
    private String argumentName;

    public ArgumentTypeException() {

    }

    public ArgumentTypeException(String argumentName, String msg) {
        super(msg);
        this.argumentName = argumentName;
    }

}

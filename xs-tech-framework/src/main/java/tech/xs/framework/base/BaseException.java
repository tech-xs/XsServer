package tech.xs.framework.base;

import lombok.Getter;
import lombok.Setter;
import tech.xs.framework.enums.ResultEnum;

/**
 * Exception基类
 *
 * @author 沈家文
 * @date 2020/7/28
 */
public class BaseException extends RuntimeException {

    @Getter
    @Setter
    private Integer code;

    /**
     * 是否在日志中输出异常信息
     */
    @Getter
    @Setter
    private boolean isPrint = true;

    /**
     * 是否在响应信息中输出异常栈信息
     */
    @Getter
    @Setter
    private boolean isResponseStackMsg = false;

    @Getter
    @Setter
    private Exception rawException;

    public BaseException() {
        super();
    }

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(String msg, boolean isPrint) {
        super(msg);
        this.isPrint = isPrint;
    }

    public BaseException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public BaseException(Integer code, String msg, boolean isPrint) {
        this(code, msg);
        this.isPrint = isPrint;
    }

    public BaseException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public BaseException(ResultEnum resultEnum, boolean isPrint) {
        this(resultEnum);
        this.isPrint = isPrint;
    }
}

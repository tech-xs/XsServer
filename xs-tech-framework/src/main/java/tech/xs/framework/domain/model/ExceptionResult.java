package tech.xs.framework.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.xs.framework.enums.ResultEnum;

import java.util.Date;
import java.util.UUID;

/**
 * 异常返回结果
 *
 * @author 沈家文
 * @date 2020/7/29
 */
@Getter
@Setter
@ToString
public class ExceptionResult<T> extends ApiResult<T> {

    /**
     * 异常ID
     * 异常的唯一标识符
     */
    private String exceptionId;

    /**
     * 异常发生的时间
     */
    private Date time;

    public ExceptionResult() {
        exceptionId = UUID.randomUUID().toString();
        time = new Date();
    }

    public ExceptionResult(Integer code, String msg, T data) {
        super(code, msg, data);
        exceptionId = UUID.randomUUID().toString();
        time = new Date();
    }

    public ExceptionResult(ResultEnum resultCode) {
        super(resultCode);
        exceptionId = UUID.randomUUID().toString();
        time = new Date();
    }

    public ExceptionResult(ResultEnum resultCode, T data) {
        super(resultCode, data);
        exceptionId = UUID.randomUUID().toString();
        time = new Date();
    }

}

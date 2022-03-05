package tech.xs.framework.config;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import tech.xs.common.util.ExceptionUtil;
import tech.xs.framework.exception.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import tech.xs.framework.domain.model.ApiResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.xs.framework.enums.ResultEnum;
import tech.xs.framework.base.BaseException;
import tech.xs.framework.exception.ArgumentTypeException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.Set;

/**
 * 全局异常处理器
 *
 * @author 沈家文
 * @date 2020/7/28
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局未知异常处理
     *
     * @param e 异常信息
     */
    @ExceptionHandler(value = Exception.class)
    public ApiResult<Object> basicExceptionHandler(HttpServletResponse response, Exception e) {
        ApiResult<Object> result = ApiResult.unknownError();
        printfExceptionInfo(e);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return result;
    }

    /**
     * 自定义基本异常的处理
     *
     * @param e 异常信息
     * @return 返回接口对应的结果集
     */
    @ExceptionHandler(BaseException.class)
    public ApiResult<Object> baseExceptionHandler(BaseException e) {
        ApiResult<Object> result = new ApiResult<>(e.getCode(), e.getMessage());
        String msg = null;
        if (e.isPrint()) {
            msg = printfExceptionInfo(e);
        }
        if (e.isResponseStackMsg()) {
            if (e.getRawException() != null) {
                msg = ExceptionUtil.getStackMsg(e.getRawException());
            } else if (msg == null) {
                msg = ExceptionUtil.getStackMsg(e);
            }
            result.setData(msg);
        }
        return result;
    }

    /**
     * 授权失败异常
     *
     * @param e 异常信息
     * @return 返回接口对应的结果集
     */
    @ExceptionHandler(AuthException.class)
    public ApiResult<Object> authExceptionHandler(AuthException e) {
        return new ApiResult<>(e.getCode(), e.getMessage());
    }

    /**
     * Http消息读取异常
     *
     * @param e 异常信息
     * @return 返回给接口对应的结果集
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ApiResult<Object> invalidFormatExceptionHandler(HttpMessageNotReadableException e) {
        Throwable cause = e.getCause();
        ApiResult<Object> result = new ApiResult<>();
        result.setCode(ResultEnum.httpMessageReadError.getCode());
        if (cause instanceof InvalidFormatException) {
            result.setMsg("json type format error");
        } else {
            result.setMsg(ResultEnum.httpMessageReadError.getMsg());
        }
        printfExceptionInfo(e);
        return result;
    }

    /**
     * 请求参数验证异常
     */
    @ExceptionHandler(BindException.class)
    public ApiResult<Object> validatedBindExceptionHandler(BindException e) {
        FieldError errorField = e.getFieldError();
        if (errorField != null) {
            return ApiResult.invalidParameter(errorField.getField(), errorField.getDefaultMessage());
        } else {
            return ApiResult.invalidParameter();
        }
    }

    /**
     * 请求参数验证异常
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ApiResult<Object> constraintViolationExceptionHandler(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        for (ConstraintViolation constraintViolation : constraintViolations) {
            Path propertyPath = constraintViolation.getPropertyPath();
            if (propertyPath instanceof PathImpl) {
                PathImpl path = (PathImpl) propertyPath;
                NodeImpl leafNode = path.getLeafNode();
                String message = constraintViolation.getMessage();
                return ApiResult.invalidParameter(leafNode.getName(), message);
            }
        }
        return new ApiResult<>(ResultEnum.invalidParameter.getCode(), e.getMessage(), null);
    }

    /**
     * 请求参数验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult<Object> validExceptionHandler(MethodArgumentNotValidException e) {
        FieldError errorField = e.getBindingResult().getFieldError();
        if (errorField != null) {
            return ApiResult.invalidParameter(errorField.getField(), errorField.getDefaultMessage());
        } else {
            return ApiResult.invalidParameter();
        }
    }

    /**
     * 请求参数验证异常(参数类型错误)
     */
    @ExceptionHandler(ArgumentTypeException.class)
    public ApiResult<Object> argumentTypeExceptionHandler(ArgumentTypeException e) {
        return ApiResult.invalidParameter(e.getArgumentName(), e.getMessage());
    }

    private String printfExceptionInfo(Exception e) {
        try {
            StringBuilder strBuf = new StringBuilder();
            String stackMsg = ExceptionUtil.getStackMsg(e);
            strBuf.append("\r\n**********exception info start**********\r\n");
            if (e instanceof BaseException) {
                Exception rawException = ((BaseException) e).getRawException();
                if (rawException != null) {
                    strBuf.append(ExceptionUtil.getStackMsg(rawException));
                }
            }
            strBuf.append(stackMsg);
            strBuf.append("**********exception info end**********");
            strBuf.append("\r\n");
            String msg = strBuf.toString();
            log.error(msg);
            return stackMsg;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
}

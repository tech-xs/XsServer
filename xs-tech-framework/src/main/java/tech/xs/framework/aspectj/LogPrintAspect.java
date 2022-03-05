package tech.xs.framework.aspectj;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import tech.xs.framework.util.ServletUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志打印处理
 *
 * @author 沈家文
 * @date 2021/2/23 11:16
 */
@Aspect
@Slf4j
@Component
public class LogPrintAspect {

    /**
     * 配置织入点[RequestMapping]
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestLog() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postLog() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getLog() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void deleteLog() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putLog() {
    }


    @AfterReturning(pointcut = "requestLog() || postLog() || getLog() || deleteLog() || putLog()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        printLog(joinPoint, null, jsonResult);
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
    public void exceptionLog() {
    }

    @AfterReturning(pointcut = "exceptionLog()", returning = "jsonResult")
    public void exceptionReturning(JoinPoint joinPoint, Object jsonResult) {
        printLog(joinPoint, null, jsonResult);
    }

    /**
     * 记录异常日志
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "requestLog() || postLog() || getLog() || deleteLog() || putLog() || exceptionLog()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        printLog(joinPoint, e, null);
    }

    /**
     * 日志打印
     *
     * @param joinPoint
     * @param e
     * @param jsonResult
     */
    private void printLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        try {
            HttpServletRequest request = ServletUtil.getRequest();
            //响应数据
            String responseData = null;
            if (jsonResult != null) {
                responseData = JSONUtil.parse(jsonResult).toString();
            }

            //请求参数
            String params = "";
            Object[] args = joinPoint.getArgs();
            if (args != null && args.length > 0) {
                if (!(args[0] instanceof Exception)) {
                    params = JSONUtil.parse(args).toString();
                }
            }

            StringBuffer buffer = new StringBuffer();
            buffer.append("\n------- request and response ---------\n");
            //打印请求地址
            buffer.append("URI:");
            buffer.append(request.getRequestURL());
            buffer.append("\n");
            //打印请求方式
            buffer.append("method:");
            buffer.append(request.getMethod());
            buffer.append("\n");
            //打印请求参数
            buffer.append("request params");
            buffer.append(params);
            buffer.append("\n");
            //打印响应数据
            buffer.append("response:");
            buffer.append(responseData);
            buffer.append("\n");
            //打印类名
            buffer.append("className:");
            buffer.append(joinPoint.getTarget().getClass().getName());
            buffer.append("\n");
            //打印方法名
            buffer.append("methodName:");
            buffer.append(joinPoint.getSignature().getName());
            buffer.append("\n");
            //打印异常信息
            if (e != null) {
                buffer.append("exception:");
                buffer.append(e.getMessage());
                buffer.append("\n");
            }
            buffer.append("------- request and response ---------\n");
            log.info(buffer.toString());
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

}

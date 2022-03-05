package tech.xs.system.config;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import tech.xs.common.constant.Symbol;
import tech.xs.framework.context.XsContext;
import tech.xs.framework.annotation.log.Log;
import tech.xs.framework.util.ServletUtil;
import tech.xs.system.domain.entity.SysInterfaceLog;
import tech.xs.system.service.SysInterfaceLogService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 操作日志记录处理
 *
 * @author 沈家文
 * @date 2021/2/23 11:16
 */
@Aspect
@Slf4j
@Component
public class LogSaveAspect {

    @Resource
    private SysInterfaceLogService sysInterfaceLogService;

    /**
     * 日志记录切入点
     */
    @Pointcut("@annotation(tech.xs.framework.annotation.log.Log)")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        SysInterfaceLog interfaceLog = new SysInterfaceLog();
        try {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }

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

            interfaceLog.setRequestUrl(request.getRequestURI());
            interfaceLog.setRequestContent(params);
            interfaceLog.setResponseContent(responseData);
            interfaceLog.setCompanyId(XsContext.getCompanyId());
            interfaceLog.setBusinessKey(joinPoint.getTarget().getClass().getName() + Symbol.POINT + joinPoint.getSignature().getName());
            if (e != null) {
                interfaceLog.setResponseContent(e.getMessage());
            }
        } catch (Exception exp) {
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
            interfaceLog.setResponseContent(exp.getMessage());
        }
        sysInterfaceLogService.add(interfaceLog);
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (int i = 0; i < paramsArray.length; i++) {
                if (!isFilterObject(paramsArray[i])) {
                    Object jsonObj = JSONUtil.parse(paramsArray[i]);
                    params += jsonObj.toString() + " ";
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    public boolean isFilterObject(final Object o) {
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }

}

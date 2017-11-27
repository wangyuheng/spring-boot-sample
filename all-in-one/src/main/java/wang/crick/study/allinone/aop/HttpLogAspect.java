package wang.crick.study.allinone.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import wang.crick.study.allinone.annotation.HttpLog;
import wang.crick.study.allinone.restful.RestApiResponse;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

@Aspect
public class HttpLogAspect {

    private Logger log = LoggerFactory.getLogger(HttpLogAspect.class);

    @Pointcut("@annotation(wang.crick.study.allinone.annotation.HttpLog)")
    public void logAnnotation() {
    }

    private ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();
    private ThreadLocal<String> traceIdThreadLocal = new ThreadLocal<>();

    private Optional<HttpLog> getLogAnnotation(JoinPoint joinPoint) {
        if (joinPoint instanceof MethodInvocationProceedingJoinPoint) {
            Signature signature = joinPoint.getSignature();
            if (signature instanceof MethodSignature) {
                MethodSignature methodSignature = (MethodSignature) signature;
                Method method = methodSignature.getMethod();
                if (method.isAnnotationPresent(HttpLog.class)) {
                    return Optional.of(method.getAnnotation(HttpLog.class));
                }
            }
        }
        return Optional.empty();
    }

    private HttpServletRequest getRequest() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        return sra.getRequest();
    }

    private String getRequestPath(HttpServletRequest request) {
        return (null != request.getServletPath() && request.getServletPath().length() > 0)
                ? request.getServletPath() : request.getPathInfo();
    }

    private long costTime() {
        return System.currentTimeMillis() - startTimeThreadLocal.get();
    }

    @Before("logAnnotation()")
    public void requestLog(JoinPoint joinPoint) {
        try {
            Optional<HttpLog> httpLog = getLogAnnotation(joinPoint);
            httpLog.ifPresent(anno -> {
                HttpServletRequest request = getRequest();
                traceIdThreadLocal.set(UUID.randomUUID().toString());
                startTimeThreadLocal.set(System.currentTimeMillis());
                List<String> excludes = Arrays.asList(anno.exclude());
                List<Object> params = new ArrayList<>();
                StringBuilder logMsg = new StringBuilder();
                logMsg.append("REQUEST_LOG. traceId:{}. ")
                        .append("requestUrl: ")
                        .append(getRequestPath(request))
                        .append(" -PARAMS- ");
                params.add(traceIdThreadLocal.get());
                request.getParameterMap().forEach((k, v) -> {
                    if (!excludes.contains(k)) {
                        logMsg.append(k).append(": {}, ");
                        params.add(v);
                    }
                });
                if (anno.headerParams().length > 0) {
                    logMsg.append(" -HEADER_PARAMS- ");
                    Arrays.asList(anno.headerParams()).forEach(param -> {
                        logMsg.append(param).append(": {}, ");
                        params.add(request.getHeader(param));
                    });
                }
                log.info(logMsg.toString(), params.toArray());
            });
        } catch (Exception ignore) {
            log.warn("print request log fail!", ignore);
        }
    }

    @AfterReturning(returning = "restApiResponse", pointcut = "logAnnotation()")
    public void response(JoinPoint joinPoint, RestApiResponse restApiResponse) {
        try {
            Optional<HttpLog> httpLog = getLogAnnotation(joinPoint);
            httpLog.ifPresent(anno -> {
                if (!anno.ignoreResponse()) {
                    log.info("RESPONSE_LOG. traceId:{}, result:{}, cost:{}",
                            traceIdThreadLocal.get(), restApiResponse, costTime());
                }
            });
        } catch (Exception ignore) {
            log.warn("print response log fail!", ignore);
        }
    }

    @AfterThrowing(throwing = "e", pointcut = "logAnnotation()")
    public void throwing(JoinPoint joinPoint, Exception e) {
        try {
            Optional<HttpLog> httpLog = getLogAnnotation(joinPoint);
            httpLog.ifPresent(anno -> {
                if (!anno.ignoreResponse()) {
                    log.info("ERROR_LOG. traceId:{}, cost:{}",
                            traceIdThreadLocal.get(), costTime(), e);
                }
            });
        } catch (Exception ignore) {
            log.warn("print error log fail!", ignore);
        }
    }

}
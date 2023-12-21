package com.github.gribanoveu.cuddle.utils.aspects;

import com.github.gribanoveu.cuddle.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class RequestLoggingAspect {
    private final JsonUtils jsonUtils;

    @Around("@annotation(LogRequest)")
    public Object logRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        var attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        var request = attributes.getRequest();
        log.info("""
                [REQUEST] Incoming request to server:
                {
                    "Method":"%1$s",
                    "URI":"%3$s",
                    "RequestId":"%2$s",
                    "Details":[%4$s]
                }
                """.formatted(request.getMethod(), request.getRequestId(), request.getRequestURI(),
                jsonUtils.convertDtoToJson(joinPoint.getArgs()[0]))
        );

        return joinPoint.proceed();
    }

    @AfterReturning(pointcut = "@annotation(LogResponse)", returning = "result")
    public void logResponse(JoinPoint joinPoint, Object result) {
        var attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        var annotation = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(LogResponse.class);
        var request = attributes.getRequest();
        var message = ("""
                {
                    "Method":"%1$s",
                    "URI":"%3$s",
                    "RequestId":"%2$s",
                    "Details":[%4$s]
                }
                """.formatted(request.getMethod(), request.getRequestId(),request.getRequestURI(),
                jsonUtils.convertDtoToJson(result)));

        if (annotation.message().isBlank()) log.info("[RESPONSE] Outgoing server response:\n {}", message);
        else log.info("[RESPONSE] {}\n {}", annotation.message(), message);
    }
}

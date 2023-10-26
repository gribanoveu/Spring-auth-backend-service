package com.github.gribanoveu.cuddly.utils.aspects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gribanoveu.cuddly.utils.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Map;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class RequestLoggingAspect {
    private final JsonUtils jsonUtils;

    @Around("@annotation(LogRequest)")
    public Object logRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        log.info("""
                Incoming request to server:
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
    public void logResponse(Object result) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        log.info("""
                Outgoing server response:
                {
                    "Method":"%1$s",
                    "URI":"%3$s",
                    "RequestId":"%2$s",
                    "Details":[%4$s]
                }
                """.formatted(request.getMethod(), request.getRequestId(),request.getRequestURI(),
                jsonUtils.convertDtoToJson(result)));
    }
}

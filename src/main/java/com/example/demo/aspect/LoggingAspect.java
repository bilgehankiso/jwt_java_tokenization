package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.example.demo.controller.*.*(..))")
    public void logBeforeMethodCall(JoinPoint joinPoint) {
        logger.info("Method called: " + joinPoint.getSignature().toShortString());
    }

    @AfterReturning(value = "execution(* com.example.demo.controller.*.*(..))", returning = "result")
    public void logAfterMethodCall(JoinPoint joinPoint, Object result) {
        logger.info("Method finished: " + joinPoint.getSignature().toShortString());
        if (result != null) {
            logger.info("Response: " + result.toString());
        }
    }

    @AfterThrowing(value = "execution(* com.example.demo.controller.*.*(..))", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        logger.error("Exception : " + joinPoint.getSignature().toShortString(), exception);
    }
}

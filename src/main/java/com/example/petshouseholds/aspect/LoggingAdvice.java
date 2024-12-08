package com.example.petshouseholds.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LoggingAdvice {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAdvice.class);

    @Before("within(@org.springframework.web.bind.annotation.RestController *) || within(@org.springframework.stereotype.Controller *)")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Executing method: {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "within(@org.springframework.web.bind.annotation.RestController *) || within(@org.springframework.stereotype.Controller *)", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        logger.info("Method {} executed successfully, returned: {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = "within(@org.springframework.web.bind.annotation.RestController *) || within(@org.springframework.stereotype.Controller *)", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        logger.error("Method {} threw an exception: {}", joinPoint.getSignature().getName(), exception.getMessage());
    }
}

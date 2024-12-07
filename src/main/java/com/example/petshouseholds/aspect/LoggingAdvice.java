package com.example.petshouseholds.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAdvice {

    // Pointcut to target all service layer methods
    @Pointcut("execution(* com.example.petshouseholds.service..*(..))")
    public void serviceLayerExecution() {}

    // Pointcut to target all repository layer methods
    @Pointcut("execution(* com.example.petshouseholds.repository..*(..))")
    public void repositoryLayerExecution() {}

    // Before advice for service layer methods
    @Before("serviceLayerExecution()")
    public void logBeforeServiceExecution(JoinPoint joinPoint) {
        log.info("Entering Service Method: {} with arguments: {}",
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs());
    }

    // AfterReturning advice for service layer methods
    @AfterReturning(pointcut = "serviceLayerExecution()", returning = "result")
    public void logAfterServiceExecution(JoinPoint joinPoint, Object result) {
        log.info("Exiting Service Method: {} with result: {}",
                joinPoint.getSignature().toShortString(),
                result);
    }

    // Before advice for repository layer methods
    @Before("repositoryLayerExecution()")
    public void logBeforeRepositoryExecution(JoinPoint joinPoint) {
        log.info("Entering Repository Method: {} with arguments: {}",
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs());
    }

    // AfterReturning advice for repository layer methods
    @AfterReturning(pointcut = "repositoryLayerExecution()", returning = "result")
    public void logAfterRepositoryExecution(JoinPoint joinPoint, Object result) {
        log.info("Exiting Repository Method: {} with result: {}",
                joinPoint.getSignature().toShortString(),
                result);
    }
}

package com.spring.librarymanagementsystem.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.spring.librarymanagementsystem.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Before Method: " + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.spring.librarymanagementsystem.service.*.*(..))", returning = "result")
    public void logAfterMethods(Object result) {
        System.out.println("After method execution. Result: " + result);
    }

    @AfterThrowing(pointcut = "execution(* com.spring.librarymanagementsystem.service.*.*(..))", throwing = "exception")
    public void logAfterThrowingMethods(Exception exception) {
        System.out.println("Exception thrown: " + exception.getMessage());
    }
}

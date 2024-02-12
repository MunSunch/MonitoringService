package com.munsun.utils.logger.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LogAspect {
    @Pointcut("within(@com.munsun.utils.logger.aspects.annotations.Loggable *)")
    public void annotatedByLog() {}

    @Pointcut("execution(* *(..))")
    public void publicMethod() {}

    @Around("publicMethod()")
    public Object loggingSpeed(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.print("Calling method " + joinPoint.getSignature() + ":");
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis() - start;
        System.out.println("Execution of method " + joinPoint.getSignature()
                         + " finished. Execution time is " + end + " ms.");
        return result;
    }
}
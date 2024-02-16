package com.munsun.utils.logger.aspects;

import com.munsun.utils.logger.aspects.annotations.SecurityLog;
import com.munsun.utils.logger.model.JournalRecord;
import com.munsun.utils.logger.service.JournalService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Date;

//@Aspect
@RequiredArgsConstructor
public class LogAspect {
    private final JournalService service;

    @Pointcut("execution(* *(..))")
    public void publicMethod() {}

    @Pointcut("within(@com.munsun.utils.logger.annotations.SecurityLog *)")
    public void annotatedBySecurityLog(){}

    @Around("publicMethod()")
    public Object loggingSpeed(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.print("Calling method " + joinPoint.getSignature() + ":");
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis() - start;
        System.out.println("Execution of method " + joinPoint.getSignature() + " finished. Execution time is " + end + " ms.");
        return result;
    }
}
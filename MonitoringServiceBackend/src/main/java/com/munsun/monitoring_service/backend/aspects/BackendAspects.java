package com.munsun.monitoring_service.backend.aspects;

import com.munsun.monitoring_service.backend.aspects.annotations.Journal;
import com.munsun.monitoring_service.backend.security.models.SecurityUser;
import com.munsun.utils.logger.model.JournalRecord;
import com.munsun.utils.logger.service.JournalService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Aspect
@Component
@RequiredArgsConstructor
public class BackendAspects {
    private final JournalService journalService;

    @Pointcut("execution(public * com.munsun.monitoring_service.backend..*(..))")
    public void publicMethod() {}

    @Pointcut("!execution(* com.munsun.monitoring_service.backend.Main.*(..))")
    public void noMainMethod() {}

    @Pointcut("@annotation(org.springframework.context.annotation.Configuration)")
    public void isAnnotatedConfiguration() {}

    @Before("@annotation(Journal) && publicMethod()")
    public void journalUserAction(JoinPoint joinPoint, Journal journal) throws Throwable {
        var securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var message = String.format("Login=%s; Role=%s; Action=%s",
                securityUser.getUsername(), securityUser.getRole().name(), journal.message());
        journalService.journal(new JournalRecord(new Date(System.currentTimeMillis()), message));
    }

//    @Around("publicMethod() && !within(com.munsun.monitoring_service.backend.configurations.*) && noMainMethod()")
//    public Object measureSpeedExecution(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.print("Calling method " + joinPoint.getSignature() + ":");
//        long start = System.currentTimeMillis();
//        Object result = joinPoint.proceed();
//        long end = System.currentTimeMillis() - start;
//            System.out.println("Execution of method " + joinPoint.getSignature() + " finished. Execution time is " + end + " ms.");
//            return result;
//    }
}
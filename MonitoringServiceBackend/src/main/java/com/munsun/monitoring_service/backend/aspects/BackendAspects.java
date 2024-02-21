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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Aspect
@Component
@RequiredArgsConstructor
public class BackendAspects {
    private final JournalService journalService;

    @Before("@annotation(Journal)")
    public void journalUserAction(JoinPoint joinPoint, Journal journal) throws Throwable {
        var securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var message = String.format("Login=%s; Role=%s; Action=%s",
                securityUser.getUsername(), securityUser.getRole().name(), journal.message());
        journalService.journal(new JournalRecord(new Date(System.currentTimeMillis()), message));
    }

    @Around("!within(@org.springframework.context.annotation.Configuration *) && execution(* com.munsun.monitoring_service.backend..*(..))")
    public Object measureSpeedExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.print("Calling method " + joinPoint.getSignature() + ":");
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis() - start;
        System.out.println("Execution of method " + joinPoint.getSignature() + " finished. Execution time is " + end + " ms.");
        return result;
    }
}
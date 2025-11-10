package org.example.weak5.aop.exercise.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ProxyInfoAspect {

    @Around("execution(* org.example.weak5.aop.exercise.service..*(..))")
    public Object printProxyInfo(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proxy = joinPoint.getThis();   // 프록시 객체
        Object target = joinPoint.getTarget(); // 실제 타깃 객체

        log.info("==== [Proxy Info] ====");
        log.info("Proxy Class  : {}", proxy.getClass());
        log.info("Target Class : {}", target.getClass());
        log.info("Method       : {}", joinPoint.getSignature());
        log.info("======================");

        return joinPoint.proceed(); // 실제 메서드 실행
    }
}

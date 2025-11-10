package org.example.weak5.aop.exercise.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@Aspect
public class LogAspect {
    // Pointcut: service 패키지 내의 모든 메서드
    @Pointcut("execution(* org.example.weak5.aop.exercise.service..*(..))")
    private void allServiceMethods() {}

    // Advice: 실행 전 로깅
    @Before("allServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("[Before] 호출 메서드: {}", joinPoint.getSignature());
    }

    // Advice: 실행 후 로깅
    @AfterReturning(value = "allServiceMethods()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        log.info("[JoinPoint] 결과: {}", joinPoint);
        log.info("[AfterReturning] 결과: {}", result);
    }
}

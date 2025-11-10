package org.example.weak5.aop.exercise.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TimeAspect {

    @Around("execution(* org.example.weak5.aop.exercise.service..*(..))")
    public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        log.info("[Around] 시작: {}", joinPoint.getSignature());

        Object result = joinPoint.proceed();

        long end = System.nanoTime();
        double elapsedMs = (end - start) / 1_000_000.0; // 나노초 → 밀리초 변환

        log.info("[Around] 종료: {} ({} ms)", joinPoint.getSignature(), String.format("%.3f", elapsedMs));
        return result;
    }
}


package org.example.weak5.aop.exercise.aspect;

import org.springframework.aop.Advisor;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdvisorConfig {
    // Advisor를 직접 등록하는 프로그래밍 방식 AOP
    @Bean
    public Advisor logAdvisor() {
        // Pointcut 지정
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* org.example.weak5.aop.exercise.service..*(..))");

        // Advice 지정
        MethodBeforeAdvice advice = (method, args, target) ->
                System.out.println("[Advisor] " + method.getName() + "() 실행 전 로그");

        // Advisor = Pointcut + Advice
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}

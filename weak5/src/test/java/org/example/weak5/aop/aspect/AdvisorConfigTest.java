package org.example.weak5.aop.aspect;

import org.example.weak5.aop.exercise.aspect.AdvisorConfig;
import org.example.weak5.aop.exercise.service.AService;
import org.example.weak5.aop.exercise.service.BService;
import org.example.weak5.aop.exercise.service.impl.AServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class AdvisorConfigTest {
    @Test
    @DisplayName("인터페이스 기반 서비스 (JDK Proxy)")
    void jdkProxyTest() {
        // 실제 타깃 객체 생성
        AService target = new AServiceImpl();

        // ProxyFactory 생성 (스프링 AOP 핵심)
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // Advisor 수동 등록
        AdvisorConfig config = new AdvisorConfig();
        proxyFactory.addAdvisor(config.logAdvisor());

        // Proxy 생성
        AService proxy = (AService) proxyFactory.getProxy();

        // Proxy 타입 출력 (JDK 동적 프록시 or CGLIB)
        System.out.println("Proxy class = " + proxy.getClass());
        System.out.println("Target class = " + target.getClass());

        // 실제 실행 (Before Advice + Target 로직)
        proxy.save();

        // 간단 검증 (ProxyFactory가 프록시 객체 생성했는지)
        assertThat(proxy).isNotEqualTo(target);
    }

    @Test
    @DisplayName(" 인터페이스 없는 클래스 (CGLIB Proxy)")
    void cglibProxyTest() {
        // 실제 타깃 객체 생성
        BService target = new BService();

        // ProxyFactory 생성 (스프링 AOP 핵심)
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // Advisor 수동 등록
        AdvisorConfig config = new AdvisorConfig();
        proxyFactory.addAdvisor(config.logAdvisor());

        // Proxy 생성
        BService proxy = (BService) proxyFactory.getProxy();

        // Proxy 타입 출력 (JDK 동적 프록시 or CGLIB)
        System.out.println("Proxy class = " + proxy.getClass());
        System.out.println("Target class = " + target.getClass());

        // 실제 실행 (Before Advice + Target 로직)
        proxy.save();

        // 간단 검증 (ProxyFactory가 프록시 객체 생성했는지)
        assertThat(proxy).isNotEqualTo(target);
    }
}
package org.example.weak5.aop.sampleProject.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Slf4j
// @Component
public class LoggingAspectV1 {
    // 공통 Pointcut
    @Pointcut("execution(* org.example.weak5.aop.sampleProject.service..*(..))")
    private void serviceMethods() {}

    /**
     * 1) 입력 파라미터 로깅 (Before)
     */
    @Before("serviceMethods()")
    public void logInput(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        writeToFile("input.txt",
                "Time: " + LocalDateTime.now() +
                        "\nMethod: " + methodName +
                        "\nArgs: " + Arrays.toString(args) + "\n\n");

        log.info("[AOP] 입력 파라미터 로깅 완료");
    }

    /**
     * 2) 출력 값 로깅 (AfterReturning)
     */
    @AfterReturning(value = "serviceMethods()", returning = "result")
    public void logOutput(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().toShortString();

        writeToFile("output.txt",
                "Time: " + LocalDateTime.now() +
                        "\nMethod: " + methodName +
                        "\nResult: " + result + "\n\n");

        log.info("[AOP] 출력 파라미터 로깅 완료");
    }

    private void writeToFile(String fileName, String content) {
        try (FileWriter fw = new FileWriter(fileName, true)) {
            fw.write(content);
        } catch (IOException e) {
            log.error("파일 저장 실패", e);
        }
    }
}

package org.example.weak5.aop.sampleProject.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    @Around("execution(* org.example.weak5.aop.sampleProject.service..*(..))")
    public Object logInputOutput(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        // 입력 로그
        writeToFile("input.txt",
                "Time: " + LocalDateTime.now() +
                        "\nMethod: " + methodName +
                        "\nArgs: " + Arrays.toString(args) + "\n\n");

        log.info("[AOP] 입력 파라미터 로깅 완료");

        // 실제 메서드 실행
        Object result = joinPoint.proceed();

        // 출력 로그
        writeToFile("output.txt",
                "Time: " + LocalDateTime.now() +
                        "\nMethod: " + methodName +
                        "\nResult: " + result + "\n\n");

        log.info("[AOP] 출력 파라미터 로깅 완료");

        return result;
    }

    private void writeToFile(String fileName, String content) {
        try (FileWriter fw = new FileWriter(fileName, true)) {
            fw.write(content);
        } catch (IOException e) {
            log.error("파일 저장 실패", e);
        }
    }
}


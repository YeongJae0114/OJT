package org.example.weak5.aop.exercise.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BService {
    public String save() {
        log.info("[Service] 핵심 로직 실행");
        return "BService 완료";
    }
}

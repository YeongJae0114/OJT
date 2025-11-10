package org.example.weak5.aop.exercise.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.weak5.aop.exercise.service.AService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AServiceImpl implements AService {
    @Override
    public void save() {
        log.info("[Service] 핵심 로직 실행");
    }
}

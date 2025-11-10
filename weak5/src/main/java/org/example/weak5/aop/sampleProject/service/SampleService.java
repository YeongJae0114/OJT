package org.example.weak5.aop.sampleProject.service;

import lombok.extern.slf4j.Slf4j;
import org.example.weak5.aop.sampleProject.dto.RequestDto;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SampleService {
    public String save(RequestDto dto) {
        log.info("[Service] 핵심 로직 실행");
        return "SampleService 완료";
    }
}

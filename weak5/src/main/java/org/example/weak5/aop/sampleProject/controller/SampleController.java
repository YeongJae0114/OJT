package org.example.weak5.aop.sampleProject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.weak5.aop.sampleProject.dto.RequestDto;
import org.example.weak5.aop.sampleProject.service.SampleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class SampleController {
    private final SampleService sampleService;

    @PostMapping("/name")
    public String getName(@RequestBody RequestDto dto) {
        log.info("Controller 호출: /name");
        sampleService.save(dto);
        return dto.getName();
    }

    @PostMapping("/age")
    public String getAge(@RequestBody RequestDto dto) {
        log.info("Controller 호출: /age");
        sampleService.save(dto);
        return String.valueOf(dto.getAge());
    }

    @PostMapping("/city")
    public String getCity(@RequestBody RequestDto dto) {
        log.info("Controller 호출: /city");
        sampleService.save(dto);
        return dto.getCity();
    }
}

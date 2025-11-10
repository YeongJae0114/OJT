package org.example.weak5.aop.exercise.controller;


import lombok.RequiredArgsConstructor;
import org.example.weak5.aop.exercise.service.AService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AController {
    private final AService aService;

    @GetMapping("/aaaa")
    public String hello() {
        aService.save();
        return "ok";
    }
}

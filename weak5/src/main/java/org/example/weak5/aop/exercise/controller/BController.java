package org.example.weak5.aop.exercise.controller;


import lombok.RequiredArgsConstructor;
import org.example.weak5.aop.exercise.service.BService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BController {
    private final BService bService;

    @GetMapping("/bbbb")
    public String save() {
        bService.save();
        return "ok";
    }
}

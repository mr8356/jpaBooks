package jpabook.jpastore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
    @GetMapping("/")
    public String home() {
        log.info("spring home");
        return "home";
    }
    
    @GetMapping("/mvc2")
    public String mvc2_home() {
        return "home";
    }
}

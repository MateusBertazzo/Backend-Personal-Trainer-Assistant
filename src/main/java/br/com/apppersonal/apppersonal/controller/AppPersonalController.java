package br.com.apppersonal.apppersonal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AppPersonalController {

    @GetMapping(value = "/test")
    public String testApi() {
        return "Olá mundo";
    }
}

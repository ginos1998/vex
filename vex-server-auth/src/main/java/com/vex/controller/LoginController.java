package com.vex.controller;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

    @PostMapping("/logout")
    public String logoutOK(HttpSecurity http) throws Exception {
        http.logout(
            logout -> logout
                .logoutSuccessUrl("/login?logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
        );
//        http.logout().logoutSuccessUrl("login?logout")
//                .deleteCookies("JSESSIONID")
//                .invalidateHttpSession(true)
//                .clearAuthentication(true);
        return "login?logout";
    }

}

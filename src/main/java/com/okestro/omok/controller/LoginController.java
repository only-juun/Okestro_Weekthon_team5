//package com.okestro.omok.controller;
//
//import com.okestro.omok.service.LoginService;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/login/oauth2")
//public class LoginController {
//    LoginService loginService;
//
//    public LoginController(LoginService loginService) {
//        this.loginService = loginService;
//    }
//
//    @GetMapping("/code/{registrationId}")
//    public void googleLogin(@RequestParam String code, @PathVariable String registrationId) {
//        loginService.socialLogin(code, registrationId);
//    }
//}

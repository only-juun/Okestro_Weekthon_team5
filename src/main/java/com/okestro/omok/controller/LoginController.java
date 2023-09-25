package com.okestro.omok.controller;

import com.okestro.omok.payload.response.UserDetailsResponse;
import com.okestro.omok.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login/oauth2")
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/code/{registrationId}")
    public ResponseEntity<UserDetailsResponse> googleLogin(
            @RequestParam String code, @PathVariable String registrationId) {
        return
                ResponseEntity.ok(loginService.socialLogin(code, registrationId));
    }
}

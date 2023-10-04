package com.okestro.omok.controller;

import com.okestro.omok.payload.response.UserTokenDetailsResponse;
import com.okestro.omok.service.LoginService;
import com.okestro.omok.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login/oauth2")
public class LoginController {

    private final LoginService loginService;

    /**
     * 구글 로그인
     */
    @GetMapping("/code/google")
    public ResponseEntity<UserTokenDetailsResponse> googleLogin(
            @RequestParam String code) {
        return ResponseEntity.
                ok(loginService.socialLogin(code));
    }
}

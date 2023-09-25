package com.okestro.omok.controller;

import com.okestro.omok.payload.request.CreateUserRequest;
import com.okestro.omok.payload.response.UserDetailsResponse;
import com.okestro.omok.service.LoginService;
import com.okestro.omok.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login/oauth2")
public class LoginController {

    private final LoginService loginService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserDetailsResponse> createUser(
            @RequestHeader("USER-ID") Long userId,
            @RequestBody @Valid CreateUserRequest createUserRequest) {

        return ResponseEntity
                .ok(userService.createUser(createUserRequest,userId));
    }

//    @GetMapping("/code/{registrationId}")
//    public ResponseEntity<UserDetailsResponse> googleLogin(
//            @RequestParam String code, @PathVariable String registrationId) {
//        return
//                ResponseEntity.ok(loginService.socialLogin(code, registrationId));
//    }
}

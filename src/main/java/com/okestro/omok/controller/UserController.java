package com.okestro.omok.controller;

import com.okestro.omok.payload.request.CreateUserRequest;
import com.okestro.omok.payload.response.UserDetailsResponse;
import com.okestro.omok.payload.response.UserDetailsResponse.UserNameResponse;
import com.okestro.omok.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    /**
     * 사용자 정보 조회
     */
    @GetMapping("/{userId}/name")
    public ResponseEntity<UserNameResponse> findUserName(
            @PathVariable(name = "userId") Long userId) {

        return ResponseEntity
                .ok(userService.findUserName(userId));
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<UserDetailsResponse> createUser(
            @RequestHeader("USER-ID") Long userId,
            @RequestBody @Valid CreateUserRequest createUserRequest) {

        return ResponseEntity
                .ok(userService.createUser(createUserRequest,userId));
    }

    /**
     * 방 참가
     */
    @PatchMapping("/{roomId}/participation")
    public ResponseEntity<Object> participationRoom(
            @RequestHeader("USER-ID") Long userId,
            @PathVariable("roomId") Long roomId) {

        userService.participationRoom(userId, roomId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    /**
     * 방 나가기
     */
    @PatchMapping("/{roomId}/exit")
    public ResponseEntity<Object> exitRoom(
            @RequestHeader("USER-ID") Long userId,
            @PathVariable("roomId") Long roomId) {
        userService.exitRoom(userId, roomId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
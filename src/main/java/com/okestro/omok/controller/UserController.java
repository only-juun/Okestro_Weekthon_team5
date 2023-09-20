package com.okestro.omok.controller;

import com.okestro.omok.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PatchMapping("/{roomId}/participation")
    public ResponseEntity<Object> participationRoom(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable("roomId") Long roomId) {

        userService.participationRoom(userId, roomId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
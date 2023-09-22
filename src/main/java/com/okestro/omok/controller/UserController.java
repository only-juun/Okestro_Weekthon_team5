package com.okestro.omok.controller;

import com.okestro.omok.payload.request.CreateUserRequest;
import com.okestro.omok.payload.response.UserDetailsResponse;
import com.okestro.omok.service.UserService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserDetailsResponse> createUser(
            @RequestHeader("X-USER-TOKEN") String userToken,
            @RequestBody @Valid CreateUserRequest createUserRequest) {

            return ResponseEntity
                    .ok(userService.createUser(createUserRequest,userToken));
    }

    @PatchMapping("/{roomId}/participation")
    public ResponseEntity<Object> participationRoom(
            @RequestHeader("X-USER-TOKEN") Long userId,
            @PathVariable("roomId") Long roomId) {

        userService.participationRoom(userId, roomId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
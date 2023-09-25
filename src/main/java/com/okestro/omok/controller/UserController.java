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


//    @GetMapping("/{userId}/name")
//        public ResponseEntity<UserNameResponse> findUserName(
//                @PathVariable(name = "userId") Long userId) {
//
//        return ResponseEntity
//                .ok(userService.findUserName(userId));
//    }
//
//    @PatchMapping("/{roomId}/participation")
//    public ResponseEntity<Object> participationRoom(
//            @RequestHeader("USER-ID") Long userId,
//            @PathVariable("roomId") Long roomId) {
//
//        userService.participationRoom(userId, roomId);
//
//        return ResponseEntity
//                .status(HttpStatus.NO_CONTENT)
//                .build();
//    }

    @PostMapping("/login")
    public ResponseEntity<UserDetailsResponse> createUser(
            @RequestHeader("USER-ID") Long userId,
            @RequestBody @Valid CreateUserRequest createUserRequest) {

        return ResponseEntity
                .ok(userService.createUser(createUserRequest,userId));
    }

    @PatchMapping("/{roomId}/participation")
    public ResponseEntity<Object> participationRoom(
            @RequestHeader("USER-ID") Long userId,
            @PathVariable("roomId") Long roomId) {

        userService.participationRoom(userId, roomId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
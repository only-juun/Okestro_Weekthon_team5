package com.okestro.omok.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    ALREADY_PARTICIPATION_ROOM(-1000,"이미 방에 참여 중입니다.", HttpStatus.BAD_REQUEST),
    EXCEED_PARTICIPATION_ROOM(-1001,"방 인원이 초과되었습니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_ROOM(-1002, "등록된 방이 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_USER(-1003, "등록된 사용자가 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_EMAIL(-1004, "해당 이메일은 사용하실 수 없습니다.", HttpStatus.FORBIDDEN),
    NO_PARTICIPATION_ROOM(-1005, "현재 참가중인 방이 아닙니다.", HttpStatus.BAD_REQUEST);

    ErrorCode(Integer code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;
}

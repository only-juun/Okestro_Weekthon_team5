package com.okestro.omok.util;

import com.okestro.omok.exception.ClientException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailValidationUtilTest {

    @Test
    @DisplayName("이메일 검증 성공 테스트")
    public void validationEmail_Success() {
        String validEmail = "km.kim@okestro.com";
        EmailValidationUtil.validationEmail(validEmail);
    }

    @Test
    @DisplayName("이메일 검증 okestro.com이 아닌 유저 예외 처리 테스트")
    public void validationEmail_OtherEmail() {
        String validEmail = "km.kim@Okestro.com";
        assertThrows(ClientException.class, () -> EmailValidationUtil.validationEmail(validEmail))
                .printStackTrace();
    }

    @Test
    @DisplayName("이메일 검증 잘못된 이메일 형식 예외 처리")
    public void validationEmail_Email() {
        String validEmail = "km.kim@okestro,com";
        assertThrows(ClientException.class, () -> EmailValidationUtil.validationEmail(validEmail))
                .printStackTrace();
    }

}
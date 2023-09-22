package com.okestro.omok.util;

import com.okestro.omok.exception.ClientException;
import com.okestro.omok.exception.ErrorCode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidationUtil {

    private static final String PATTERN = "@okestro.com";

    public static void validationEmail(String email) {
        Pattern regex = Pattern.compile(PATTERN);
        Matcher matcher = regex.matcher(email);

        if (!matcher.find() ||  ".".equals(email.substring(8,9))) {
            throw new ClientException(ErrorCode.INVALID_EMAIL);
        }
    }

}

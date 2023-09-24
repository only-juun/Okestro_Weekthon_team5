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

        String[] splitEmail = email.split("@");

        if (!matcher.find() || splitEmail.length != 2  || !".".equals(splitEmail[1].substring(7,8))) {
            throw new ClientException(ErrorCode.INVALID_EMAIL);
        }
    }

}

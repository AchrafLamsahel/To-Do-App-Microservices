package org.usermicroservice.utils;

import org.modelmapper.spi.ErrorMessage;
import org.usermicroservice.dto.UserRequestDto;
import org.usermicroservice.enumerations.MessagesError;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UserInputValidation {
    private static Boolean isNull(String field) {
        return field == null || field.trim().isEmpty();
    }

    private static Boolean isValidEmail(String email) {
        final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        var matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    public static List<ErrorMessage> validate(UserRequestDto userRequestDto) {
        var errors = new ArrayList<ErrorMessage>();

        if (isNull(userRequestDto.getFirstName())) {
            errors.add(new ErrorMessage(MessagesError.FIRSTNAME_IS_REQUIRED.getMessage()));
        }

        if (isNull(userRequestDto.getPassword())) {
            errors.add(new ErrorMessage(MessagesError.PASSWORD_IS_REQUIRED.getMessage()));
        }

        if (isNull(userRequestDto.getLastName())) {
            errors.add(new ErrorMessage(MessagesError.LASTNAME_IS_REQUIRED.getMessage()));
        }

        if (isNull(userRequestDto.getUserName())) {
            errors.add(new ErrorMessage(MessagesError.USERNAME_IS_REQUIRED.getMessage()));
        }

        if (isNull(userRequestDto.getEmail())) {
            errors.add(new ErrorMessage(MessagesError.EMAIL_IS_REQUIRED.getMessage()));
        } else if (!isValidEmail(userRequestDto.getEmail())) {
            errors.add(new ErrorMessage(MessagesError.EMAIL_IS_INVALID.getMessage()));
        }

        return errors;
    }
}

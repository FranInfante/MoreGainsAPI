package com.example.MoreGains.util.exceptions;

import com.example.MoreGains.util.messages.MessageConstants;

public class UserException extends Exception {
    public UserException(String s) {
        super(s);
    }

    public static UserException userNameExistingException() {
        return new UserException(MessageConstants.EXISTING_USER);
    }

    public static UserException emailExistingException() {
        return new UserException(MessageConstants.EXISTING_MAIL);
    }


    public static UserException failedUpdate() {
        return new UserException(MessageConstants.MODIFY_USER_ERROR);
    }
}

package com.gsheet.student.exception;

import com.gsheet.student.util.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class GoogleCredentialRefreshException extends RuntimeException {

    @Override
    public String getMessage() {
        return Messages.getMessageForLocale("google.auth.error.token-update");
    }
}

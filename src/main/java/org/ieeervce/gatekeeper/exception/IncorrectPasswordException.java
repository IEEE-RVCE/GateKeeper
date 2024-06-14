package org.ieeervce.gatekeeper.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectPasswordException extends Exception{
    public IncorrectPasswordException(String message)
    {
        super(message);
    }
}

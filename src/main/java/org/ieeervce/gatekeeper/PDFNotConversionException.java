package org.ieeervce.gatekeeper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PDFNotConversionException extends Exception{
    public PDFNotConversionException(String message){
        super(message);
    }
}





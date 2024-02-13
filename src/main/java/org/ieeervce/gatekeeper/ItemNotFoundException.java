package org.ieeervce.gatekeeper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception raised if Item is not found
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends Exception {
    /**
     * Create an ItemFoundException with a message to present
     * @param message Error message
     */
    public ItemNotFoundException(String message) {
        super(message);
    }
}

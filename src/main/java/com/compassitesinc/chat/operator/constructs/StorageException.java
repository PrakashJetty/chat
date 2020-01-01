package com.compassitesinc.chat.operator.constructs;

/**
 * Created by pavithra on 3/7/18.
 */
public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.compassitesinc.chat.operator.constructs;

/**
 * Created by pavithra on 3/7/18.
 */
public class StorageFileNotFoundException extends StorageException {

    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

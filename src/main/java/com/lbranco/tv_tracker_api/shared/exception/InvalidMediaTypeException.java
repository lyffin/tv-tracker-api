package com.lbranco.tv_tracker_api.shared.exception;

public class InvalidMediaTypeException extends RuntimeException {

    public InvalidMediaTypeException(String type) {
        super("Invalid media type: " + type + ". Supported values are movie, tv.");
    }
}

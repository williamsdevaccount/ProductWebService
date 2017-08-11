package io.swagger.api;

public class InvalidInputException extends ApiException {
    public InvalidInputException(int code, String msg) {
        super(code, msg);
    }
}

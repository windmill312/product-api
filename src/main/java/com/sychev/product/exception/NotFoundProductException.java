package com.sychev.product.exception;

public class NotFoundProductException extends RuntimeException {

    private static final long serialVersionUID = 4214090731521988527L;

    public NotFoundProductException() {
        super();
    }

    public NotFoundProductException(String message) {
        super(message);
    }
}

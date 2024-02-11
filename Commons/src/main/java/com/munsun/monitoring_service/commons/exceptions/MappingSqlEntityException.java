package com.munsun.monitoring_service.commons.exceptions;

public class MappingSqlEntityException extends RuntimeException{
    public MappingSqlEntityException() {
    }

    public MappingSqlEntityException(String message) {
        super(message);
    }

    public MappingSqlEntityException(Throwable cause) {
        super(cause);
    }
}

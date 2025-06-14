package org.realresourcesafrica.rsa.tradeverification.exception;

public class InquiryNotFoundException extends RuntimeException {

    public InquiryNotFoundException(String message) {
        super(message);
    }

    public InquiryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}


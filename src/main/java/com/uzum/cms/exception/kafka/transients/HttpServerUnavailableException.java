package com.uzum.cms.exception.kafka.transients;

public class HttpServerUnavailableException extends TransientException {
    public HttpServerUnavailableException(Exception ex) {
        super(ex);
    }
}

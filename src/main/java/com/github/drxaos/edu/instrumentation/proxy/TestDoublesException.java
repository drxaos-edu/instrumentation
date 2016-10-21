package com.github.drxaos.edu.instrumentation.proxy;

public class TestDoublesException extends RuntimeException {
    public TestDoublesException() {
    }

    public TestDoublesException(String message) {
        super(message);
    }

    public TestDoublesException(String message, Throwable cause) {
        super(message, cause);
    }

    public TestDoublesException(Throwable cause) {
        super(cause);
    }
}

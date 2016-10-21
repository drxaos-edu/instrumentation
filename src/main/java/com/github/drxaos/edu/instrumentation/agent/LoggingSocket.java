package com.github.drxaos.edu.instrumentation.agent;

import org.apache.commons.io.input.TeeInputStream;
import org.apache.commons.io.output.TeeOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class LoggingSocket extends Socket {

    public static Socket createSocket() {
        return new LoggingSocket();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return new TeeOutputStream(super.getOutputStream(), System.out);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new TeeInputStream(super.getInputStream(), System.out);
    }
}

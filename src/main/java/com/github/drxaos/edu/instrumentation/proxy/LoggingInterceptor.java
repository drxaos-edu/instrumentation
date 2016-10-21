package com.github.drxaos.edu.instrumentation.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.io.input.TeeInputStream;
import org.apache.commons.io.output.TeeOutputStream;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

public class LoggingInterceptor implements MethodInterceptor {
    public static Socket createSocket() {
        return (Socket) Enhancer.create(Socket.class, new LoggingInterceptor());
    }

    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        if (method.getName().equals("getOutputStream")) {
            OutputStream os = (OutputStream) methodProxy.invokeSuper(object, args);
            return new TeeOutputStream(os, System.out);
        } else if (method.getName().equals("getInputStream")) {
            InputStream is = (InputStream) methodProxy.invokeSuper(object, args);
            return new TeeInputStream(is, System.out);
        } else {
            return methodProxy.invokeSuper(object, args);
        }
    }
}

package com.github.drxaos.edu.instrumentation.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Wrapper implements MethodInterceptor {


    private TestDoublesControl control;
    private Object obj;

    public Wrapper(Object obj, TestDoublesControl control) {
        this.obj = obj;
        this.control = control;
    }

    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        if (control.isIgnored(method)) {
            return method.invoke(obj, args);
        }

        if (control.isPlanning()) {
            control.planCall(object, method, args, methodProxy);
            return null;
        } else {
            TestDoublesControl.Entry entry = control.checkCall(object, method, args, methodProxy);
            if (entry.overrideResult) {
                return entry.result;
            } else {
                return method.invoke(obj, args);
            }
        }
    }
}

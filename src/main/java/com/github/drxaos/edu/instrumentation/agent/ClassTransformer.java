package com.github.drxaos.edu.instrumentation.agent;

import javassist.ByteArrayClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.Descriptor;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ClassTransformer implements ClassFileTransformer {
    private ClassPool pool;

    public ClassTransformer() {
        pool = ClassPool.getDefault();
    }

    public byte[] transform(ClassLoader loader, String className,
                            Class classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            if (!className.equals("sun/net/NetworkClient")) {
                return classfileBuffer;
            }

            pool.insertClassPath(new ByteArrayClassPath(className, classfileBuffer));

            CtClass ctSocket = pool.get("java.net.Socket");
            CtClass ct = pool.get("sun.net.NetworkClient");
            CtMethod ctMethod = ct.getMethod("createSocket", Descriptor.ofMethod(ctSocket, new CtClass[0]));
            ctMethod.setBody("return java.lang.ClassLoader.getSystemClassLoader().loadClass(\"com.github.drxaos.edu.instrumentation.agent.LoggingSocket\").getDeclaredMethod(\"createSocket\", new Class[0]).invoke(null, new Object[0]);");

            return ct.toBytecode();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

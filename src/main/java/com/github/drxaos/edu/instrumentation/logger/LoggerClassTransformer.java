package com.github.drxaos.edu.instrumentation.logger;

import javassist.ByteArrayClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class LoggerClassTransformer implements ClassFileTransformer {
    private ClassPool pool;

    public LoggerClassTransformer() {
        pool = ClassPool.getDefault();
    }

    public byte[] transform(ClassLoader loader, String className,
                            Class classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            if (!className.startsWith("javax/xml/")) {
                return classfileBuffer;
            }

            pool.insertClassPath(new ByteArrayClassPath(className, classfileBuffer));

            CtClass ct = pool.get(className.replace("/", "."));
            for (CtMethod ctMethod : ct.getDeclaredMethods()) {
                if (!ctMethod.isEmpty()) {
                    ctMethod.insertBefore("System.out.println(\"\" + System.currentTimeMillis() + \": >\" + \"" + ct.getName() + "." + ctMethod.getName() + "();\");");
                    ctMethod.insertAfter("System.out.println(\"\" + System.currentTimeMillis() + \": ;\" + \"" + ct.getName() + "." + ctMethod.getName() + "();\");", true);
                }
            }

            return ct.toBytecode();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

package com.github.drxaos.edu.instrumentation.generator;

import me.qmx.jitescript.CodeBlock;
import me.qmx.jitescript.JiteClass;

import java.io.PrintStream;
import java.lang.reflect.Method;

import static me.qmx.jitescript.CodeBlock.newCodeBlock;
import static me.qmx.jitescript.util.CodegenUtils.*;

public class Jite {
    public static class DynamicClassLoader extends ClassLoader {
        public Class<?> define(JiteClass jiteClass) {
            byte[] classBytes = jiteClass.toBytes();
            System.out.println(classBytes.length);
            return super.defineClass(c(jiteClass.getClassName()), classBytes, 0, classBytes.length);
        }
    }

    public static void main(String[] args) throws Exception {
        final String className = "HelloTest";
        JiteClass jiteClass = new JiteClass(className) {
            {
                // you can use the pre-constructor style
                defineMethod("main", ACC_PUBLIC | ACC_STATIC, sig(void.class, String[].class), new CodeBlock() {
                    {
                        ldc("helloWorld");
                        getstatic(p(System.class), "out", ci(PrintStream.class));
                        swap();
                        invokevirtual(p(PrintStream.class), "println", sig(void.class, Object.class));
                        voidreturn();
                    }
                });

                // or use chained api
                defineMethod("hello", ACC_PUBLIC, sig(String.class), newCodeBlock().ldc("qwerty").areturn());

                defineDefaultConstructor();
            }
        };

        Class<?> clazz = new DynamicClassLoader().define(jiteClass);

        Method mainMethod = clazz.getMethod("main", String[].class);
        mainMethod.invoke(null, (Object) new String[]{});

        Object instance = clazz.newInstance();
        Method helloMethod = clazz.getMethod("hello");
        Object result = helloMethod.invoke(instance);
        System.out.println(result);
    }
}

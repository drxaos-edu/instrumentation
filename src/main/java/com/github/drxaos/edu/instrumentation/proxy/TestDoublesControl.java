package com.github.drxaos.edu.instrumentation.proxy;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.Factory;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.List;

public class TestDoublesControl {

    class Entry {
        Object o;
        String methodName;
        Object[] args;
        boolean overrideResult;
        Object result;

        public Entry(Object o, String methodName, Object[] args) {
            this.o = o;
            this.methodName = methodName;
            this.args = args;
        }

        @Override
        public String toString() {
            return names.get(o) + "." + methodName + "(" + Arrays.toString(args) + ")";
        }

        public void setOverrideResult(Object overrideResult) {
            this.overrideResult = true;
            this.result = overrideResult;
        }
    }

    private boolean planning;

    private List<String> ignores = new ArrayList<String>();
    private ArrayList<Entry> plan;
    private IdentityHashMap<Object, String> names = new IdentityHashMap<Object, String>();

    public TestDoublesControl() {
        ignores.add("toString");
        ignores.add("equals");
        ignores.add("hashCode");
        ignores.add("compare");
        ignores.add("compareTo");
        ignores.add("wait");
        ignores.add("finalize");
        ignores.add("<init>");
        ignores.add("<clinit>");
    }

    public static <T> void copyAllFields(T to, T from) {
        Class<T> clazz = (Class<T>) from.getClass();
        Field[] fields = clazz.getFields();

        if (fields != null) {
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    field.set(to, field.get(from));
                } catch (IllegalAccessException e) {
                    // ignore
                }
            }
        }
    }

    public <T> T wrap(String name, T obj) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(obj.getClass());
        Wrapper interceptor = new Wrapper(obj, this);
        enhancer.setCallbackType(interceptor.getClass());
        Class aClass = enhancer.createClass();

        Object o = SilentObjectCreator.create(aClass);
        copyAllFields(o, obj);
        ((Factory) o).setCallbacks(new Callback[]{interceptor});
        names.put(o, name);
        return (T) o;
    }

    public void plan() {
        planning = true;
        plan = new ArrayList<Entry>();
    }

    public void start() {
        planning = false;
    }

    public void end() {
        if (plan.size() > 0) {
            throw new TestDoublesException("not called: " + plan.toString());
        }
    }

    public boolean isPlanning() {
        return planning;
    }

    public boolean isIgnored(Method method) {
        return ignores.contains(method.getName());
    }

    public void planCall(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        plan.add(new Entry(object, method.getName(), args));
    }

    public Entry checkCall(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Entry expected = plan.remove(0);
        if (expected.o != object) {
            throw new TestDoublesException("wrong object: " + object);
        }
        if (!expected.methodName.equals(method.getName())) {
            throw new TestDoublesException("wrong method: " + method.getName());
        }
        // TODO check args
        return expected;
    }

    public void overrideResult(Object result) {
        plan.get(plan.size() - 1).setOverrideResult(result);
    }

}

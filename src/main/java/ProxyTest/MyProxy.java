package ProxyTest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyProxy implements InvocationHandler {

    private Class<?> type;

    public MyProxy(Class<?> type) {
        this.type = type;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Singeltest singeltest = new Singeltest(type);
        singeltest.say();
        return "";
    }

    public static <T> T newcreateProxy(Class<T> tClass) {
        ClassLoader classLoader = tClass.getClassLoader();
        MyProxy myProxy = new MyProxy(tClass);
        Class<?>[] in = new Class[]{tClass};
        return (T) Proxy.newProxyInstance(classLoader, in, myProxy);
    }
}

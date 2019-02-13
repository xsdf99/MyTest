package RemoteProxy.Client;

import java.lang.reflect.Proxy;

public class BeanFactory {

    public static <T> T createBean(Class<T> classType) {
        ClientProxy clientProxy = new ClientProxy(classType);
        return (T) Proxy.newProxyInstance(classType.getClassLoader(), new Class[]{classType}, clientProxy);
    }
}

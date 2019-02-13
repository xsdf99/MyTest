package SpringTest.MyProxy;

import SpringTest.Utils.AspectUtil;
import SpringTest.Utils.MyApplicationContext;
import javafx.util.Pair;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class MyHandle implements MethodInterceptor {

    public Object getBean(Class<?> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Map<String, Pair<List<String>, List<String>>> aliasPair = new LinkedHashMap<>();
        AspectUtil.aspectMap.forEach((alias, map) -> {
            Pair<List<String>, List<String>> pair = map.get(method.getName());
            if (pair != null) {
                aliasPair.put(alias, pair);
            }
        });
        //前置方法
        aliasPair.forEach((alias, pair) -> startMethod(alias, pair.getKey()));

        methodProxy.invokeSuper(o, objects);

        //后置方法
        aliasPair.forEach((alias, pair) -> startMethod(alias, pair.getValue()));

        return null;
    }

    private void startMethod(String alias, List<String> methodNameList) {
        Object object = MyApplicationContext.getBean(alias);
        Class<?> clazz = object.getClass();
        methodNameList.forEach(methodName -> {
            for (Method beforeMethod : clazz.getDeclaredMethods()) {
                if (methodName.equals(beforeMethod.getName())) {
                    try {
                        beforeMethod.invoke(object);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        });
    }
}

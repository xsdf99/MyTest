package SpringTest.Utils;

import SpringTest.MyAnnotation.*;
import SpringTest.MyProxy.MyHandle;
import javafx.util.Pair;
import org.springframework.util.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class MyApplicationContext {

    /**
     * key:类的别名  value:实例
     */
    private static Map<String, Object> objectMap = Collections.synchronizedMap(new HashMap<>());
    private static Map<String, Class<?>> clazzMap = Collections.synchronizedMap(new HashMap<>());

    public MyApplicationContext() {
        //获取需要扫描的包的路径
        XmlParseUtil xmlParseUtil = new XmlParseUtil();
        //获取所有的添加注解的类
        scanPackage(xmlParseUtil.getScanPackageName());
        //初始化切面的方法
        initAspect();
        //生成代理
        initProxy();
        //自动注入
        automaticInjection();
    }

    private void initAspect() {
        clazzMap.forEach((alias, clazz) -> {
            if (clazz.isAnnotationPresent(MyAspect.class)) {
                Map<String, String> pointcutMap = new HashMap<>();
                Map<String, Pair<List<String>, List<String>>> methodMap = new HashMap<>();
                Map<String, Pair<List<String>, List<String>>> pointMap;
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(MyPointcut.class)) {
                        MyPointcut myPointcut = method.getAnnotation(MyPointcut.class);
                        pointcutMap.put(myPointcut.methodName(), method.getName());
                        continue;
                    }
                    if (method.isAnnotationPresent(MyBefore.class)) {
                        MyBefore myPointcut = method.getAnnotation(MyBefore.class);
                        if (methodMap.get(myPointcut.methodName()) == null) {
                            methodMap.put(myPointcut.methodName(), new Pair<>(new ArrayList<>(Collections.singletonList(method.getName())), new ArrayList<>()));
                        } else {
                            methodMap.get(myPointcut.methodName()).getKey().add(method.getName());
                        }
                        continue;
                    }
                    if (method.isAnnotationPresent(MyAfter.class)) {
                        MyAfter myPointcut = method.getAnnotation(MyAfter.class);
                        if (methodMap.get(myPointcut.methodName()) == null) {
                            methodMap.put(myPointcut.methodName(), new Pair<>(new ArrayList<>(), new ArrayList<>(Collections.singletonList(method.getName()))));
                        } else {
                            methodMap.get(myPointcut.methodName()).getValue().add(method.getName());
                        }
                    }
                }
                if (pointcutMap.size() > 0) {
                    pointMap = new HashMap<>();
                    pointcutMap.forEach((key, value) -> pointMap.put(key, methodMap.get(value)));
                    AspectUtil.aspectMap.put(alias, pointMap);
                }
            }
        });

    }

    private void initProxy() {
        //使用代理生成对象
        MyHandle myHandle = new MyHandle();
        out:
        for (Map.Entry<String, Class<?>> outEntry : clazzMap.entrySet()) {
            String alias = outEntry.getKey();
            Class<?> clazz = outEntry.getValue();
            if (!AspectUtil.aspectMap.containsKey(alias)) {
                for (Map.Entry<String, Map<String, Pair<List<String>, List<String>>>> entry : AspectUtil.aspectMap.entrySet()) {
                    for (Method method : clazz.getDeclaredMethods()) {
                        if (entry.getValue().containsKey(method.getName())) {
                            //启动代理
                            objectMap.put(alias, myHandle.getBean(clazz));
                            continue out;
                        }
                    }
                }
            }
            try {
                objectMap.put(alias, clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void automaticInjection() {
        clazzMap.forEach((alias, clazz) -> {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(MyAutoWired.class)) {
                    MyAutoWired myAutoWired = field.getAnnotation(MyAutoWired.class);
                    String fieldAlias = StringUtils.isEmpty(myAutoWired.name()) ? field.getName() : myAutoWired.name();
                    Object fieldObj = objectMap.get(fieldAlias);
                    field.setAccessible(true);
                    try {
                        field.set(objectMap.get(alias), fieldObj);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 根据实例获取别名
     *
     * @return 别名
     */
    private String getAlias(Class<?> clazz) {
        String alias = "";
        //MyController
        MyController myController = clazz.getAnnotation(MyController.class);
        if (myController != null) {
            alias = myController.name();
            return StringUtils.isEmpty(alias) ? getLowerAlisa(clazz.getSimpleName()) : alias;
        }
        //MyService
        MyService myService = clazz.getAnnotation(MyService.class);
        if (myService != null) {
            alias = myService.name();
            return StringUtils.isEmpty(alias) ? getLowerAlisa(clazz.getSimpleName()) : alias;
        }

        return alias;
    }

    private String getLowerAlisa(String name) {
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    private void scanPackage(String packageName) {
        URL url = this.getClass().getClassLoader().getResource(packageName.replaceAll("\\.", "/"));
        try {
            assert url != null;
            File file = new File(url.toURI());
            file.listFiles(pathname -> {
                String path = packageName + "." + pathname.getName();
                if (pathname.isDirectory()) {
                    scanPackage(path);
                } else {
                    String classPath = path.replaceAll("\\.class", "");
                    try {
                        Class<?> clazz = this.getClass().getClassLoader().loadClass(classPath);
                        if (clazz.isAnnotationPresent(MyController.class) || clazz.isAnnotationPresent(MyService.class)) {
                            String alias = getAlias(clazz);
                            clazzMap.put(alias, clazz);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static Object getBean(String aliasName) {
        return objectMap.get(aliasName);
    }

    public static void main(String[] args) {
        MyApplicationContext myApplicationContext = new MyApplicationContext();
        myApplicationContext.scanPackage("SpringTest");
        System.out.println(myApplicationContext.getLowerAlisa("DFDFDF"));
    }
}


package MyTest3;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestU {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<TestU> clazz = TestU.class;
        Method method = clazz.getDeclaredMethod("test1", String.class);
        method.invoke(clazz, "1");
    }

    private static void test1(String a) {
        System.out.println("1");
    }

    private static void test2(String a) {
        System.out.println("2");
    }
}

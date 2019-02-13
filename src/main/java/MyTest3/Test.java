package MyTest3;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Test {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
//        Map<String, String> map = new HashMap<>();
//        map.put("1", "1");
//        map.put("2", "2");
//        map.put("3", "3");
//        map.put("4", "4");
//
//        Set<String> key = map.keySet();
//
//        key.removeIf(s -> Integer.parseInt(s) % 2 == 0);
//        Set<String> idSet = new LinkedHashSet<>();
//        idSet.add("10510");
//        idSet.add("10513");
//        idSet.add("10504");
//        idSet.add("10512");
//        idSet.add("10515");
//        idSet.add("10506");
//        idSet.add("10519");
//        idSet.add("10503");
//        List<String> list = new ArrayList<>(idSet);
//
//        System.out.println(list);

//        Map<Integer, String> map = new HashMap<>();
//        map.put(1, "1");
//        map.put(2, "2");
//        map.forEach((k, v) -> map.put(k, ""));
//        System.out.println(map);
//        map.computeIfPresent(2, (a, b) -> (a - b));
//        if (map.get(2) > 1)
//            System.out.println(map);new Random().nextInt(2);
//        elsenew Random().nextInt(2);
//            System.out.println(map);
//        String a = "qwertyu";
//        System.out.println(a.contains("erty"));
//        for (int i = 0; i < 10; i++)
//            System.out.println(new Random().nextInt(3) + 1);

//        List<Integer> a = new ArrayList<>();
//
//        a.add(5);
//        a.add(5);
//        a.add(5);
//        a.clear();
//        System.out.println(a);
        System.out.println(60 % 60 == 0 ? 0 : 1);
        Class<?> tClass = Test.class;
        Test a = (Test) tClass.getConstructor().newInstance();
        Object[] o = {"1","2"};
        System.out.println(o);
    }
}

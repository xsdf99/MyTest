package Mytest2;

import java.util.*;

public class Test21 {
    public static void main(String[] args) {
//        List<Integer> a = new ArrayList<>();
//        a.add(1);
//        a.add(2);
//        a.add(3);
//        List<Integer> b = new ArrayList<>(a.size());
//        a.forEach(c -> b.add(c));
//        System.out.println(a);
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(100,1);
        map.put(200,2);
        map.put(400,3);
        getTypeByValueWithDifferentClass1(map,203);
//        List<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        list.add(4);
//        list.add(5);
//
//        List<Integer> list1 = new ArrayList<>();
//        list1.add(1);
//        list1.add(2);
//        list1.add(3);
//        list.forEach(id -> {
//            Iterator<Integer> it = list1.iterator();
//            while (it.hasNext()) {
//                if (id == it.next()) {
//                    it.remove();
//                    break;
//                }
//            }
//            System.out.println(new Random().nextInt(60));
//        });

//        LinkedHashSet<String> set = new LinkedHashSet<>();
//        set.add("1");
//        set.add("2");
//        set.add("3");
//        set.add("4");
//        set.add("5");
//        set.add("6");
//        set.add("7");
//        set.forEach(System.out::println);
//        Map<Integer, Integer> map = new HashMap<>();
//        int count = 1;
//        map.put(++count, 1);
//        System.out.println(new Random().nextInt(60));

    }


    private static Map map;

    static {
        System.out.println("1");
        map = new HashMap();
        System.out.println("1");
    }

    public static Integer getTypeByValueWithDifferentClass(TreeMap<Integer, Integer> map, int value) {
        Integer type = map.get(value);
        if (type == null) {
            Integer key = map.lowerKey(value);
            if (key != null) {
                type = map.get(key);
            }
        }
        int a = map.lastEntry().getValue();
        return type;
    }


    /**
     * 获取某一阶段的id
     */
    public static Integer getTypeByValueWithDifferentClass1(TreeMap<Integer, Integer> map, int value) {
        Integer type = map.get(value);
        if (type == null) {
            Integer key = map.higherKey(value);
            if (key != null) {
                type = map.get(key);
            }
        }
        return type;
    }
}

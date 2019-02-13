package Mytest2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TestC {
    public static void main(String[] args) {
//        TestB testB = new TestB();
//        testB.test(new TestA(), new CallB() {
//            @Override
//            public void onResult() {
//                System.out.println("c");
//            }
//        });
//        int[] a = {1, 1};
//        aa(a);
//        System.out.println(a);
        List<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(1);
        a.add(1);
        a.add(1);
        List<Integer> b = new ArrayList<>();
        b.add(1);
        b.add(1);
        b.add(1);
        b.add(1);
        System.out.println(a);
        out:
        for (int i = 0; i < a.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                if (b.get(j) == 1) {
                    b.remove(j);
                    continue out;
                }
            }
        }
        List<Integer> c = new LinkedList<>();

        c.add(0);
        c.add(1);
        c.add(2);
        c.add(3);
        c.add(4);
        c.add(5);
        List<Integer> d = c.subList(1, 4);


        int fff = 7;
        fff = (int) (fff * 0.8);
        System.out.println(66);

        vvv(c);
        if(2+2*5<10){
            System.out.println("m");
        }
    }

    private static void aa(int[] a) {

        a[0]++;
    }

    private static void vvv(List aaa) {
        int c = (int) aaa.get(1);
    }

}

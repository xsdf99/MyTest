package Mytest1;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public abstract class Mytest11 extends MyTest2 {
    public static void main(String[] args) {
        long time = System.currentTimeMillis();


        Integer[] numbs = {1, 2, 3, 4, 5};
        Integer[] probs = numbs.clone();
        probs[1] = 5;

        int c = 10;
        a(10);

        System.out.println(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneOffset.ofHours(8)));


    }

    private static void a(int c) {
        c--;
    }

}

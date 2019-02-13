package Mytest1;

import java.util.Random;

public class Mytest3 {

    public static void main(String[] args) {

        Integer[] a = {100, 100, 200, 200, 300, 300, 400, 400};
        Integer[] b  = randomRoundTable(a, 2000, 6);
        for(int i=0;i<100;i++){
            System.out.println(new Random().nextInt(3)+1);
        }
    }

    public static Integer[] randomRoundTable(Integer[] probs, int total, int num) {
        Integer[] numbs = new Integer[num];
        for (int x = 0; x < num; x++) {
            int r = new Random().nextInt(total);
            int p = 0;
            for (int i = 0; i < probs.length; ++i) {
                p += probs[i];
                if (p >= r) {
                    numbs[x] = i;
                    total -= probs[i];
                    probs[i] = 0;
                    break;
                }
            }
        }
        return numbs;
    }


}

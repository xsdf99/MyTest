package Mytest2;

public class TestA {

    public void sys(String a, CallB callB) {
        System.out.println(a);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                callB.onResult();
            }
        }).start();

        System.out.println("结束");
    }
}

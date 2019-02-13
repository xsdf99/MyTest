package CallBack;

public class TestB {

    public void sys(String a ,Call call){
        System.out.println(a);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                call.result(56);
            }
        }).start();
    }
}

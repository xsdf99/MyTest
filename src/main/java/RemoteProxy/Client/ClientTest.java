package RemoteProxy.Client;

import RemoteProxy.DataInterfase;

public class ClientTest {
    public static void main(String[] args) throws InterruptedException {
        DataInterfase dataInterfase = BeanFactory.createBean(DataInterfase.class);
        dataInterfase.getData("丁峰", System.out::println);
//        Thread.sleep(1000);
        dataInterfase.getData("丁峰21312", System.out::println);
    }


}

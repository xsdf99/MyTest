package ProxyTest;

public class Test {

    public static void main(String[] args) {
        TestInterface testInterface = MyProxy.newcreateProxy(TestInterface.class);
        testInterface.say();
    }
}

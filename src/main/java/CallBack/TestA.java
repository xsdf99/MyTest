package CallBack;

public class TestA {
    public static void main(String[] args) {
        TestB b = new TestB();
        b.sys("a", new Call() {
            @Override
            public void result(Object res) {
                System.out.println(res);
            }
        });
    }
}

package CglibTest;

public class Test {
    public static void main(String[] args) {
        MyHandle myHandle = new MyHandle();
        My1 my1 = (My1) myHandle.getBean(My1.class);
        my1.sy("1");
    }
}

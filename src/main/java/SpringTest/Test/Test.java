package SpringTest.Test;

import SpringTest.Utils.MyApplicationContext;

public class Test {
    public static void main(String[] args) {
        MyApplicationContext myApplicationContext = new MyApplicationContext();
        MyTestController myTestController = (MyTestController) myApplicationContext.getBean("myTestController");
        myTestController.print1();
    }
}

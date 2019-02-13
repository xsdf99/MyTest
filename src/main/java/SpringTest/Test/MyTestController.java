package SpringTest.Test;

import SpringTest.MyAnnotation.MyAutoWired;
import SpringTest.MyAnnotation.MyController;

@MyController
public class MyTestController {

    @MyAutoWired
    private MyTestService myTestService;

    public void print1() {
        myTestService.print("哈哈哈");
    }
}

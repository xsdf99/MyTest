package SpringTest.Test;

import SpringTest.MyAnnotation.*;

@MyController
@MyAspect
public class MyTestAspect {

    @MyPointcut(methodName = "print")
    public void point() {

    }

    @MyBefore(methodName = "point")
    public void bef() {
        System.out.println("前置1");
    }


    @MyBefore(methodName = "point")
    public void bef1() {
        System.out.println("前置2");
    }

    @MyAfter(methodName = "point")
    public void aft() {
        System.out.println("后置1");
    }
}

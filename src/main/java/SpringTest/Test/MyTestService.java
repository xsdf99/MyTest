package SpringTest.Test;

import SpringTest.MyAnnotation.MyService;

@MyService
public class MyTestService {

    public void print(String value) {
        System.out.println(value);
    }
}

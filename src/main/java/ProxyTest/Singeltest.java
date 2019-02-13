package ProxyTest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Singeltest {

    private Class<?> type;

    public Singeltest(Class<?> type) {
        this.type = type;
    }


    public void say() {
        System.out.println("说话");
    }
}

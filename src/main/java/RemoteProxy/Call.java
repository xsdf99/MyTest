package RemoteProxy;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Call implements Serializable {

    private String className;
    private String methodName;
    private Class<?>[] paramTypes;
    private Object[] params;
    private Object result;
    private String sign;

    public Call(String className, String methodName, Class<?>[] paramTypes, Object[] params) {
        this.className = className;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.params = params;
    }

}

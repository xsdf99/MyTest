package RemoteProxy.Client;

import RemoteProxy.Call;
import RemoteProxy.CallBack;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Getter
@Setter
public class ClientProxy implements InvocationHandler {

    private Class<?> classType;

    private static NioClient nioClient;

    public ClientProxy(Class<?> classType) {
        this.classType = classType;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (nioClient == null) {
            nioClient = new NioClient();
            new Thread(nioClient).start();
        }
        Call call = new Call(classType.getName(), method.getName(), new Class[]{method.getParameterTypes()[0]}, new Object[]{args[0]});
        nioClient.sendMsg(call, (CallBack) args[1]);
        return null;
    }


}

package RemoteProxy.Server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ServerTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        new Thread(new NioServer()).start();
    }


}

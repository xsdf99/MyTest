package RemoteProxy.Server;

import RemoteProxy.CallBack;
import RemoteProxy.DataInterfase;

public class DataServer implements DataInterfase {
    @Override
    public String getData(String name, CallBack callBack) {
        return null;
    }

    @Override
    public String getData(String name) {
        return name + "666";
    }
}

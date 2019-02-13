package RemoteProxy;

import java.io.Serializable;

public interface CallBack extends Serializable {
    void result(Object res);
}

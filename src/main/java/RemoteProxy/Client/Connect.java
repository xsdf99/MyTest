package RemoteProxy.Client;

import RemoteProxy.Call;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.Socket;

@Getter
@Setter
public class Connect {

    private InputStream is;
    private OutputStream os;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Socket socket;

    public void Connect() throws IOException {
        socket = new Socket("127.0.0.1", 5055);
    }

    public void sendMsg(Call call) throws IOException {
        os = socket.getOutputStream();
        outputStream = new ObjectOutputStream(os);
        outputStream.writeObject(call);
    }

    public Object receive() throws IOException, ClassNotFoundException {
        is = socket.getInputStream();
        inputStream = new ObjectInputStream(is);
        return inputStream.readObject();
    }

    public void close() throws IOException {
        inputStream.close();
        outputStream.close();
        is.close();
        os.close();
        socket.close();
    }
}

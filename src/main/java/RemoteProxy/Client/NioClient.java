package RemoteProxy.Client;

import RemoteProxy.Call;
import RemoteProxy.CallBack;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Getter
@Setter
public class NioClient implements Runnable {

    private static ConcurrentMap<String, CallBack> concurrentMap;

    private volatile SocketChannel socketChannel;
    private Selector selector;

    public NioClient() {
        try {
            if (concurrentMap == null) {
                concurrentMap = new ConcurrentHashMap<>();
            }
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            selector = Selector.open();
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 7654));
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            while (true) {
                selector.select();
                Iterator<SelectionKey> its = selector.selectedKeys().iterator();
                while (its.hasNext()) {
                    SelectionKey key = its.next();
                    its.remove();
                    handle(key, selector);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void handle(SelectionKey key, Selector selector) {
        if (key.isValid()) {
            if (key.isConnectable()) {
                SocketChannel socketChannel = (SocketChannel) key.channel();
                try {
                    if (socketChannel.finishConnect()) {
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (key.isReadable()) {
                try {
                    receive(socketChannel);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized void sendMsg(Call call, CallBack callBack) {
        String id = UUID.randomUUID().toString();
        call.setSign(id);
        concurrentMap.put(id, callBack);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(byteStream);
            os.writeObject(call);
            os.flush();
            ByteBuffer byteBuffer = ByteBuffer.wrap(byteStream.toByteArray());
            socketChannel.write(byteBuffer);
            System.out.println(System.currentTimeMillis());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null)
                    os.close();
                byteStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void receive(SocketChannel socketChannel) throws ClassNotFoundException, IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        if (socketChannel.read(byteBuffer) > 0) {
            ByteArrayInputStream byteStream = new ByteArrayInputStream(byteBuffer.array());
            ObjectInputStream is = new ObjectInputStream(byteStream);
            Call call = (Call) is.readObject();
            CallBack callBack = concurrentMap.remove(call.getSign());
            if (callBack != null) {
                callBack.result(call.getResult());
            }
        }
    }

}

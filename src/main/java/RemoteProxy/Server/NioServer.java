package RemoteProxy.Server;

import RemoteProxy.Call;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioServer implements Runnable {

    private Selector selector;

    public NioServer() {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            selector = Selector.open();
            serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1", 7654));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while (true) {
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Iterator<SelectionKey> its = selector.selectedKeys().iterator();
            while (its.hasNext()) {
                SelectionKey key = its.next();
                its.remove();
                handle(key, selector);
            }
        }
    }

    private void handle(SelectionKey key, Selector selector) {
        if (key.isValid()) {
            if (key.isAcceptable()) {
                try {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (key.isReadable()) {
                System.out.println(System.currentTimeMillis());
                SocketChannel socketChannel = (SocketChannel) key.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                try {
                    if (socketChannel.read(byteBuffer) > 0) {
                        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteBuffer.array());
                        ObjectInputStream is = new ObjectInputStream(inputStream);
                        Call call = (Call) is.readObject();
                        getResult(call);
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        ObjectOutputStream os = new ObjectOutputStream(outputStream);
                        os.writeObject(call);
                        socketChannel.write(ByteBuffer.wrap(outputStream.toByteArray()));
                    }
                } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void getResult(Call call) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> classType = Class.forName(call.getClassName());
        Method method = classType.getMethod(call.getMethodName(), call.getParamTypes());
        call.setResult(method.invoke(new DataServer(), call.getParams()));
        System.out.println("处理一次");
    }
}

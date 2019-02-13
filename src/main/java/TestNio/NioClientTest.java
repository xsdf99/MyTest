package TestNio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;


public class NioClientTest {

    private volatile SocketChannel socketChannel;
    private volatile Selector selector;

    private static volatile int cc = 0;

    public static void main(String[] args) {
        NioClientTest test = new NioClientTest();

//        try {
//            test.socketChannel.register(test.selector, SelectionKey.OP_WRITE);
//        } catch (ClosedChannelException e) {
//            e.printStackTrace();
//        }
//        for (int i = 0; i < 1; i++) {
//            new Thread(test).start();
////            try {
////                Thread.sleep(100);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
//        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                        test.write(test.socketChannel);
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        test.test(1);
    }

//    @Override
//    public void run() {
//        cc = cc + 1;
//        test(cc);
//    }

    private void test(int count) {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            boolean connect = socketChannel.connect(new InetSocketAddress("127.0.0.1", 9095));
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();
                    handle(key, selector, count);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(SocketChannel socketChannel) throws IOException {
//        socketChannel.register(selector, SelectionKey.OP_READ);
        byte[] bytes = ("客户端").getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(bytes);
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
    }

    private void handle(SelectionKey key, Selector selector, int count) throws IOException {
        if (key.isValid()) {
            if (key.isConnectable()) {
                SocketChannel socketChannel = (SocketChannel) key.channel();
                if (socketChannel.finishConnect()) {
                    socketChannel.register(selector, SelectionKey.OP_READ);
//                    write(socketChannel);
                }
            }

            if (key.isReadable()) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                SocketChannel socketChannel = (SocketChannel) key.channel();
                if (socketChannel.read(byteBuffer) > 0) {
                    byteBuffer.flip();
                    byte[] bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes);
                    String msg = new String(bytes, Charset.defaultCharset());
                    System.out.println("client receive:" + msg);
//                    bytes = ("客户端呵呵" + count).getBytes();
//                    byteBuffer = ByteBuffer.allocate(1024);
//                    byteBuffer.put(bytes);
//                    byteBuffer.flip();
//                    socketChannel.write(byteBuffer);
                }
            }

            if (key.isWritable()) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                SocketChannel socketChannel = (SocketChannel) key.channel();
                byte[] bytes = ("客户端消息").getBytes();
                byteBuffer.put(bytes);
                byteBuffer.flip();
                socketChannel.write(byteBuffer);
            }
        }
    }


}

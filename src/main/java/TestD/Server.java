package TestD;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {

    private boolean running;

    private Selector selector;
    String writeMsg = "test";
    private final int TimeOut = 3000;
    SelectionKey ssckey;
    ExecutorService exec;

    public Server() {

        running = true;
        /**启动线程池*/
        exec = Executors.newCachedThreadPool();
    }

    public void init() {
        try {
            /*创建一个选择器*/
            selector = Selector.open();
            /*打开监听信道**/
            ServerSocketChannel ssc = ServerSocketChannel.open();
            /*设定非阻塞状态*/
            ssc.configureBlocking(false);
            /*绑定端口*/
            ssc.socket().bind(new InetSocketAddress(2345));
            // 将选择器绑定到监听信道,只有非阻塞信道才可以注册选择器.并在注册过程中指出该信道可以进行Accept操作
            ssckey = ssc.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("server is starting..." + new Date());

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        new Thread(server).start();

    }

    public void execute() {

        try {
            while (running) {
                // 等待某信道就绪(或超时)
                if (selector.select(TimeOut) == 0) {
                    System.out.print("等待...");
                    continue;
                }
                if (selector.select() > 0) {
                    System.out.println("running selector.selector() 方法...");
                }
                // 取得迭代器.selectedKeys()中包含了每个准备好某一I/O操作的信道的SelectionKey
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();

                    it.remove();
                    if (!key.isValid()) continue;
                    // 有客户端连接请求时
                    if (key.isAcceptable()) {

                        getConn(key);

                    }
                    // 从客户端读取数据
                    else if (key.isReadable()) {

                        readMsg(key);
                    } else if (key.isValid() && key.isWritable()) {
                        if (writeMsg != null) {

                            writeMsg(key);
                        }
                    } else
                        break;
                }

                Thread.yield();
            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getConn(SelectionKey key) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel sc = ssc.accept();
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_READ);
        //sc.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);
        System.out.println("build connection :" + sc.socket().getRemoteSocketAddress());

    }

    private void readMsg(SelectionKey key) throws IOException {
        StringBuffer sb = new StringBuffer();

        SocketChannel sc = (SocketChannel) key.channel();
        System.out.print(sc.socket().getRemoteSocketAddress() + " ");

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.clear();
        int len = 0;

        if (sc.read(buffer) > 0) {
            ByteArrayInputStream input = new ByteArrayInputStream(buffer.array());
            ObjectInputStream objInput = new ObjectInputStream(input);
            try {
                User user = (User) objInput.readObject();
                System.out.println(user.getId() + "..........");
            } catch (ClassNotFoundException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        /*写入对象*/
        MyWriterObject o = new MyWriterObject("", sc);
        exec.execute(o);




            /*while((len=sc.read(buffer))>0){
                 buffer.flip();
                 sb.append(new String(buffer.array(),0,len));
            }
            if(sb.length()>0) System.out.println("get from client:"+sb.toString());
            */
          /*  if(sb.toString().trim().toLowerCase().equals("quit")){
                sc.write(ByteBuffer.wrap("BYE".getBytes()));

                key.cancel();
                sc.close();
                sc.socket().close();

            }
            else{

                String toMsg=sc.socket().getRemoteSocketAddress()+ "said:"+sb.toString();
                System.out.println(toMsg);
                */
                /*Iterator<SelectionKey> it=key.selector().keys().iterator();
                while(it.hasNext()){
                    SelectionKey skey=it.next();
                    if(skey!=key&&skey!=ssckey){
                        MyWriter myWriter=new MyWriter(skey,"");
                        exec.execute(myWriter);
                    }else{
                    //User user = new User();
                    //user.setId("001");
                    //key.attach(user);
                    服务器返回客户端对象形势
                    MyWriterObject o = new MyWriterObject(skey,"");
                    exec.execute(o);

                    }


                     else{
                    key.attach(writeMsg);
                        key.interestOps(SelectionKey.OP_READ|SelectionKey.OP_WRITE);
                    }


               // }
                //key.interestOps(SelectionKey.OP_READ);
            }*/

    }

    public void run() {
        init();
        execute();
    }

    private void writeMsg(SelectionKey key) throws IOException {

        System.out.println("++++enter write+++");
        SocketChannel sc = (SocketChannel) key.channel();
        String str = (String) key.attachment();

        sc.write(ByteBuffer.wrap(str.getBytes()));
        key.interestOps(SelectionKey.OP_READ);
    }
}


class MyWriter implements Runnable {
    SelectionKey key;
    String msg;

    public MyWriter(SelectionKey key, String msg) {
        this.key = key;
        this.msg = msg;
    }

    public void run() {
        try {
            SocketChannel client = (SocketChannel) key.channel();

            if (client == null || !client.isConnected() || client.isConnectionPending()) {
                System.out.println("00000000000000000000000000000000000");
            }
            client.write(ByteBuffer.wrap(msg.getBytes()));
            Thread.yield();
        } catch (IOException ex) {
            Logger.getLogger(MyWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class MyWriterObject implements Runnable {
    SelectionKey key;
    String msg;
    SocketChannel client;

    public MyWriterObject(String msg, SocketChannel client) {
        this.client = client;
        this.msg = msg;
    }

    public void run() {
        try {
            // SocketChannel client = (SocketChannel) key.channel();

            User user = new User();
            user.setId("001");

            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bOut);
            out.writeObject(user);
            out.flush();

            client.write(ByteBuffer.wrap(bOut.toByteArray()));

            Thread.yield();
        } catch (IOException ex) {
            Logger.getLogger(MyWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
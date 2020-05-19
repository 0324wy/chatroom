package biooneclient;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        final String QUIT = "quit";
        final int DEFAULT_PORT = 8888;
        ServerSocket serverSocket = null;

        try {
            //1.实例化ServerSocket对象，绑定端口号
            serverSocket = new ServerSocket(DEFAULT_PORT);
            System.out.println("服务端启动，监听端口：" + DEFAULT_PORT);
            while (true){
                //2.服务器端持续监听客户端连接请求
                Socket socket = serverSocket.accept();
                System.out.println("客户端[" + socket.getPort() + "]已连接");
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream()));

                while (true) {
                    //3.读取客户端发送的消息
                    String msg = reader.readLine();
                    if (msg.equals(QUIT)){
                        System.out.println("客户端[" + socket.getPort() + "]退出");
                        break;
                    }
                    if (msg != null){
                        System.out.println("客户端[" + socket.getPort() + "]发送消息：" + msg);
                    }
                    //4.回复客户端消息
                    writer.write("服务器回复：" + msg + "\n");
                    writer.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (serverSocket != null){
                try {
                    //5.关闭服务器端Socket
                    serverSocket.close();
                    System.out.println("关闭了serverSocket");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

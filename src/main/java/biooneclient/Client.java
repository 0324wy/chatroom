package biooneclient;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        final String QUIT = "quit";
        final String DEFAULT_SERVER_HOST = "127.0.0.1";
        final int DEFAULT_SERVER_PORT = 8888;
        Socket socket = null;
        BufferedWriter writer = null;
        try {
            //1.实例化socket，并传入服务器主机名和端口号
            socket = new Socket(DEFAULT_SERVER_HOST, DEFAULT_SERVER_PORT);

            //2.创建IO流
            writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            //3.等待用户输入信息
            BufferedReader consoleReader = new BufferedReader(
                    new InputStreamReader(System.in));
            while (true) {
                String input = consoleReader.readLine();
                //4.信息发送给服务器
                writer.write(input + "\n");
                writer.flush();
                if (input.equals(QUIT)){
                    break;
                }
                //5.读取服务器发送的消息
                String msg = reader.readLine();
                System.out.println(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                //关闭writer，也就flush了，也就关闭了socket
                writer.close();
                System.out.println("关闭了socket");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

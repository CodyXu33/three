package cn.lapis17.server;

import java.net.ServerSocket;
import java.net.Socket;

public class Main {


    public static void main(String[] args)  {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            System.out.println("等待连接中：");
            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("收到客户端连接:"+client.getInetAddress().getHostAddress());
                HandleAccept handleAccept = new HandleAccept(client);
                handleAccept.start();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}

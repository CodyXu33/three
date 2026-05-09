package cn.lapis17.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 9999;
        Socket socket = new Socket(host, port);
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        //监听服务器信息
        new Thread(() -> {
            while (true) {

                String s = null;
                try {
                    s = bufferedReader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                if (s != null) {
                    System.out.println(s);
                }
            }


        }).start();
        //end 监听服务器信息


        System.out.println("请输入用户名：");
        String username = new Scanner(System.in).nextLine();
        bufferedWriter.write("LOGIN:" + username + "\n");
        bufferedWriter.flush();
        System.out.println();
        System.out.println("请输入消息（按q退出)：");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            if (line.equals("q")) {
                bufferedWriter.write("LOGOUT:" + username + "\n");
                bufferedWriter.flush();
                break;
            }
            bufferedWriter.write(username + ":" + line + "\n");
            bufferedWriter.flush();
        }

        bufferedWriter.close();
        bufferedReader.close();
        socket.close();
    }
}

package cn.lapis17.server;

import cn.lapis17.utils.SocketMapUtil;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class HandleAccept implements Runnable{

    BufferedWriter bufferedWriter = null;
    BufferedReader bufferedReader = null;

    Socket client;
    public HandleAccept(Socket socket) {
        this.client = socket;
    }

    @Override
    public void run() {
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            bufferedReader  = new BufferedReader(new InputStreamReader(client.getInputStream()));
            while (true) {
                String message = bufferedReader.readLine();
                //消息处理
                if (message != null) {
                    if (message.startsWith("LOGIN")) {
                        String[] split = message.split(":");
                        String username = split[1];
                        SocketMapUtil.onLineUserMap.put(username, client);
                        broadcast((username + "上线了\n"),username);
                    }else if(message.startsWith("LOGOUT")){
                        String username = message.split(":")[1];
                        broadcast((username + "下线了\n"),username);
                        SocketMapUtil.onLineUserMap.remove(username);
                        break;
                    }else {
                        broadcast(message);
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            SocketMapUtil.onLineUserMap.entrySet().removeIf(entry -> entry.getValue() == client);
        }

    }


    public void start () {
        Thread thread = new Thread(this);
        thread.start();
    }

    //广播
    private void broadcast(String message) throws IOException {
        String username = message.split(":")[0];
        String messageContent = message.split(":")[1];
        for (Map.Entry<String, Socket> socketEntry : SocketMapUtil.onLineUserMap.entrySet()) {
            String key = socketEntry.getKey();
            if (!key.equals(username)) {
                Socket socket = socketEntry.getValue();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bufferedWriter.write("<----"+username+"说："+messageContent+"\n");
                bufferedWriter.flush();
            }else {
                bufferedWriter.write("---->你说："+messageContent+"\n");
                bufferedWriter.flush();
            }
        }
    }

    private void broadcast(String message,String ignoreUsername) throws IOException {
        for (Map.Entry<String, Socket> socketEntry : SocketMapUtil.onLineUserMap.entrySet()) {
            String key = socketEntry.getKey();
            if (!key.equals(ignoreUsername)) {
                Socket socket = socketEntry.getValue();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bufferedWriter.write(message);
                bufferedWriter.flush();
            }
        }
    }

}

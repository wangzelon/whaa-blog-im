package com.whaa.blog.chat.jdk;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * created by wangzelong 2021-06-07 16:23
 */
public class IOClient {
    public static void main(String[] args) {
        new Thread(()->{
            try {
                Socket socket=new Socket("127.0.0.1",8888);
                while (true){
                    socket.getOutputStream().write((new Date()+"hello world").getBytes(StandardCharsets.UTF_8));
                    Thread.sleep(2000);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

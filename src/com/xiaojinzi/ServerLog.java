package com.xiaojinzi;

import com.google.gson.Gson;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;

/**
 * Created by cxj on 21/12/2017.
 */
public class ServerLog implements Runnable {

    private static ServerLog log = new ServerLog();

    private Gson g = new Gson();

    public static ServerLog getLog() {
        return log;
    }

    private ServerSocket serverSocket;

    private ServerLog() {
        new Thread(this).start();
    }

    private boolean isLoopSendMessage = true;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(13579);
            while (true) {

                System.out.println("create Socket success");

                final Socket socket = serverSocket.accept();

                new Thread() {
                    @Override
                    public void run() {

                        try {

                            OutputStream outputStream = socket.getOutputStream();
                            PrintWriter printWriter = new PrintWriter(outputStream);

                            InputStream inputStream = socket.getInputStream();
                            Reader reader = new InputStreamReader(inputStream, "UTF-8");
                            BufferedReader bufferedReader = new BufferedReader(reader);

                            StringBuffer sb = new StringBuffer();
                            String line = null;

                            while (isLoopSendMessage) {

                                line = bufferedReader.readLine();
                                if (line == null || "".equals(line)) {
                                    continue;
                                }

                                try {
                                    JSONObject jb = new JSONObject(line);
                                    String tag = jb.optString("device_tag");
                                    if (tag == null || "".equals(tag)) {
                                        jb.put("device_tag", socket.getRemoteSocketAddress().toString());
                                        line = jb.toString();
                                    }
                                } catch (Exception ignore) {
                                }

                                //System.out.println("line = \n" + line);

                                WebSocket.sendMessage(line);

                            }

                        } catch (IOException e) {
                        }
                    }
                }.start();

            }
        } catch (IOException e) {
            System.out.println("create Socket fail");
        }
    }



}

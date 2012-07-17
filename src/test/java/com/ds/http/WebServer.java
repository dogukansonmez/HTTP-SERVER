package com.ds.http;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Simple multithreaded web http.
 *
 * @author Dogukan Sonmez
 */

public class WebServer {

    ServerSocket serverSocket;

    public WebServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void startProcessing() {
        System.out.println("Web http starting on port: " + serverSocket.getLocalPort());
        while (true) {
            try {
                System.out.println("Listening for client request");
                Socket socket = serverSocket.accept();
                System.out.println("Client request accepted");
                process(socket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void process(final Socket socket) throws IOException {
        if (socket == null) return;
        Runnable clientRequestHandler = new Runnable() {
            public void run() {
                try {
                    System.out.println("Starting to process client request");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String message = bufferedReader.readLine();

                    OutputStream outputStream = socket.getOutputStream();
                    DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                    dataOutputStream.writeUTF("Received message is  " + message);

                    bufferedReader.close();
                    dataOutputStream.flush();
                    socket.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(clientRequestHandler).start();
    }

    public static void main(String... args) {
        try {
            new WebServer(8080).startProcessing();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

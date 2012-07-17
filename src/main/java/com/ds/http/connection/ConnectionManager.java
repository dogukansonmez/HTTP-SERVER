package com.ds.http.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Dogukan Sonmez
 */

public class ConnectionManager {

    private ServerSocket serverSocket;
    
    private int socketTimeout;

    public ConnectionManager(int port, int socketTimeoutInMilliseconds) throws IOException {
        serverSocket = new ServerSocket(port);
        this.socketTimeout = socketTimeoutInMilliseconds;
    }

    public Connection awaitClient() throws IOException {
        Socket socket = serverSocket.accept();
        return ConnectionBuilder.buildConnection(socket,socketTimeout);
    }

    public void shutDown() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

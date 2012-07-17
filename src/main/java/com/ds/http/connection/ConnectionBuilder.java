package com.ds.http.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Dogukan Sonmez
 */

public class ConnectionBuilder {

    public static Connection buildConnection(Socket socket, int socketTimeout) throws IOException {
        Connection connection = new Connection();
        socket.setSoTimeout(socketTimeout);
        connection.setSocket(socket);
        connection.setOpen(true);
        BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        connection.setBufferedReader(rd);
        return connection;
    }
}

package com.ds.http.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author Dogukan Sonmez
 */

public class Connection {

    private Socket socket;

    private boolean open;

    private BufferedReader bufferedReader;

    public OutputStream getOutputStream() throws IOException {
        return this.socket.getOutputStream();
    }

    public InputStream getInputStream() throws IOException {
        return this.socket.getInputStream();
    }

    public void disconnect(){
        this.open = false;
        try {
            socket.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isOpen(){
        return open;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public String readLine() throws IOException {
        return  bufferedReader.readLine();
    }

    public int read() throws IOException {
        return bufferedReader.read();
    }
}

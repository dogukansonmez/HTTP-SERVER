package com.ds.http;

/**
 * @author Dogukan Sonmez
 */

public class Bootstrap {

    public static void main(String... args) {
        Server server = new Server();
        server.start();
        try {
            System.out.println("Press to stop server...");
            System.in.read();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

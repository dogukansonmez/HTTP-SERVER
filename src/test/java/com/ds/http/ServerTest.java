package com.ds.http;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static junit.framework.Assert.assertEquals;

/**
 * @author Dogukan Sonmez
 */

public class ServerTest {

    Server server;

    @Before
    public void doBefore(){
        server = new Server();
        server.start();
    }
    @Test(expected = FileNotFoundException.class)
    public void httpGetRequest() throws IOException {
        URLConnection urlConnection =  new URL("http://localhost:8080/Thereisnofile").openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String inputLine = in.readLine();
        assertEquals("GET / HTTP/1.1",inputLine);
    }
}

package com.ds.http;

import com.ds.http.connection.Connection;
import com.ds.http.connection.ConnectionManager;
import com.ds.http.processor.ClientRequestProcessor;
import com.ds.http.resource.ResourceFinder;
import com.ds.http.scheduler.ClientRequestScheduler;
import com.ds.http.scheduler.RequestScheduler;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Dogukan Sonmez
 */

public class Server {

    public static final String PROP_FILE = "server.properties";

    volatile boolean isServerAlive = true;

    private ConnectionManager connectionManager;

    private RequestScheduler requestScheduler;

    private ExecutorService mainExecutor;

    private Properties properties;

    private String webRoot;


    public Server() {
        loadProperties();
        init();
    }

    private void init() {
        try {
            int port = Integer.parseInt(properties.getProperty("portNumber"));
            int threadCount = Integer.parseInt(properties.getProperty("threadCount"));
            int socketTimeoutInMilliseconds =  Integer.parseInt(properties.getProperty("timeout"));
            webRoot = properties.getProperty("webRoot");
            connectionManager = new ConnectionManager(port,socketTimeoutInMilliseconds);
            requestScheduler = new ClientRequestScheduler(threadCount);
            mainExecutor = Executors.newSingleThreadExecutor();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadProperties() {
        try {
            properties = new Properties();
            InputStream is = getClass().getResourceAsStream(PROP_FILE);
            properties.load(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        System.out.println("Server started...");
        mainExecutor.execute(new Runnable() {
            public void run() {
                while (isServerAlive) {
                    try {
                        Connection connection = connectionManager.awaitClient();
                        ClientRequestProcessor clientRequestProcessor = new ClientRequestProcessor(connection);
                        clientRequestProcessor.setResourceFinder(new ResourceFinder(webRoot));
                        requestScheduler.schedule(clientRequestProcessor);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void stop() {
        connectionManager.shutDown();
        requestScheduler.shutDown();
        mainExecutor.shutdown();
    }

}

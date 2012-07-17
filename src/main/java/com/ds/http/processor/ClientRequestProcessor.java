package com.ds.http.processor;

import com.ds.http.connection.Connection;
import com.ds.http.constants.HTTP;
import com.ds.http.message.HttpMethod;
import com.ds.http.message.HttpRequest;
import com.ds.http.message.HttpResponse;
import com.ds.http.message.HttpResponseFactory;
import com.ds.http.processor.util.LineIterator;
import com.ds.http.resource.ResourceFinder;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Dogukan Sonmez
 */

public class ClientRequestProcessor {

    private Connection connection;

    private ResourceFinder resourceFinder;

    public void setResourceFinder(ResourceFinder resourceFinder) {
        this.resourceFinder = resourceFinder;
    }

    public ClientRequestProcessor(Connection connection) {
        this.connection = connection;
    }

    public void process() {
        while (!Thread.interrupted() && connection.isOpen()) {
            try {
                HttpRequest httpRequest = parseConnection();

                System.out.println("New request received." + httpRequest.getMethod() + " " + httpRequest.getURI() + " " + httpRequest.getHttpVersion());

                HttpResponse httpResponse = HttpResponseFactory.createHttpResponse(httpRequest, resourceFinder);

                sendResponse(httpResponse);

                closeConnectionIfItIsNotPersistent(httpRequest);

            } catch (Exception e) {
                if (e instanceof SocketTimeoutException) {
                    System.out.println("Read time out occurred closing connection");
                } else {
                    // e.printStackTrace();
                }
                connection.disconnect();
            }
        }


    }

    private HttpRequest parseConnection() throws IOException {
        System.out.println("Extract request from connection");
        HttpRequest httpRequest = new HttpRequest();

        String line = connection.readLine();
        LineIterator lineIterator = new LineIterator(line);
        httpRequest.setMethod(HttpMethod.valueOf(lineIterator.next()));
        httpRequest.setURI(lineIterator.next());
        httpRequest.setHttpVersion(lineIterator.next());

        Map<String, String> headers = new HashMap<String, String>();
        String nextLine = "";
        while (!(nextLine = connection.readLine()).equals("")) {
            String values[] = nextLine.split(":", 2);
            headers.put(values[0], values[1].trim());
        }
        httpRequest.setRequestHeaders(headers);

        if (headers.containsKey(HTTP.CONTENT_LENGTH)) {
            int size = Integer.parseInt(headers.get(HTTP.CONTENT_LENGTH));
            byte[] data = new byte[size];
            int n;
            for (int i = 0; i < size && (n = connection.read()) != -1; i++) {
                data[i] = (byte) n;
            }
            httpRequest.setPayload(data);
        }

    return httpRequest;
}

    private void sendResponse(HttpResponse response) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
        writer.write(HTTP.VERSION);
        writer.write(' ');
        writer.write("" + response.getStatusCode().getCode());
        writer.write(' ');
        writer.write(response.getStatusCode().getInfo());
        writer.write(HTTP.EOL);
        if (response.getHeaders() != null) {
            for (String key : response.getHeaders().keySet()) {
                writer.write(key + ": " + response.getHeaders().get(key) + HTTP.EOL);
            }
        }
        writer.write(HTTP.EOL);
        if (response.getEntity() != null) {
            writer.write(response.getEntity());
        }
        writer.flush();
        connection.getOutputStream().flush();
    }

    private void closeConnectionIfItIsNotPersistent(HttpRequest httpRequest) {
        if (httpRequest.getHttpVersion().equalsIgnoreCase(HTTP.VERSION) && httpRequest.getRequestHeaders().containsKey(HTTP.CONNECTION)) {
            System.out.println("Not going to close connection due to persistent properties...");
        } else {
            connection.disconnect();
        }
    }


}

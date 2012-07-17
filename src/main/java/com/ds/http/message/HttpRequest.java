package com.ds.http.message;

import java.util.Map;

/**
 * @author Dogukan Sonmez
 */

public class HttpRequest {

    private HttpMethod method;
    
    private String URI;
    
    private String httpVersion;
    
    private Map<String,String> requestHeaders;

    private byte[] payload;

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String getURI() {
        return URI;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(Map<String, String> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }
}

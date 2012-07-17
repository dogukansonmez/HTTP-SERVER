package com.ds.http.message;

/**
 * @author Dogukan Sonmez
 */

public enum HttpStatusCode {

    OK(200, "OK"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int code;

    private final String info;

    HttpStatusCode(int code,String info){
        this.code = code;
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }


}

package com.ds.http.message;

import com.ds.http.constants.HTTP;
import com.ds.http.resource.ResourceFinder;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author Dogukan Sonmez
 */

public class HttpPostResponse extends AbstractHttpResponse implements HttpResponse {

    public HttpPostResponse(HttpRequest request, ResourceFinder resourceFinder) {
        super(request, resourceFinder);
    }

    @Override
    void prepareResponse() {
        File file = getResource(getRequest().getURI());
        if(file.isDirectory()){
            setStatusCode(HttpStatusCode.NOT_FOUND);
            setEntity("<html><body><h1> File " + file.getPath() + " not found! </h1></body></html>");
            addHeader(HTTP.CONTENT_TYPE, "text/html");
            System.out.println("File " + file.getPath() + " not found");
        }else{
            try {
                FileOutputStream fos = new FileOutputStream(file.getPath());
                fos.write(getRequest().getPayload());
                setStatusCode(HttpStatusCode.OK);
                addHeader(HTTP.CONTENT_TYPE, "text/html");
                setEntity("<html><body><h1> File saved successfully! </h1></body></html>");
                System.out.println("New file created" + file.getPath());
            } catch (Exception e) {
                setStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR);
                setEntity("<html><body><h1> Exception occurred while writing file... </h1></body></html>");
                e.printStackTrace();
            }


        }
    }
}

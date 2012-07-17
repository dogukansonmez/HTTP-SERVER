package com.ds.http.message;

import com.ds.http.constants.HTTP;
import com.ds.http.resource.ResourceFinder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Dogukan Sonmez
 */

public class HttpGetResponse extends AbstractHttpResponse implements HttpResponse {


    public HttpGetResponse(HttpRequest request, ResourceFinder resourceFinder) {
        super(request,resourceFinder);
    }

    public void prepareResponse() {

        File file = getResource(getRequest().getURI());
        if (!file.exists()) {
            setStatusCode(HttpStatusCode.NOT_FOUND);
            setEntity("<html><body><h1> File " + file.getPath() + " not found! </h1></body></html>");
            addHeader(HTTP.CONTENT_TYPE, "text/html");
            System.out.println("File " + file.getPath() + " not found");

        } else if (!file.canRead()) {
            setStatusCode(HttpStatusCode.FORBIDDEN);
            setEntity("<html><body><h1> Access denied </h1></body></html>");
            addHeader(HTTP.CONTENT_TYPE, "text/html");
            System.out.println("Cannot read file " + file.getPath());

        }else if(file.isDirectory()){
            setStatusCode(HttpStatusCode.OK);
            StringBuilder sb = new StringBuilder();
            sb.append("<html><body><h1> Files :</h1><hr><br><a href=\"..\">Parent Directory</a><br>");
            String[] files = file.list();
            for (int i = 0; files != null && i < files.length; i++) {
                File f = new File(file, files[i]);
                if (f.isDirectory()) {
                    sb.append("<a href=\""+files[i]+"/\">"+files[i]+"/</a><br>");
                } else {
                    sb.append("<a href=\""+files[i]+"\">"+files[i]+"</a><br>");
                }
            }
            sb.append("<hr></body></html>");
            addHeader(HTTP.CONTENT_TYPE, "text/html");
            setEntity(sb.toString());
        }
        else {
            setStatusCode(HttpStatusCode.OK);
            addHeader(HTTP.CONTENT_TYPE, getContentType(file.getName()));
            addHeader(HTTP.CONTENT_LENGTH, file.length() + "");
            setEntity(getFileContent(file));
            System.out.println("Serving file " + file.getPath());
        }
    }


    private String getFileContent(File file) {
        StringBuilder contents = new StringBuilder();
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            try {
                String line = null;
                while ((line = input.readLine()) != null) {
                    contents.append(line);
                    contents.append(System.getProperty("line.separator"));
                }
            } finally {
                input.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return contents.toString();
    }

}

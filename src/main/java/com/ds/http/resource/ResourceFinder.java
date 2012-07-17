package com.ds.http.resource;

import java.io.File;

/**
 * @author Dogukan Sonmez
 */

public class ResourceFinder {

    private String webRoot;
    
    public ResourceFinder(String webRoot){
        this.webRoot = webRoot;
    }

    public String getWebRoot() {
        return webRoot;
    }

    public void setWebRoot(String webRoot) {
        this.webRoot = webRoot;
    }

    public File find(String path){
        return new File(webRoot,path);
    }
}

package com.ds.http.processor.util;

/**
 * @author Dogukan Sonmez
 */

public class LineIterator {

    private String splitter = " ";
    
    private String[] data;
    
    private int currentIndex = 0;
    
    public LineIterator(String line) {
        data = parseLine(line);
    }

    private String[] parseLine(String line) {
        if(line == null)
            return new String[0];
        else
            return line.split(splitter);
    }

    public String next() {
        if(data != null && currentIndex > -1 && currentIndex < data.length)
            return data[currentIndex++];
        else
            return null;
    }

    public void setSplitter(String splitter) {
        this.splitter = splitter;
    }

    public String getSplitter() {
        return splitter;
    }
}

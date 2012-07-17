package com.ds.http.scheduler;

import com.ds.http.processor.ClientRequestProcessor;

/**
 * @author Dogukan Sonmez
 */

public interface RequestScheduler {

    public void schedule(ClientRequestProcessor clientRequestProcessor);

    public void shutDown();

}

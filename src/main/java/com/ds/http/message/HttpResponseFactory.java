package com.ds.http.message;

import com.ds.http.exceptions.UnSupportedHttpMethodException;
import com.ds.http.resource.ResourceFinder;

/**
 * @author Dogukan Sonmez
 */

public class HttpResponseFactory {

    public static HttpResponse createHttpResponse(HttpRequest request,ResourceFinder resourceFinder) throws UnSupportedHttpMethodException {

        switch (request.getMethod()) {
            case GET:
                return new HttpGetResponse(request,resourceFinder);
            case POST:
                return new HttpPostResponse(request,resourceFinder);
        }

        throw new UnSupportedHttpMethodException("Un supported HTTP request method :" + request.getMethod().toString());

    }
}

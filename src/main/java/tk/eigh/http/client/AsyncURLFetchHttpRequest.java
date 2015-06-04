/*
 * Copyright Webapp Pavic Switzerland
 */
package tk.eigh.http.client;

import com.google.appengine.api.ThreadManager;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import java.io.IOException;
import java.util.concurrent.Callable;
import org.springframework.http.client.AsyncClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureTask;

/**
 *
 * @author NT <Ante Pavic at 8K>
 */
class AsyncURLFetchHttpRequest extends URLFetchHttpRequest implements AsyncClientHttpRequest {


    AsyncURLFetchHttpRequest(HTTPRequest request) {
       super(request);
    }

    @Override
    public ListenableFuture<ClientHttpResponse> executeAsync() throws IOException {
        final URLFetchService service = URLFetchServiceFactory.getURLFetchService();
        ListenableFutureTask<ClientHttpResponse> future = new ListenableFutureTask<>(new Callable<ClientHttpResponse>() {

            @Override
            public ClientHttpResponse call() throws Exception {
                return new URLFetchHttpResponse(service.fetch(request));
            }
        });
        ThreadManager.createThreadForCurrentRequest(future).start();
        return future;
    }

}

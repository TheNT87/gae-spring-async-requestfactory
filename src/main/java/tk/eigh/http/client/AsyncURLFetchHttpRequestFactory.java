/*
 * Copyright Webapp Pavic Switzerland
 */
package tk.eigh.http.client;

import static com.google.appengine.api.urlfetch.FetchOptions.Builder.*;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import java.io.IOException;
import java.net.URI;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.AsyncClientHttpRequest;
import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;

/**
 *
 * @author NT <Ante Pavic at 8K>
 */
public class AsyncURLFetchHttpRequestFactory implements AsyncClientHttpRequestFactory,ClientHttpRequestFactory {

    private final static double DEAD_LINE = 38d;

    @Override
    public AsyncClientHttpRequest createAsyncRequest(URI uri, HttpMethod httpMethod) throws IOException {
        return new AsyncURLFetchHttpRequest(new HTTPRequest(uri.toURL(), HTTPMethod.valueOf(httpMethod.name()),
                allowTruncate().setDeadline(DEAD_LINE).doNotValidateCertificate()));
    }

    @Override
    public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
        return new URLFetchHttpRequest(new HTTPRequest(uri.toURL(), HTTPMethod.valueOf(httpMethod.name()),
                allowTruncate().setDeadline(DEAD_LINE).doNotValidateCertificate()));
    }

}

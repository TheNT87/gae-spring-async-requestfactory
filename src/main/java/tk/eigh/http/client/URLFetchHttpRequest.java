/*
 * Copyright Webapp Pavic Switzerland
 */
package tk.eigh.http.client;

import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;

/**
 *
 * @author NT <Ante Pavic at 8K>
 */
class URLFetchHttpRequest implements ClientHttpRequest {

    final HTTPRequest request;
    final HttpHeaders headers = new HttpHeaders();
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    URLFetchHttpRequest(HTTPRequest request) {
        this.request = request;
    }

    @Override
    public ClientHttpResponse execute() throws IOException {
        URLFetchService fetchService = URLFetchServiceFactory.getURLFetchService();
        return new URLFetchHttpResponse(fetchService.fetch(request));
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.valueOf(request.getMethod().name());
    }

    @Override
    public URI getURI() {
        try {
            return request.getURL().toURI();
        } catch (URISyntaxException ex) {
            return null;
        }
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }

    @Override
    public OutputStream getBody() throws IOException {
        return baos;
    }
}

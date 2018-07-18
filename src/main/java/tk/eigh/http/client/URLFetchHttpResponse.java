/*
 * Copyright Webapp Pavic Switzerland
 */
package tk.eigh.http.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPResponse;

/**
 *
 * @author NT <Ante Pavic at 8K>
 */
class URLFetchHttpResponse implements ClientHttpResponse {

    private final HTTPResponse   response;
    private ByteArrayInputStream bais;

    public URLFetchHttpResponse(HTTPResponse response) {
        this.response = response;
    }

    @Override
    public HttpStatus getStatusCode() throws IOException {
        return HttpStatus.valueOf(response.getResponseCode());
    }

    @Override
    public int getRawStatusCode() throws IOException {
        return response.getResponseCode();
    }

    @Override
    public String getStatusText() throws IOException {
        return HttpStatus.valueOf(response.getResponseCode()).getReasonPhrase();
    }

    @Override
    public void close() {
        try {
            bais.close();
        } catch (IOException ex) {
            Logger.getLogger(URLFetchHttpResponse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public InputStream getBody() throws IOException {
        bais = new ByteArrayInputStream(response.getContent());
        return bais;
    }

    @Override
    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        for (HTTPHeader header : response.getHeaders()) {
            headers.add(header.getName(), header.getValue());
        }
        return headers;
    }

}

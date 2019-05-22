package io.spring.lab.web.client.rewrite;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UriRewritingHttpRequestWrapper implements HttpRequest {

    private final HttpRequest request;

    private final UriCustomizer customizer;

    @Override
    public String getMethodValue() {
        return request.getMethodValue();
    }

    @Override
    public URI getURI() {
        return customizer.apply(request.getURI());
    }

    @Override
    public HttpHeaders getHeaders() {
        return request.getHeaders();
    }
}

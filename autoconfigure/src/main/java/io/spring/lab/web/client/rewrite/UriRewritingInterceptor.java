package io.spring.lab.web.client.rewrite;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UriRewritingInterceptor implements ClientHttpRequestInterceptor {

    private final UriCustomizer customizer;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        return execution.execute(new UriRewritingHttpRequestWrapper(request, customizer), body);
    }
}

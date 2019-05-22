package io.spring.lab.stubrunner;

import java.net.URI;

import org.springframework.cloud.contract.stubrunner.StubFinder;
import org.springframework.core.annotation.Order;

import io.spring.lab.web.client.rewrite.UriCustomizer;
import lombok.AllArgsConstructor;

import static java.util.Optional.ofNullable;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.web.util.UriComponentsBuilder.fromUri;

@AllArgsConstructor
@Order(HIGHEST_PRECEDENCE)
public class StubRunnerUriCustomizer implements UriCustomizer {

    private final StubFinder stubFinder;

    @Override
    public URI apply(URI uri) {
        String serviceId = uri.getHost();
        return ofNullable(stubFinder.findAllRunningStubs().getPort(serviceId))
                .map(port -> fromUri(uri)
                        .host("localhost")
                        .port(port)
                        .build().toUri())
                .orElse(uri);
    }
}

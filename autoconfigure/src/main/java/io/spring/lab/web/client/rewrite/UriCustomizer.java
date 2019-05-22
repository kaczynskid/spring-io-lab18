package io.spring.lab.web.client.rewrite;

import java.net.URI;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import org.springframework.core.annotation.AnnotationAwareOrderComparator;

public interface UriCustomizer extends UnaryOperator<URI> {

    UriCustomizer identity = uri -> uri;

    BinaryOperator<UriCustomizer> composer = (u1, u2) -> uri -> u1.andThen(u2).apply(uri);

    static UriCustomizer compose(List<UriCustomizer> customizers) {
        return customizers.stream()
                .sorted(AnnotationAwareOrderComparator.INSTANCE)
                .reduce(identity, composer);
    }
}

package io.spring.lab.web.client.rewrite;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@ConditionalOnProperty(name = "uri.customizer.enabled", havingValue = "true", matchIfMissing = true)
public @interface ConditionalOnUriRewrite {

}

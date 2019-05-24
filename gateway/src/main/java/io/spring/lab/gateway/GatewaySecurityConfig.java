package io.spring.lab.gateway;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;

import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toCollection;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Slf4j
@Configuration
@Order(HIGHEST_PRECEDENCE)
@EnableWebSecurity
public class GatewaySecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ApplicationContext context = http.getSharedObject(ApplicationContext.class);
        OAuth2LogoutSuccessHandler logoutSuccessHandler = context.getBean(OAuth2LogoutSuccessHandler.class);

        http
                .authorizeRequests()
                    .anyRequest().authenticated()
                .and()
                .oauth2Login()
                        .userInfoEndpoint()
                                .userAuthoritiesMapper(this::grantedAuthoritiesMapper);

        http.logout()
                .logoutSuccessHandler(logoutSuccessHandler);
    }

    private Collection<? extends GrantedAuthority> grantedAuthoritiesMapper(Collection<? extends GrantedAuthority> authorities) {
        LinkedHashSet<SimpleGrantedAuthority> resourceAccess = authorities.stream()
                .filter(OidcUserAuthority.class::isInstance)
                .map(OidcUserAuthority.class::cast)
                .filter(this::issuedByKeycloak)
                .filter(this::hasResourceAccessClaims)
                .map(a -> a.getIdToken().getClaimAsStringList("client-authorities"))
                .flatMap(Collection::stream)
                .map(SimpleGrantedAuthority::new)
                .collect(toCollection(LinkedHashSet::new));
        log.info("RESOURCE_ACCESS: {}", resourceAccess);
        return resourceAccess;
    }

    private boolean issuedByKeycloak(OidcUserAuthority authority) {
        return authority.getIdToken().getIssuer().toString().equals("http://localhost:8080/auth/realms/sprio");
    }

    private boolean hasResourceAccessClaims(OidcUserAuthority authority) {
        return authority.getIdToken().containsClaim("client-authorities");
    }
}

package com.threembank.config;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.adapter.ForwardedHeaderTransformer;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    @ConditionalOnProperty(name = "server.ssl.enabled", havingValue = "true")
    public SecurityWebFilterChain httpsRedirectSecurityFilter(ServerHttpSecurity http) {
        securityFactory(http);
        http.redirectToHttps(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    @ConditionalOnProperty(name = "server.ssl.enabled", havingValue = "false", matchIfMissing = true)
    public SecurityWebFilterChain defaultSecurityFilter(ServerHttpSecurity http) {
        securityFactory(http);
        return http.build();
    }

    private void securityFactory(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(ex -> ex
                        .pathMatchers("/actuator/**").permitAll()
                        .pathMatchers("/actuator/prometheus").permitAll()
                        .pathMatchers("/auth/**").permitAll()
                        .anyExchange().permitAll())
                .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()))
                .cors(Customizer.withDefaults());
    }

    @Bean
    public ForwardedHeaderTransformer forwardedHeaderTransformer() {
        return new ForwardedHeaderTransformer();
    }


    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(
                Objects.requireNonNull(exchange.getRequest()
                                .getRemoteAddress())
                        .getAddress()
                        .getHostAddress()
        );
    }

}

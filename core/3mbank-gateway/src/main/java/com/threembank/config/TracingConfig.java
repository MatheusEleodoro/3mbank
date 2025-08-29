package com.threembank.config;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.http.server.reactive.observation.ServerRequestObservationContext;

@Configuration
public class TracingConfig {
    @Bean
    ObservationRegistryCustomizer<ObservationRegistry> skipActuator() {
        PathMatcher pathMatcher = new AntPathMatcher("/");
        return registry -> registry.observationConfig()
                .observationPredicate((name, context) -> {
                    if (context instanceof ServerRequestObservationContext ctx) {
                        return !pathMatcher.match("/actuator/**", ctx.getCarrier().getURI().getPath());
                    }
                    return true;
                });
    }

    @Bean
    ObservationRegistryCustomizer<ObservationRegistry> skipSecuritySpans() {
        return registry -> registry.observationConfig().observationPredicate((name, _) ->
                !name.startsWith("spring.security"));
    }
}

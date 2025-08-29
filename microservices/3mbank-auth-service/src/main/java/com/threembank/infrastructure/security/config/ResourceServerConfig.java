package com.threembank.infrastructure.security.config;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;

import com.threembank.infrastructure.security.config.properties.SecProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.stream.Collectors;

/**
 * EN: Configuration class for the OAuth2 Resource Server.
 * This class sets up the security filter chain, authentication manager, password encoder,
 * JWT decoder, JWT authentication converter, and CORS configuration for protecting resources.
 * It relies on {@link SecProperties} for various security-related configurations.
 * <br><br>
 * PT-BR: Classe de configuração para o Servidor de Recursos OAuth2.
 * Esta classe configura a cadeia de filtros de segurança, o gerenciador de autenticação, o codificador de senha,
 * o decodificador JWT, o conversor de autenticação JWT e a configuração CORS para proteger os recursos.
 * Ela depende de {@link SecProperties} para várias configurações relacionadas à segurança.
 */
@Configuration
@RequiredArgsConstructor
public class ResourceServerConfig {
    private final SecProperties properties;
    private static final String[] SWAGGER_WHITELIST = {
            "/doc",
            "/doc/**",              // ← NECESSÁRIO com path customizado
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/openapi.yaml"// ← PARA ACESSAR O YAML DIRETO
          };
    /**
     * EN: Defines the main security filter chain for the application, ordered with higher precedence (Order 2).
     * This chain configures CSRF, CORS, authorization rules for various endpoints, session management (stateless),
     * OAuth2 resource server JWT validation, and security headers. It also enables HTTPS redirection if SSL is active.
     * <br><br>
     * PT-BR: Define a cadeia de filtros de segurança principal para a aplicação, ordenada com maior precedência (Ordem 2).
     * Esta cadeia configura CSRF, CORS, regras de autorização para vários endpoints, gerenciamento de sessão (stateless),
     * validação JWT do servidor de recursos OAuth2 e cabeçalhos de segurança. Também habilita o redirecionamento para HTTPS se o SSL estiver ativo.
     *
     * @param http The HttpSecurity to configure.
     * @return The configured SecurityFilterChain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(SWAGGER_WHITELIST).permitAll()
                                .requestMatchers(
                                        "/login",
                                        "/login/client",
                                        "/refresh",
                                        "/register/user",
                                        "/actuator/**",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**").permitAll()
                                .requestMatchers("/register/client").hasRole("ADMIN")
                                .anyRequest().permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .httpBasic(AbstractHttpConfigurer::disable)
                .headers(headers -> headers
                        .httpStrictTransportSecurity(hsts -> hsts
                                .includeSubDomains(true)
                                .maxAgeInSeconds(31536000)
                        )
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::deny)
                );


        if (properties.isSSLEnable()) {
            http.redirectToHttps(Customizer.withDefaults());
        }


        return http.build();
    }

    /**
     * EN: Provides the {@link AuthenticationManager} bean from the Spring Security configuration.
     * This manager is responsible for processing authentication requests.
     * <br><br>
     * PT-BR: Fornece o bean {@link AuthenticationManager} a partir da configuração do Spring Security.
     * Este gerenciador é responsável por processar requisições de autenticação.
     *
     * @param configuration The authentication configuration.
     * @return The AuthenticationManager.
     * @throws Exception if an error occurs while retrieving the authentication manager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * EN: Provides a {@link PasswordEncoder} bean that uses a delegating password encoder.
     * This allows for multiple password encoding strategies to be supported.
     * <br><br>
     * PT-BR: Fornece um bean {@link PasswordEncoder} que utiliza um codificador de senha delegador.
     * Isso permite que múltiplas estratégias de codificação de senha sejam suportadas.
     *
     * @return The PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * EN: Creates a {@link JwtDecoder} bean configured with the RSA public key.
     * This decoder is used by the resource server to validate incoming JWTs.
     * It ensures that the provided key's public part is an instance of {@link RSAPublicKey}.
     * <br><br>
     * PT-BR: Cria um bean {@link JwtDecoder} configurado com a chave pública RSA.
     * Este decodificador é usado pelo servidor de recursos para validar JWTs recebidos.
     * Garante que a parte pública da chave fornecida seja uma instância de {@link RSAPublicKey}.
     *
     * @param rsaKey The RSAKey containing the public key for JWT signature verification.
     * @return The configured JwtDecoder.
     * @throws JOSEException if an error occurs during Nimbus JOSE library operations.
     * @throws IllegalArgumentException if the public key from {@code rsaKey} is not an instance of {@link RSAPublicKey}.
     */
    @Bean
    public JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
        if (!(rsaKey.toPublicKey() instanceof RSAPublicKey)) {
            throw new IllegalArgumentException("The public key, isn't an instance of RSAPublicKey.");
        }
        return NimbusJwtDecoder.withPublicKey((RSAPublicKey) rsaKey.toPublicKey()).build();
    }

    /**
     * EN: Provides a {@link JwtAuthenticationConverter} bean.
     * This converter extracts authorities from the "authorities" claim in a JWT
     * and maps them to {@link SimpleGrantedAuthority} objects, prefixing each role with "ROLE_".
     * <br><br>
     * PT-BR: Fornece um bean {@link JwtAuthenticationConverter}.
     * Este conversor extrai autoridades da claim "authorities" em um JWT
     * e as mapeia para objetos {@link SimpleGrantedAuthority}, prefixando cada papel com "ROLE_".
     *
     * @return The JwtAuthenticationConverter.
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            var authorities = jwt.getClaimAsStringList("authorities");
            if (authorities == null) {
                return List.of();
            }
            return authorities.stream()
                    .map(role-> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        });

        return converter;
    }

    /**
     * EN: Configures and provides a {@link CorsConfigurationSource} bean.
     * This sets up Cross-Origin Resource Sharing (CORS) policies based on the
     * settings defined in {@link SecProperties}, such as allowed origins, methods, and headers.
     * <br><br>
     * PT-BR: Configura e fornece um bean {@link CorsConfigurationSource}.
     * Isso configura as políticas de Cross-Origin Resource Sharing (CORS) com base nas
     * configurações definidas em {@link SecProperties}, como origens permitidas, métodos e cabeçalhos.
     *
     * @return The CorsConfigurationSource.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(properties.getAllowedOrigins());
        configuration.setAllowedMethods(properties.getAllowedMethods());
        configuration.setAllowedHeaders(properties.getAllowedHeaders());
        configuration.setAllowCredentials(properties.getAllowCredentials());
        configuration.setMaxAge(properties.getMaxAge());
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
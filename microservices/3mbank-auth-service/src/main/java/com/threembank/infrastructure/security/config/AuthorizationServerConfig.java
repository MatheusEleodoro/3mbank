package com.threembank.infrastructure.security.config;


import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.threembank.infrastructure.persistence.repository.ServiceRegisteredRepository;
import com.threembank.infrastructure.persistence.repository.jpa.ServiceJpaRepository;
import com.threembank.infrastructure.security.config.properties.SecProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.util.UUID;

/**
 * EN: Configuration class for the OAuth2 Authorization Server.
 * This class sets up the necessary beans and configurations for the authorization server,
 * including security filters, client registration, JWT encoding, and JWK sourcing.
 *<br><br>
 * PT-BR: Classe de configuração para o Servidor de Autorização OAuth2.
 * Esta classe configura os beans e as configurações necessárias para o servidor de autorização,
 * incluindo filtros de segurança, registro de cliente, codificação JWT e fornecimento de JWK.
 */
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class AuthorizationServerConfig {
    private final SecProperties properties;

    /**
     * EN: Defines the security filter chain for the OAuth2 Authorization Server endpoints.
     * It applies the default OAuth2 Authorization Server configurations.
     * <br<br>
     * PT-BR: Define a cadeia de filtros de segurança para os endpoints do Servidor de Autorização OAuth2.
     * Aplica as configurações padrão do Servidor de Autorização OAuth2.
     *
     * @param http The HttpSecurity to configure.
     * @return The configured SecurityFilterChain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer();

        http.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, Customizer.withDefaults());

        return http.build();
    }

    /**
     * EN: Configures the settings for the Authorization Server.
     * Specifically, it sets the token endpoint path.
     * <br<br>
     * PT-BR: Configura as definições para o Servidor de Autorização.
     * Especificamente define o caminho do endpoint de token.
     * @return The AuthorizationServerSettings.
     */
    @Bean
    public AuthorizationServerSettings serverSettings() {
        return AuthorizationServerSettings.builder()
                .tokenEndpoint("/login/client")
                .build();
    }

    /**
     * EN: Provides a repository for registered OAuth2 clients.
     * It uses a custom implementation {@link ServiceRegisteredRepository} backed by {@link ServiceJpaRepository}.
     *<br<br>
     * PT-BR: Fornece um repositório para clientes OAuth2 registrados.
     * Utiliza uma implementação customizada {@link ServiceRegisteredRepository} suportada por {@link ServiceJpaRepository}.
     *
     * @param serviceJpaRepository The JPA repository for service entities.
     * @return The RegisteredClientRepository.
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository(ServiceJpaRepository serviceJpaRepository) {
        return new ServiceRegisteredRepository(serviceJpaRepository);
    }

    /**
     * EN: Creates a JwtEncoder bean using Nimbus implementation.
     * This encoder is used to create JWTs.
     *<br<br>
     * PT-BR: Cria um bean JwtEncoder usando a implementação Nimbus.
     * Este codificador é usado para criar JWTs.
     *
     * @param source The JWKSource to use for signing JWTs.
     * @return The JwtEncoder.
     */
    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> source) {
        return new NimbusJwtEncoder(source);
    }

    /**
     * EN: Provides a JWKSource bean.
     * This source provides the JWKSet containing the RSA key used for signing and verifying JWTs.
     *<br<br>
     * PT-BR: Fornece um bean JWKSource.
     * Esta fonte fornece o JWKSet contendo a chave RSA usada para assinar e verificar JWTs.
     *
     * @return The JWKSource.
     */
    @Bean
    protected JWKSource<SecurityContext> jwkSource() {
        return ((jwkSelector, _) -> jwkSelector.select(new JWKSet(rsaKey())));
    }

    /**
     * EN: Creates an RSAKey bean.
     * This key is generated using the public and private keys from {@link SecProperties}
     * and is used for JWT operations. A unique key ID is generated for each instance.
     *<br<br>
     * PT-BR: Cria um bean RSAKey.
     * Esta chave é gerada usando as chaves pública e privada de {@link SecProperties}
     * e é usada para operações JWT. Um ID de chave exclusivo é gerado para cada instância.
     *
     * @return The RSAKey.
     */
    @Bean
    protected RSAKey rsaKey() {
        return new RSAKey.Builder(properties.getPublicKey())
                .privateKey(properties.getPrivateKey())
                .keyID(UUID.randomUUID().toString())
                .build();
    }
}
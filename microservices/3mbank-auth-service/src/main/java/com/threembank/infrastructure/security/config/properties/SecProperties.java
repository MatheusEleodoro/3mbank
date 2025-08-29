package com.threembank.infrastructure.security.config.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "services.security")
public class SecProperties {
    private Cors cors = new Cors();
    private Jwt jwt = new Jwt();
    private Ssl ssl = new Ssl();

    public RSAPrivateKey getPrivateKey(){
        return jwt.getToken().getPrivateKey();
    }

    public RSAPublicKey getPublicKey(){
        return jwt.getToken().getPublicKey();
    }

    public Duration getExpirationAt(){
        return jwt.getToken().getExpirationAt();
    }

    public Duration getExpirationRt(){
        return jwt.getToken().getExpirationRt();
    }

    public List<String> getAllowedOrigins(){
        return cors.getAllowedOrigins();
    }

    public List<String> getAllowedMethods(){
        return cors.getAllowedMethods();
    }

    public List<String> getAllowedHeaders(){
        return cors.getAllowedHeaders();
    }

    public List<String> getExposedHeaders(){
        return cors.getExposedHeaders();
    }

    public Boolean getAllowCredentials(){
        return cors.getAllowCredentials();
    }

    public Long getMaxAge(){
        return cors.getMaxAge();
    }

    public boolean isSSLEnable(){
        return this.ssl.getEnable();
    }

    public String getSSLType(){
        return  this.ssl.getType();
    }

    public String getSSLStore(){
        return  this.ssl.getStore();
    }
    public String getSSLPassword(){
        return  this.ssl.getPassword();
    }

    public String getSSLAlias(){
        return  this.ssl.getAlias();
    }


    @Getter
    @Setter
    static class Ssl {
        private Boolean enable;
        private String type;
        private String store;
        private String password;
        private String alias;

    }
    @Getter
    @Setter
    static class Cors {
        private List<String> allowedOrigins;
        private List<String> allowedMethods;
        private List<String> allowedHeaders;
        private List<String> exposedHeaders;
        private Boolean allowCredentials;
        private Long maxAge;
    }

    @Getter
    @Setter
    static class Jwt {
        private Token token = new Token();
    }


    @Setter
    @Getter
    static class Token {
        private RSAPrivateKey privateKey;
        private RSAPublicKey publicKey;
        private Duration expirationAt;
        private Duration expirationRt;

    }
}

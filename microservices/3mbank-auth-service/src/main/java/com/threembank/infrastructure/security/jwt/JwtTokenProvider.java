package com.threembank.infrastructure.security.jwt;

import com.threembank.application.service.token.TokenResult;
import com.threembank.application.service.token.TokenProvider;
import com.threembank.infrastructure.security.config.properties.SecProperties;
import com.threembank.infrastructure.security.user.BasicUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.Instant;

@RequiredArgsConstructor
@Service
public class JwtTokenProvider implements TokenProvider {
   private final SecProperties properties;
   private final HttpServletRequest request;
   private final JwtEncoder encoder;

    @Override
    public TokenResult generate(BasicUserDetails userDetails) {
        var now = Instant.now();
        var uri = URI.create(request.getRequestURL().toString());
        var issuer = "%s://%s".formatted(uri.getScheme(),uri.getAuthority());
        var claimset = JwtClaimsSet.builder()
                .subject(String.valueOf(userDetails.getId()))
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(now.plus(properties.getExpirationAt()))
                .claim("username",userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .build();

        var accessToken = encode(claimset);
        var refreshToken = generateRefreshToken(claimset.getSubject(), issuer);
        return new TokenResult(accessToken,refreshToken,claimset.getExpiresAt(),"Bearer");
    }

    private String generateRefreshToken(String subject,String issuer) {
        Instant now = Instant.now();
        Instant expiry = now.plus(properties.getExpirationRt());

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(expiry)
                .subject(subject)
                .claim("type", "refresh")
                .build();

        return encode(claims);
    }

    private String encode(JwtClaimsSet claims) {
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}

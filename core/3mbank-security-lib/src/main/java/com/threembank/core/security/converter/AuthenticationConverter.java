package com.threembank.core.security.converter;

import com.threembank.core.security.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
public class AuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private static final String AUTHORITIES_CLAIM = "authorities";
    private static final String USERNAME_CLAIM = "username";

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {

        UUID id = UUID.fromString(jwt.getSubject());
        String username = jwt.getClaimAsString(USERNAME_CLAIM);


        List<String> authoritiesClaim = jwt.getClaimAsStringList(AUTHORITIES_CLAIM);
        Set<GrantedAuthority> authorities = (authoritiesClaim != null)
                ? authoritiesClaim.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet())
                : Collections.emptySet();

        User userDetails = new User(id, username, authorities);

        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }
}
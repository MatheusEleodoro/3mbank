package com.threembank.application.service;

import com.threembank.application.service.token.TokenResult;
import com.threembank.application.usercase.AuthUseCase;
import com.threembank.domain.entity.Token;
import com.threembank.domain.repository.UserRepository;
import com.threembank.domain.repository.TokenRepository;
import com.threembank.infrastructure.security.jwt.JwtTokenProvider;
import com.threembank.infrastructure.security.user.BasicUserDetails;
import com.threembank.interfaces.dto.LoginRequest;
import com.threembank.interfaces.dto.LoginResponse;
import com.threembank.interfaces.dto.RefreshRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthUseCase {
    private final AuthenticationManager manager;
    private final JwtTokenProvider tokenProvider;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        var authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        var user = ((BasicUserDetails) authentication.getPrincipal());
        var tokenResult = tokenProvider.generate(user);

        saveUserToken(tokenResult,user.getId());

        return new LoginResponse(tokenResult.accessToken(), tokenResult.refreshToken(), tokenResult.tokenType(), tokenResult.expiresIn().getEpochSecond());
    }

    @Override
    public LoginResponse refresh(RefreshRequest refreshRequest) {
        var userToken = tokenRepository.findByToken(refreshRequest.getToken())
                .orElseThrow(() -> new BadCredentialsException("Invalid refresh token"));

        if (userToken.isRevoked() || userToken.isExpired()) {
            throw new BadCredentialsException("Expired or revoked refresh token");
        }

        var user = userRepository.findById(userToken.getUserId())
                .orElseThrow(() -> new BadCredentialsException("User not found"));

        var tokenResult = tokenProvider.generate(BasicUserDetails.builder()
                .id(user.getId())
                .authorities(new HashSet<>(user.getAuthorities()))
                .username(user.getEmail())
                .password(user.getPassword())
                .build());

        tokenRepository.revoke(userToken);
        saveUserToken(tokenResult,user.getId());
        return new LoginResponse(tokenResult.accessToken(), tokenResult.refreshToken(), tokenResult.tokenType(), tokenResult.expiresIn().getEpochSecond());
    }

    @Override
    public void logout(UUID userId) {
        tokenRepository.revokeAll(userId);
    }

    private void saveUserToken(TokenResult tokenResult, UUID userId){
        tokenRepository.save(Token.builder()
                .withRefreshToken(tokenResult.refreshToken())
                .withRevoked(false)
                .withExpiresAt(tokenResult.expiresIn())
                .withUserId(userId)
                .build());
    }
}

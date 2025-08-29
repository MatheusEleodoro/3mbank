package com.threembank.application.service.token;

import java.time.Instant;

public record TokenResult(String accessToken,String refreshToken, Instant expiresIn, String tokenType) {}

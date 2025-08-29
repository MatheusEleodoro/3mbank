package com.threembank.application.usercase;

import com.threembank.interfaces.dto.LoginRequest;
import com.threembank.interfaces.dto.LoginResponse;
import com.threembank.interfaces.dto.RefreshRequest;

import java.util.UUID;

public interface AuthUseCase {
    LoginResponse login(LoginRequest loginRequest);
    LoginResponse refresh(RefreshRequest refreshRequest);
    void logout(UUID userId);
}

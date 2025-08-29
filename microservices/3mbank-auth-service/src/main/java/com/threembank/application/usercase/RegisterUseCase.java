package com.threembank.application.usercase;

import com.threembank.interfaces.dto.RegisterClientRequest;
import com.threembank.interfaces.dto.RegisterClientResponse;
import com.threembank.interfaces.dto.RegisterUserRequest;

public interface RegisterUseCase {
    void register(RegisterUserRequest registerUserRequest);
    RegisterClientResponse register(RegisterClientRequest clientRequest);
}

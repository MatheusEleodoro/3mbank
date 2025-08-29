package com.threembank.interfaces.controller;

import com.threembank.application.usercase.AuthUseCase;
import com.threembank.infrastructure.security.user.BasicUserDetails;
import com.threembank.interfaces.controller.doc.AuthSwaggerDoc;
import com.threembank.interfaces.dto.LoginRequest;
import com.threembank.interfaces.dto.LoginResponse;
import com.threembank.interfaces.dto.RefreshRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController implements AuthSwaggerDoc {
    private final AuthUseCase auth;

    @PostMapping("/login")
    @Override
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        var login = auth.login(loginRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(login);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshRequest refreshRequest) {
        var login = auth.refresh(refreshRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(login);
    }

    @PutMapping("/logout")
    public ResponseEntity<Void> logout(BasicUserDetails basicUser) {
        auth.logout(basicUser.getId());
        return ResponseEntity.noContent().build();
    }
}

package com.threembank.interfaces.controller;

import com.threembank.application.usercase.RegisterUseCase;
import com.threembank.interfaces.controller.doc.RegisterSwaggerDoc;
import com.threembank.interfaces.dto.RegisterClientRequest;
import com.threembank.interfaces.dto.RegisterClientResponse;
import com.threembank.interfaces.dto.RegisterUserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController implements RegisterSwaggerDoc {
    private final RegisterUseCase register;

    @PostMapping("/user")
    @Override
    public void register(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        register.register(registerUserRequest);
    }

    @PostMapping("/client")
    public ResponseEntity<RegisterClientResponse> register(@RequestBody @Valid RegisterClientRequest clientRequest) {
        var registered = register.register(clientRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registered);
    }
}

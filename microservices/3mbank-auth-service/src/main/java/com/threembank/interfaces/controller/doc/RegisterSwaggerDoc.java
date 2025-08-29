package com.threembank.interfaces.controller.doc;

import com.threembank.interfaces.dto.RegisterUserRequest;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Register", description = "This endpoint is used to register a new user")
public interface RegisterSwaggerDoc {

    void register(@Parameter(description = "Request object to register user") RegisterUserRequest registerUserRequest);
}

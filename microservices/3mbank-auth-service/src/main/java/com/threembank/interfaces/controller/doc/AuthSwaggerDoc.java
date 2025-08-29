package com.threembank.interfaces.controller.doc;

import com.threembank.interfaces.dto.LoginRequest;
import com.threembank.interfaces.dto.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AuthSwaggerDoc {

    @Operation(
            summary = "User login",
            description = "Authenticates the user and returns a JWT token if credentials are valid",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login successful",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LoginResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid credentials or request format",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized - credentials do not match",
                            content = @Content
                    )
            }
    )
    ResponseEntity<LoginResponse> login(@Parameter(name = "Login", schema = @Schema(implementation = LoginRequest.class)) LoginRequest loginRequest);
}

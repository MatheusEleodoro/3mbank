package com.threembank.application.service;

import com.threembank.application.exception.UseCaseValidationException;
import com.threembank.application.mapper.UserMapper;
import com.threembank.application.usercase.RegisterUseCase;
import com.threembank.domain.entity.Client;
import com.threembank.domain.repository.ServiceRepository;
import com.threembank.domain.repository.UserRepository;
import com.threembank.interfaces.dto.RegisterClientRequest;
import com.threembank.interfaces.dto.RegisterClientResponse;
import com.threembank.interfaces.dto.RegisterUserRequest;
import com.threembank.shared.message.ValidationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterUseCase {
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    @Override
    public void register(RegisterUserRequest registerUserRequest) {
        if(userRepository.exists(registerUserRequest.getEmail())){
            throw new UseCaseValidationException(ValidationMessage.of("e-mail", "This e-mail already exists"));
        }
        var user  = userMapper.toUser(registerUserRequest);
        user.definePassword(registerUserRequest.getPassword(), encoder);
        userRepository.save(user);
    }

    @Override
    public RegisterClientResponse register(RegisterClientRequest clientRequest) {
        if(serviceRepository.exists(clientRequest.getClientId())){
            throw new UseCaseValidationException(ValidationMessage.of("clientId", "This clientId already exists"));
        }
        var client = Client.create(clientRequest.getClientId(),clientRequest.getScopes());
        client.secret(encoder);
        serviceRepository.save(client);
        return new RegisterClientResponse(client.getClientId(), client.getRawSecret());
    }
}

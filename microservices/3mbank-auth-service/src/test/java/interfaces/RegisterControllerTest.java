package interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threembank.application.exception.UseCaseValidationException;
import com.threembank.application.usercase.RegisterUseCase;
import com.threembank.domain.valueobject.Role;
import com.threembank.domain.valueobject.Scope;
import com.threembank.interfaces.controller.RegisterController;
import com.threembank.interfaces.dto.RegisterClientRequest;
import com.threembank.interfaces.dto.RegisterClientResponse;
import com.threembank.interfaces.dto.RegisterUserRequest;
import com.threembank.interfaces.exception.GlobalExceptionHandler;
import com.threembank.shared.message.ValidationMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RegisterControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RegisterUseCase registerUseCase;

    @InjectMocks
    private RegisterController registerController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(registerController)
                .setValidator(validator)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void registerUser_callsUseCase() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest(
                "Teste",
                "Unit",
                "teste@unit.com",
                "Test@123Qwerty",
                true,
                Set.of(Role.USER)
        );

        doNothing().when(registerUseCase).register(any(RegisterUserRequest.class));

        mockMvc.perform(post("/register/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(registerUseCase, times(1)).register(any(RegisterUserRequest.class));
    }

    @Test
    void registerClient_callsUseCase_returnsCreated() throws Exception {
        RegisterClientRequest request = new RegisterClientRequest();
        request.setClientId("test-client-id");
        request.setScopes(List.of(Scope.READ, Scope.WRITE));

        RegisterClientResponse response = new RegisterClientResponse("test-client-id", "generatedClientSecret");

        when(registerUseCase.register(any(RegisterClientRequest.class))).thenReturn(response);

        mockMvc.perform(post("/register/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clientId").value("test-client-id"))
                .andExpect(jsonPath("$.clientSecret").value("generatedClientSecret"));

        verify(registerUseCase, times(1)).register(any(RegisterClientRequest.class));
    }

    @Test
    void registerUser_nullFirstName_badRequest() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest(null, "Unit", "teste@unit.com", "Test@123Qwerty", true, Set.of(Role.USER));
        mockMvc.perform(post("/register/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
        verify(registerUseCase, never()).register(any(RegisterUserRequest.class));
    }

    @Test
    void registerUser_emptyFirstName_badRequest() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest("", "Unit", "teste@unit.com", "Test@123Qwerty", true, Set.of(Role.USER));
        mockMvc.perform(post("/register/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
        verify(registerUseCase, never()).register(any(RegisterUserRequest.class));
    }

    @Test
    void registerUser_nullLastName_badRequest() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest("Teste", null, "teste@unit.com", "Test@123Qwerty", true, Set.of(Role.USER));
        mockMvc.perform(post("/register/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
        verify(registerUseCase, never()).register(any(RegisterUserRequest.class));
    }

    @Test
    void registerUser_emptyLastName_badRequest() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest("Teste", "", "teste@unit.com", "Test@123Qwerty", true, Set.of(Role.USER));
        mockMvc.perform(post("/register/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
        verify(registerUseCase, never()).register(any(RegisterUserRequest.class));
    }

    @Test
    void registerUser_nullEmail_badRequest() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest("Teste", "Unit", null, "Test@123Qwerty", true, Set.of(Role.USER));
        mockMvc.perform(post("/register/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
        verify(registerUseCase, never()).register(any(RegisterUserRequest.class));
    }

    @Test
    void registerUser_nullPassword_badRequest() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest("Teste", "Unit", "teste@unit.com", null, true, Set.of(Role.USER));
        mockMvc.perform(post("/register/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
        verify(registerUseCase, never()).register(any(RegisterUserRequest.class));
    }

    @Test
    void registerUser_nullTwoFactor_badRequest() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest("Teste", "Unit", "teste@unit.com", "Test@123Qwerty", null, Set.of(Role.USER));
        mockMvc.perform(post("/register/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
        verify(registerUseCase, never()).register(any(RegisterUserRequest.class));
    }

    @Test
    void registerUser_nullRoles_badRequest() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest("Teste", "Unit", "teste@unit.com", "Test@123Qwerty", true, null);
        mockMvc.perform(post("/register/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
        verify(registerUseCase, never()).register(any(RegisterUserRequest.class));
    }

    @Test
    void registerUser_emptyRoles_badRequest() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest("Teste", "Unit", "teste@unit.com", "Test@123Qwerty", true, Collections.emptySet());
        mockMvc.perform(post("/register/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
        verify(registerUseCase, never()).register(any(RegisterUserRequest.class));
    }

    @Test
    void registerClient_nullClientId_badRequest() throws Exception {
        RegisterClientRequest request = new RegisterClientRequest();
        request.setClientId(null);
        request.setScopes(List.of(Scope.READ));
        mockMvc.perform(post("/register/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
        verify(registerUseCase, never()).register(any(RegisterClientRequest.class));
    }

    @Test
    void registerClient_emptyClientId_useCaseRejects() throws Exception {
        RegisterClientRequest request = new RegisterClientRequest();
        request.setClientId("");
        request.setScopes(List.of(Scope.READ));

        when(registerUseCase.register(any(RegisterClientRequest.class)))
                .thenThrow(new UseCaseValidationException(ValidationMessage.of("clientId", "Client ID cannot be empty")));

        mockMvc.perform(post("/register/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.clientId").value("Client ID cannot be empty"));

        verify(registerUseCase, times(1)).register(any(RegisterClientRequest.class));
    }

    @Test
    void registerClient_nullScopes_badRequest() throws Exception {
        RegisterClientRequest request = new RegisterClientRequest();
        request.setClientId("test-client");
        request.setScopes(null);
        mockMvc.perform(post("/register/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
        verify(registerUseCase, never()).register(any(RegisterClientRequest.class));
    }

    @Test
    void registerClient_emptyScopes_useCaseRejects() throws Exception {
        RegisterClientRequest request = new RegisterClientRequest();
        request.setClientId("test-client");
        request.setScopes(Collections.emptyList());

        when(registerUseCase.register(any(RegisterClientRequest.class)))
                .thenThrow(new UseCaseValidationException(ValidationMessage.of("scopes", "Scopes cannot be empty")));

        mockMvc.perform(post("/register/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.scopes").value("Scopes cannot be empty"));

        verify(registerUseCase, times(1)).register(any(RegisterClientRequest.class));
    }

    @Test
    void registerClient_useCaseThrowsValidationException_badRequest() throws Exception {
        RegisterClientRequest request = new RegisterClientRequest();
        request.setClientId("existing-client-id");
        request.setScopes(List.of(Scope.READ));

        when(registerUseCase.register(any(RegisterClientRequest.class)))
                .thenThrow(new UseCaseValidationException(ValidationMessage.of("clientId", "This clientId already exists")));

        mockMvc.perform(post("/register/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.clientId").value("This clientId already exists"));

        verify(registerUseCase, times(1)).register(any(RegisterClientRequest.class));
    }
}
package interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threembank.application.usercase.AuthUseCase;
import com.threembank.interfaces.controller.AuthController;
import com.threembank.interfaces.dto.LoginRequest;
import com.threembank.interfaces.dto.LoginResponse;
import com.threembank.interfaces.dto.RefreshRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthUseCase authUseCase;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void login_shouldReturnLoginResponseAndCreatedStatus() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("user");
        loginRequest.setPassword("password");

        LoginResponse expectedResponse = new LoginResponse("accessToken", "refreshToken", "Bearer",3600L);

        when(authUseCase.login(any(LoginRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").value("accessToken"))
                .andExpect(jsonPath("$.refreshToken").value("refreshToken"))
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.expiresIn").value(3600L));

        verify(authUseCase, times(1)).login(loginRequest);
    }

    @Test
    void refresh_shouldReturnLoginResponseAndCreatedStatus() throws Exception {
        RefreshRequest refreshRequest = new RefreshRequest();
        refreshRequest.setToken("oldRefreshToken");

        LoginResponse expectedResponse = new LoginResponse("newAccessToken", "newRefreshToken", "Bearer",3600L);

        when(authUseCase.refresh(any(RefreshRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(post("/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(refreshRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").value("newAccessToken"))
                .andExpect(jsonPath("$.refreshToken").value("newRefreshToken"))
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.expiresIn").value(3600L));

        verify(authUseCase, times(1)).refresh(refreshRequest);
    }
}
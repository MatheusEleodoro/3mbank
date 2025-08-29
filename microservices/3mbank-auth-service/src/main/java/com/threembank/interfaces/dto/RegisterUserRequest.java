package com.threembank.interfaces.dto;

import com.threembank.domain.valueobject.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;



@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Requisição para cadastro de um novo usuário no banco digital")
public class RegisterUserRequest {

    @NotEmpty
    @NotNull
    @Schema(description = "User's first name", example = "Matheus")
    private String firstName;

    @NotEmpty
    @NotNull
    @Schema(description = "User's last name", example = "Eleodoro")
    private String lastName;

    @NotEmpty
    @NotNull
    @Schema(description = "User's email address", example = "matheus@email.com")
    private String email;

    @NotEmpty
    @NotNull
    @Schema(description = "User password", example = "SenhaSegura123")
    private String password;

    @NotNull
    @Schema(description = "Has the user enabled two-factor authentication?", example = "true")
    private Boolean twoFactorEnabled;

    @NotEmpty
    @NotNull
    @Schema(description = "User permission")
    Set<Role> roles;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getTwoFactorEnabled() {
        return twoFactorEnabled;
    }

    public void setTwoFactorEnabled(Boolean twoFactorEnabled) {
        this.twoFactorEnabled = twoFactorEnabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
package com.threembank.domain.entity;

import com.threembank.domain.validation.StrongPassword;
import com.threembank.domain.validation.Validate;
import com.threembank.domain.valueobject.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * EN: Represents a user in the system.
 * This entity contains user-specific information such as identification, credentials, personal details, roles, and timestamps.
 * <br><br>
 * PT-BR: Representa um usuário no sistema.
 * Esta entidade contém informações específicas do usuário, como identificação, credenciais, detalhes pessoais, papéis e timestamps.
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class User {
    /**
     * EN: The unique identifier for the user.
     * <br><br>
     * PT-BR: O identificador único para o usuário.
     */
    private UUID id;

    /**
     * EN: The email address of the user. It must be a valid email format.
     * <br><br>
     * PT-BR: O endereço de e-mail do usuário. Deve estar em um formato de e-mail válido.
     */
    @Email(message = "E-mail is not valid")
    private String email;

    /**
     * EN: The password for the user account. It is subject to strong password validation.
     * <br><br>
     * PT-BR: A senha para a conta do usuário. Está sujeita à validação de senha forte.
     */
    @StrongPassword
    private String password;

    /**
     * EN: The first name of the user. It cannot be empty.
     * <br><br>
     * PT-BR: O primeiro nome do usuário. Não pode estar vazio.
     */
    @NotEmpty
    private String firstName;

    /**
     * EN: The last name of the user. It cannot be empty.
     * <br><br>
     * PT-BR: O sobrenome do usuário. Não pode estar vazio.
     */
    @NotEmpty
    private String lastName;

    /**
     * EN: A list of roles assigned to the user.
     * <br><br>
     * PT-BR: Uma lista de papéis atribuídos ao usuário.
     */
    private List<Role> roles;

    /**
     * EN: The timestamp when the user account was created.
     * <br><br>
     * PT-BR: O timestamp de quando a conta do usuário foi criada.
     */
    private LocalDateTime createdAt;

    /**
     * EN: The timestamp when the user account was last updated.
     * <br><br>
     * PT-BR: O timestamp da última atualização da conta do usuário.
     */
    private LocalDateTime updatedAt;


    /**
     * EN: Sets and encodes the user's password.
     * Updates the `updatedAt` timestamp to the current time.
     * <br><br>
     * PT-BR: Define e codifica a senha do usuário.
     * Atualiza o timestamp `updatedAt` para a hora atual.
     *
     * @param rawPassword The plain text password to be encoded.
     * @param encoder     The password encoder to use for hashing the password.
     */
    public void definePassword(String rawPassword , PasswordEncoder encoder) {
        this.password = encoder.encode(rawPassword);
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * EN: Validates the current state of the User object.
     * This method uses a custom validation mechanism (Validate.of) to check constraints.
     * <br><br>
     * PT-BR: Valida o estado atual do objeto User.
     * Este método utiliza um mecanismo de validação customizado (Validate.of) para verificar as restrições.
     */
    public void isValid(){
        Validate.of(this);
    }

    /**
     * EN: Retrieves the authorities (roles) granted to the user.
     * Converts the list of {@link Role} objects into a collection of {@link GrantedAuthority}.
     * <br><br>
     * PT-BR: Recupera as autoridades (papéis) concedidas ao usuário.
     * Converte a lista de objetos {@link Role} em uma coleção de {@link GrantedAuthority}.
     *
     * @return A collection of granted authorities for the user.
     */
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .toList();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
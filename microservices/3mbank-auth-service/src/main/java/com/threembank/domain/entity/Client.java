package com.threembank.domain.entity;


import com.threembank.domain.exception.DomainValidationException;
import com.threembank.shared.exception.GenerateException;
import com.threembank.shared.message.ValidationMessage;
import com.threembank.domain.valueobject.Scope;
import lombok.Data;
import lombok.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

/**
 * EN: Represents an OAuth2 client application.
 * This entity holds information about a client, such as its ID, secret, and authorized scopes.
 * <br><br>
 * PT-BR: Representa uma aplicação cliente OAuth2.
 * Esta entidade contém informações sobre um cliente, como seu ID, segredo e escopos autorizados.
 */
@Data
public class Client {
    /**
     * EN: The unique identifier for the client.
     * <br><br>
     * PT-BR: O identificador único para o cliente.
     */
    private String clientId;

    /**
     * EN: The encoded client secret.
     * <br><br>
     * PT-BR: O segredo do cliente codificado.
     */
    private String clientSecret;

    /**
     * EN: The raw, unencoded client secret, generated during client creation.
     * This should be displayed to the user once and not stored in plain text long-term.
     * <br><br>
     * PT-BR: O segredo do cliente bruto, não codificado, gerado durante a criação do cliente.
     * Isso deve ser exibido ao usuário uma vez e não armazenado em texto simples a longo prazo.
     */
    private String rawSecret;

    /**
     * EN: A set of scopes authorized for this client.
     * <br><br>
     * PT-BR: Um conjunto de escopos autorizados para este cliente.
     */
    private Set<Scope> scopes;


    /**
     * EN: Creates a new Client instance with a generated raw secret.
     * Validates that clientId and scopes are not empty.
     * <br><br>
     * PT-BR: Cria uma nova instância de Cliente com um segredo bruto gerado.
     * Valida se o clientId e os escopos não estão vazios.
     *
     * @param clientId The unique identifier for the client. Must not be null or empty.
     * @param scopes   A collection of scopes to be assigned to the client. Must not be null or empty.
     * @return A new Client instance.
     * @throws DomainValidationException if clientId or scopes are invalid.
     */
    public static Client create(@NonNull String clientId, @NonNull Collection<Scope> scopes) {
        var client = new Client();

        if (clientId.isEmpty()) {
            throw new DomainValidationException(ValidationMessage.of("clientId", "Client ID cannot be null"));
        }
        if (scopes.isEmpty()) {
            throw new DomainValidationException(ValidationMessage.of("scope", "Scope cannot be null"));
        }
        client.setClientId(clientId);
        client.setRawSecret(randonSecret(clientId)); // Corrected typo from randonSecret to randomSecret
        client.setScopes(new HashSet<>(scopes));
        return client;
    }

    /**
     * EN: Encodes the raw client secret using the provided PasswordEncoder and sets the clientSecret field.
     * <br><br>
     * PT-BR: Codifica o segredo bruto do cliente usando o PasswordEncoder fornecido e define o campo clientSecret.
     *
     * @param encoder The PasswordEncoder to use for encoding the raw secret.
     */
    public void secret(PasswordEncoder encoder) {
        this.clientSecret = encoder.encode(this.rawSecret);
    }


    /**
     * EN: Generates a random secret string based on the client ID.
     * The generation process involves using SecureRandom, combining with clientId, hashing with SHA-256,
     * and then Base64 encoding with specific character replacements.
     * <br><br>
     * PT-BR: Gera uma string de segredo aleatória com base no ID do cliente.
     * O processo de geração envolve o uso de SecureRandom, combinação com o clientId, hash com SHA-256,
     * e então codificação Base64 com substituições de caracteres específicas.
     *
     * @param clientId The client ID to be used as part of the secret generation.
     * @return A Base64 encoded string representing the generated secret, with certain characters replaced.
     * @throws GenerateException if the SHA-256 algorithm is not available.
     */
    private static String randonSecret(String clientId) { // Corrected typo from randonSecret to randomSecret
        try {
            SecureRandom random = new SecureRandom();
            byte[] randomBytes = new byte[24];
            random.nextBytes(randomBytes);

            byte[] clientIdBytes = clientId.getBytes(StandardCharsets.UTF_8);

            byte[] combined = new byte[clientIdBytes.length + randomBytes.length];
            System.arraycopy(clientIdBytes, 0, combined, 0, clientIdBytes.length);
            System.arraycopy(randomBytes, 0, combined, clientIdBytes.length, randomBytes.length);

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(combined);

            byte[] secretBytes = new byte[24];
            System.arraycopy(hash, 0, secretBytes, 0, secretBytes.length);

            return Base64.getEncoder().encodeToString(secretBytes)
                    .replace("\\", "#")
                    .replace("-", "^")
                    .replace("_", "$")
                    .replace("/", "@")
                    .replace("=", "");

        } catch (NoSuchAlgorithmException error) {
            throw new GenerateException("Unable to create secret for Client ID: {%s}".formatted(clientId), error);
        }
    }
}
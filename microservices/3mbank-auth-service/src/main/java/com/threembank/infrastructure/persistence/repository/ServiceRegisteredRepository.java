package com.threembank.infrastructure.persistence.repository;

import com.threembank.infrastructure.persistence.repository.jpa.ServiceJpaRepository;
import com.threembank.shared.exception.BasicValidationException;
import com.threembank.shared.message.ValidationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;


@RequiredArgsConstructor
public class ServiceRegisteredRepository implements RegisteredClientRepository {
    private final ServiceJpaRepository repository;

    @Override
    public void save(RegisteredClient registeredClient) {
        // Implementar se o Spring Authorization Server precisar gerenciar o ciclo de vida do RegisteredClient diretamente.
        // Atualmente, a gestão de Clientes é feita via RegisterUseCase e o método save(Client client) desta classe.
        // Lançar UnsupportedOperationException se esta funcionalidade não for prevista:
        // throw new UnsupportedOperationException("Client registration and updates are managed via custom application services.");
    }

    @Override
    public RegisteredClient findById(String id) {
        var client = repository.findById(id)
                .orElseThrow(() -> new BasicValidationException(ValidationMessage.of("clientID", "Client not found")));

        return RegisteredClient
                .withId(client.getClientId())
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scopes(scope-> scope.addAll(client.getScopes().stream().map(Enum::name).toList()))
                .build();
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return findById(clientId);
    }
}

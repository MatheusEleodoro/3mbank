package com.threembank.infrastructure.persistence.entity;

import com.threembank.domain.valueobject.Scope;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "services")
@EntityListeners(AuditingEntityListener.class)
public class ServiceEntity {

    @Id
    @Column(name = "client_id", unique = true, nullable = false)
    private String clientId;

    @Column(name = "client_secret", nullable = false)
    private String clientSecret;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "services_scopes", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "scope")
    @Enumerated(EnumType.STRING)
    private Set<Scope> scopes = new HashSet<>();
}

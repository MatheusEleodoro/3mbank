# 3MBank Config Server

![Spring Cloud Config](https://img.shields.io/badge/Spring%20Cloud-Config%20Server-brightgreen)
![HashiCorp Vault](https://img.shields.io/badge/HashiCorp-Vault-blue)
![Consul](https://img.shields.io/badge/Consul-Service%20Discovery-red)
![Java](https://img.shields.io/badge/Java-24-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.2-green)

## 📋 Índice

- [Visão Geral](#visão-geral)
- [Arquitetura](#arquitetura)
- [Fluxo de Funcionamento](#fluxo-de-funcionamento)
- [Configuração](#configuração)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Dependências](#dependências)
- [Como Executar](#como-executar)
- [Endpoints](#endpoints)
- [Monitoramento](#monitoramento)
- [Troubleshooting](#troubleshooting)

## 🎯 Visão Geral

O **3MBank Config Server** é o serviço centralizado de configuração da arquitetura de microserviços do 3MBank. Ele é responsável por fornecer configurações externalizadas para todos os microserviços do ecossistema, utilizando múltiplas fontes de dados:

- **Git Repository**: Configurações versionadas e auditáveis
- **HashiCorp Vault**: Secrets e informações sensíveis
- **Consul**: Service discovery e health checks

### 🚀 Principais Funcionalidades

- ✅ **Configuração Centralizada**: Um ponto único para todas as configurações dos microserviços
- ✅ **Múltiplas Fontes**: Integração com Git, Vault e Consul
- ✅ **Segurança**: Gestão segura de secrets via HashiCorp Vault
- ✅ **Versionamento**: Controle de versão das configurações via Git
- ✅ **Hot Reload**: Atualizações dinâmicas sem restart dos serviços
- ✅ **High Availability**: Registro automático no Consul para descoberta de serviços
- ✅ **Monitoramento**: Endpoints de health check e métricas via Actuator

## 🏗️ Arquitetura

```
┌─────────────────────────────────────────────────────────────────────────────────┐
│                              3MBank Ecosystem                                  │
├─────────────────────────────────────────────────────────────────────────────────┤
│                                                                                 │
│  ┌─────────────────────────┐     ┌─────────────────────────┐                   │
│  │   Configuration Sources │     │    Service Discovery    │                   │
│  │                         │     │                         │                   │
│  │  ┌───────────────────┐  │     │  ┌───────────────────┐  │                   │
│  │  │  Git Repository   │  │     │  │      Consul       │  │                   │
│  │  │ config-server-repo│  │     │  │ 3mbank.consul.com │  │                   │
│  │  │                   │  │     │  │      :80          │  │                   │
│  │  └───────────────────┘  │     │  └───────────────────┘  │                   │
│  │           │              │     │           │              │                   │
│  │  ┌───────────────────┐  │     │           │              │                   │
│  │  │ HashiCorp Vault   │  │     │           │              │                   │
│  │  │ 3mbank.vault.com  │  │     │           │              │                   │
│  │  │      :8200        │  │     │           │              │                   │
│  │  └───────────────────┘  │     │           │              │                   │
│  └─────────────────────────┘     └─────────────────────────┘                   │
│              │                               │                                  │
│              └───────────┐     ┌─────────────┘                                  │
│                          │     │                                                │
│                    ┌─────────────────┐                                          │
│                    │  Config Server  │                                          │
│                    │     :8888       │                                          │
│                    └─────────────────┘                                          │
│                            │                                                    │
│          ┌─────────────────┼─────────────────┐                                  │
│          │                 │                 │                                  │
│  ┌───────────────┐ ┌───────────────┐ ┌───────────────┐ ┌───────────────┐       │
│  │   Gateway     │ │ Auth Service  │ │Account Service│ │Transaction Svc│       │
│  │    :8080      │ │    :8081      │ │    :8082      │ │    :8083      │       │
│  └───────────────┘ └───────────────┘ └───────────────┘ └───────────────┘       │
│                                                                                 │
│  ┌─────────────────────────┐                                                   │
│  │      Monitoring         │                                                   │
│  │  ┌───────────────────┐  │                                                   │
│  │  │   Prometheus      │  │                                                   │
│  │  └───────────────────┘  │                                                   │
│  │  ┌───────────────────┐  │                                                   │
│  │  │     Grafana       │  │                                                   │
│  │  └───────────────────┘  │                                                   │
│  └─────────────────────────┘                                                   │
└─────────────────────────────────────────────────────────────────────────────────┘
```

### Relacionamentos entre Componentes

**Config Server** é o centro da arquitetura e:
- 📖 **Lê configurações** do repositório Git local
- 🔐 **Busca secrets** do HashiCorp Vault
- 📡 **Se registra** no Consul para service discovery
- 🚀 **Serve configurações** para todos os microserviços
- 📊 **Expõe métricas** para Prometheus/Grafana

## 🔄 Fluxo de Funcionamento

### 1. Inicialização do Config Server

```
┌─────────────────┐    1. Load bootstrap.yaml    ┌─────────────────┐
│  Config Server  │─────────────────────────────→│ Bootstrap Context│
│      App        │                               │                 │
└─────────────────┘                               └─────────────────┘
         │                                                 │
         │                                                 │ 2. Connect & authenticate
         │                                                 ▼
         │                                        ┌─────────────────┐
         │                                        │ HashiCorp Vault │
         │                                        │                 │
         │                                        └─────────────────┘
         │ 3. Initialize Git backend                        │
         ▼                                                 │ Return secrets/configs
┌─────────────────┐                                        │
│ Git Repository  │◄───────────────────────────────────────┘
│                 │
└─────────────────┘
         │
         │ Repository ready
         ▼
┌─────────────────┐    4. Register service    ┌─────────────────┐
│  Config Server  │─────────────────────────→│     Consul      │
│                 │◄─────────────────────────│                 │
└─────────────────┘   Registration confirmed  └─────────────────┘
         │
         ▼
    Server Ready ✅
```

### 2. Requisição de Configuração pelos Microserviços

```
┌─────────────────┐  1. Discover config-server  ┌─────────────────┐
│  Microservice   │─────────────────────────────→│     Consul      │
│                 │◄─────────────────────────────│                 │
└─────────────────┘  Return config-server URL    └─────────────────┘
         │
         │ 2. GET /application-name/profile/label
         ▼
┌─────────────────┐
│  Config Server  │
│                 │
└─────────────────┘
         │
         │ 3. Process Configuration Request
         ├─────────────────────────────────────────────┐
         │                                             │
         ▼ Read application configs                     ▼ Fetch secrets
┌─────────────────┐                            ┌─────────────────┐
│ Git Repository  │                            │ HashiCorp Vault │
│                 │                            │                 │
└─────────────────┘                            └─────────────────┘
         │                                             │
         │ Return config files                         │ Return encrypted secrets
         └─────────────────┐         ┌─────────────────┘
                          │         │
                          ▼         ▼
                   ┌─────────────────┐
                   │  Config Server  │
                   │ Merge configs   │
                   └─────────────────┘
                          │
                          │ Return merged configuration
                          ▼
                   ┌─────────────────┐
                   │  Microservice   │
                   │ Apply config &  │
                   │     start       │
                   └─────────────────┘
```

### 3. Fluxo de Atualização de Configuração

```
┌─────────────────┐  1. Push config changes  ┌─────────────────┐
│   Developer     │─────────────────────────→│ Git Repository  │
│                 │                          │                 │
└─────────────────┘                          └─────────────────┘
                                                      │
                                                      │
         2. Manual refresh trigger                    │
┌─────────────────┐  POST /actuator/refresh  ┌─────────────────┐
│ Actuator        │─────────────────────────→│  Config Server  │
│ Endpoint        │                          │                 │
└─────────────────┘                          └─────────────────┘
                                                      │
                            ┌─────────────────────────┼─────────────────────────┐
                            │ Pull latest changes     │                         │
                            ▼                         ▼ Re-fetch secrets       │
                   ┌─────────────────┐       ┌─────────────────┐               │
                   │ Git Repository  │       │ HashiCorp Vault │               │
                   │                 │       │                 │               │
                   └─────────────────┘       └─────────────────┘               │
                                                                                │
         3. Refresh configuration                                               │
┌─────────────────┐  GET /application-name/profile/label                       │
│  Microservice   │─────────────────────────────────────────────────────────────┘
│                 │◄─────────────────────────────────────────────────────────────┐
└─────────────────┘  Return updated configuration                               │
         │                                                                      │
         ▼ Apply new configuration                                               │
┌─────────────────┐                                                            │
│  Microservice   │                                                            │
│ Configuration   │                                                            │
│ updated without │                                                            │
│    restart ✅   │                                                            │
└─────────────────┘                                                            │
```

## ⚙️ Configuração

### Bootstrap Configuration (`bootstrap.yaml`)

O arquivo `bootstrap.yaml` é carregado **antes** do contexto principal da aplicação e configura as integrações essenciais:

```yaml
spring:
  application:
    name: config-server
  cloud:
    vault:
      enabled: true
      host: 3mbank.vault.com
      port: 8200
      scheme: http
      authentication: TOKEN
      token: myroot
      kv:
        enabled: true
        backend: secret
        default-context: application
      fail-fast: true
    config:
      server:
        vault:
          host: 3mbank.vault.com
          port: 8200
          scheme: http
          authentication: TOKEN
          token: myroot
          backend: secret
          default-key: application
          kv-version: 2
```

**📝 Configurações principais:**
- **`vault.enabled`**: Habilita integração com Vault
- **`vault.fail-fast`**: Falha rápido se Vault não estiver disponível
- **`vault.authentication`**: Método de autenticação (TOKEN)
- **`vault.kv.backend`**: Backend do Vault para key-value store

### Application Configuration (`application.yml`)

O arquivo `application.yml` configura o comportamento da aplicação:

```yaml
server:
  port: ${SERVER_PORT:8888}

spring:
  cloud:
    config:
      server:
        git:
          uri: file:///${user.dir}
          search-paths: '/3mbank-infra/config-server-repo'
          default-label: master
    consul:
      host: ${CONSUL_DISCOVERY_HOST:3mbank.consul.com}
      port: ${CONSUL_DISCOVERY_PORT:80}
      discovery:
        register: true
        prefer-ip-address: true
        health-check-path: /actuator/health
        health-check-interval: 10s

management:
  endpoints:
    web:
      exposure:
        include: health, info, refresh, vault
  endpoint:
    health:
      show-details: always

logging:
  level:
    org.springframework.cloud.config: INFO
    org.springframework.cloud.vault: INFO
    org.springframework.cloud.consul: INFO
```

**📝 Configurações principais:**
- **`config.server.git`**: Configuração do repositório Git local
- **`consul.discovery`**: Registro automático no Consul
- **`management.endpoints`**: Endpoints de monitoramento habilitados

## 📁 Estrutura do Projeto

```
3mbank-config-server/
├── 📄 pom.xml                           # Dependências Maven
├── 📖 README.md                         # Documentação do projeto
├── 🔧 .env                             # Variáveis de ambiente (opcional)
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/
│   │   │   └── 📁 com/threembank/configserver/
│   │   │       ├── ☕ Application.java          # Classe principal
│   │   │       └── 🔒 SecurityConfig.java      # Configuração de segurança
│   │   └── 📁 resources/
│   │       ├── ⚡ bootstrap.yaml               # Configuração de bootstrap
│   │       └── ⚙️ application.yml              # Configuração da aplicação
│   └── 📁 test/
└── 📁 target/                          # Arquivos compilados
```

### Repositório de Configurações

```
3mbank-infra/config-server-repo/
├── 🌐 application.yml                   # Configurações globais
├── 🚪 gateway.yml                       # Configurações do Gateway
├── 👤 account-service.yml              # Configurações do Account Service
├── 🔐 auth-service.yml                 # Configurações do Auth Service
└── 💰 transaction-service.yml          # Configurações do Transaction Service
```

---
## 🚀 Como Executar

### Pré-requisitos

1. **☕ Java 24+**
2. **📦 Maven 3.6+**
3. **🔐 HashiCorp Vault** rodando em `3mbank.vault.com:8200`
4. **📡 Consul** rodando em `3mbank.consul.com:80`
5. **📂 Repositório de configurações** em `/3mbank-infra/config-server-repo`

### Passos para Execução

#### 1. Clone e Navegue para o Diretório
```bash
cd E:\development\3mbank\core\3mbank-config-server
```

#### 2. Compile o Projeto
```bash
mvn clean compile
```

#### 3. Execute a Aplicação
```bash
# Opção 1: Via Maven
mvn spring-boot:run

# Opção 2: Via JAR
mvn clean package
java -jar target/config-server-1.0.0-SNAPSHOT.jar
```

#### 4. Verificar se está Funcionando
```bash
# Verificar saúde do serviço
curl http://localhost:8888/actuator/health

# Testar configuração
curl http://localhost:8888/application/default
```

#### 5. Acessar Configurações via Browser

Abra no navegador:

- [Config Server](http://localhost:8888/actuator/health)
- [Vault Status](http://localhost:8888/actuator/vault)
- [Git Configurations](http://localhost:8888/application/default)

### Variáveis de Ambiente (Opcionais)

```bash
export SERVER_PORT=8888
export CONSUL_DISCOVERY_HOST=3mbank.consul.com
export CONSUL_DISCOVERY_PORT=80
export VAULT_HOST=3mbank.vault.com
export VAULT_PORT=8200
```

## 🌐 Endpoints

### Configuration Endpoints

| Endpoint | Método | Descrição | Exemplo |
|----------|--------|-----------|---------|
| `/{application}/{profile}` | GET | Configuração para aplicação e perfil específicos | `/gateway/dev` |
| `/{application}/{profile}/{label}` | GET | Configuração com label específico | `/gateway/dev/master` |
| `/{application}-{profile}.yml` | GET | Configuração em formato YAML | `/gateway-dev.yml` |
| `/{application}-{profile}.json` | GET | Configuração em formato JSON | `/gateway-dev.json` |
| `/{application}-{profile}.properties` | GET | Configuração em formato Properties | `/gateway-dev.properties` |

### Management Endpoints

| Endpoint | Método | Descrição |
|----------|--------|-----------|
| `/actuator/health` | GET | Status de saúde do serviço |
| `/actuator/info` | GET | Informações da aplicação |
| `/actuator/vault` | GET | Status da conexão com Vault |
| `/actuator/refresh` | POST | Recarrega configurações |

### Exemplos de Uso

```bash
# Obter configuração do gateway para perfil development
curl http://localhost:8888/gateway/development

# Obter configuração em formato YAML
curl http://localhost:8888/gateway-development.yml

# Verificar saúde do serviço
curl http://localhost:8888/actuator/health

# Recarregar configurações
curl -X POST http://localhost:8888/actuator/refresh
```

## 📊 Monitoramento

### Health Checks

O Config Server expõe endpoints de saúde que verificam:

- ✅ **Application Status**: Status geral da aplicação
- ✅ **Vault Connectivity**: Conectividade com HashiCorp Vault
- ✅ **Git Repository**: Acesso ao repositório de configurações
- ✅ **Consul Registration**: Status do registro no Consul

### Métricas

Métricas disponíveis via Actuator:
- **Request count**: Número de requisições por endpoint
- **Response time**: Tempo de resposta das requisições
- **Error rate**: Taxa de erro das requisições
- **JVM metrics**: Uso de memória, garbage collection, threads

### Logs

Configuração de logs estruturados para observabilidade:

```yaml
logging:
  level:
    org.springframework.cloud.config: DEBUG
    org.springframework.cloud.vault: INFO
    org.springframework.cloud.consul: INFO
  pattern:
    console: "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
```

## 🔍 Troubleshooting

### ❌ Problemas Comuns

#### 1. Erro de Conexão com Vault

**Sintoma:** `VaultException: Vault is down or unsealed`

**Soluções:**
```bash
# Verificar se Vault está rodando
curl http://3mbank.vault.com:8200/v1/sys/health

# Verificar logs do Config Server
tail -f logs/config-server.log

# Verificar token de autenticação
echo $VAULT_TOKEN
```

#### 2. Repositório Git Não Encontrado

**Sintoma:** `NoSuchFileException: config-server-repo`

**Soluções:**
- ✅ Verificar se o diretório `/3mbank-infra/config-server-repo` existe
- ✅ Verificar permissões de leitura do diretório
- ✅ Verificar se o caminho está correto no `application.yml`
- ✅ Criar o diretório se não existir:
  ```bash
  mkdir -p /3mbank-infra/config-server-repo
  ```

#### 3. Falha no Registro no Consul

**Sintoma:** `ConsulException: Connection refused`

**Soluções:**
```bash
# Verificar se Consul está rodando
curl http://3mbank.consul.com:80/v1/status/leader

# Verificar conectividade de rede
ping 3mbank.consul.com

# Verificar logs do Consul
docker logs consul
```

#### 4. Configuração Não Atualizada

**Sintoma:** Microserviços não recebem configurações atualizadas

**Soluções:**
```bash
# Forçar refresh do Config Server
curl -X POST http://localhost:8888/actuator/refresh

# Forçar refresh do microserviço cliente
curl -X POST http://microservice:port/actuator/refresh

# Verificar se o arquivo foi atualizado no Git
git log --oneline -5
```

### 🔧 Script de Diagnóstico

```bash
#!/bin/bash

echo "=== 3MBank Config Server Health Check ==="

# 1. Verificar se Config Server está rodando
echo "1. ✅ Checking Config Server..."
if curl -f http://localhost:8888/actuator/health >/dev/null 2>&1; then
    echo "   ✅ Config Server is running"
else
    echo "   ❌ Config Server is down"
fi

# 2. Verificar Vault
echo "2. 🔐 Checking Vault connectivity..."
if curl -f http://3mbank.vault.com:8200/v1/sys/health >/dev/null 2>&1; then
    echo "   ✅ Vault is reachable"
else
    echo "   ❌ Vault is unreachable"
fi

# 3. Verificar Consul
echo "3. 📡 Checking Consul connectivity..."
if curl -f http://3mbank.consul.com:80/v1/status/leader >/dev/null 2>&1; then
    echo "   ✅ Consul is reachable"
else
    echo "   ❌ Consul is unreachable"
fi

# 4. Verificar configuração de exemplo
echo "4. ⚙️ Testing configuration retrieval..."
if curl -f http://localhost:8888/application/default >/dev/null 2>&1; then
    echo "   ✅ Configuration retrieval working"
else
    echo "   ❌ Configuration retrieval failed"
fi

# 5. Verificar diretório de configurações
echo "5. 📂 Checking config repository..."
if [ -d "/3mbank-infra/config-server-repo" ]; then
    echo "   ✅ Config repository directory exists"
    echo "   📄 Files found: $(ls -1 /3mbank-infra/config-server-repo | wc -l)"
else
    echo "   ❌ Config repository directory not found"
fi

echo "=== Health Check Complete ==="
```

## 🔒 Segurança

### Autenticação com Vault

O Config Server usa autenticação por token com o Vault:

```yaml
spring:
  cloud:
    vault:
      authentication: TOKEN
      token: myroot  # ⚠️ Em produção, usar variável de ambiente
```

**⚠️ Importante:** Em produção, o token deve ser fornecido via variável de ambiente ou método de autenticação mais seguro como AppRole.

### Configurações Sensíveis

Informações sensíveis devem ser armazenadas no Vault:

- 🔑 **Database passwords**
- 🗝️ **API keys**
- 🔐 **JWT secrets**
- 🛡️ **Encryption keys**

### Network Security

- 🔥 Configurar firewall para restringir acesso ao Config Server
- 🔒 Usar HTTPS em produção
- 👤 Implementar autenticação/autorização se necessário

## 📈 Performance

### Otimizações

1. **💾 Cache de Configurações**: Configurações são cacheadas para melhor performance
2. **🔗 Connection Pooling**: Pool de conexões para Vault e Git
3. **⚡ Async Processing**: Processamento assíncrono quando possível

### Monitoring de Performance

```yaml
management:
  metrics:
    export:
      prometheus:
        enabled: true
  endpoint:
    metrics:
      enabled: true
```

## 🚀 Deployment

### 🐳 Docker

```dockerfile
FROM openjdk:24-jre-slim

COPY target/config-server-1.0.0-SNAPSHOT.jar app.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "/app.jar"]
```
---

## 🤝 Contribuição

Para contribuir com o projeto:

1. 🍴 Fork o repositório
2. 🌿 Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. 💾 Commit suas mudanças (`git commit -am 'Adiciona nova feature'`)
4. 📤 Push para a branch (`git push origin feature/nova-feature`)
5. 🔀 Abra um Pull Request

## 📞 Suporte

Para suporte técnico ou dúvidas:

- **📧 Email**: devops@3mbank.com
- **💬 Slack**: #3mbank-config-server
- **📖 Documentação**: [Config Server Wiki](http://wiki.3mbank.com/config-server)

---

**3MBank Config Server** - Configuração centralizada e segura para microserviços 🏦🔧

# 🔐 3m Auth Service

## Visão Geral

O **3m Auth Service** é um microsserviço robusto e seguro, desenvolvido com **Spring Boot**, projetado para gerenciar a **autenticação e autorização de usuários e aplicações cliente**. Ele utiliza **JWT (JSON Web Tokens)** para autenticação e segue o padrão **OAuth2** para autorização, garantindo segurança em ambientes com múltiplos microsserviços.

---

## ⚙️ Funcionalidades Principais

- ✅ **Cadastro de Usuários** com validação de senha forte.
- 🔐 **Login com JWT** (accessToken e refreshToken).
- 🔁 **Atualização de Token** com refresh token.
- 🚪 **Logout** com invalidação de tokens.
- 🧾 **Cadastro de Clientes OAuth2** (com escopos) — requer papel ADMIN.
- 🤝 **Autenticação de Clientes OAuth2** via `client_credentials`.
- 🔒 **Política de Senha Forte** com validações customizadas.
- 🛡️ **RBAC** (Controle de Acesso Baseado em Papéis).
- 📚 **Controle de Escopos** para clientes OAuth2.
- 📄 **Documentação da API com Swagger/OpenAPI**.
- 🧰 **Tratamento Global de Exceções**.
- 🧩 **Configuração Flexível** (JWT, CORS, SSL, banco de dados).

---

## 🛠️ Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Security (OAuth2, JWT)**
- **Spring Data JPA + Hibernate**
- **MapStruct**
- **Lombok**
- **Jakarta Bean Validation**
- **Nimbus JOSE + JWT**
- **H2 / PostgreSQL / MySQL**
- **Maven**

---

## 🧾 Estrutura do Projeto
``` yaml
com.threembank
├── application # Casos de uso e lógica da aplicação
│ ├── service
│ ├── mapper
│ └── exception
├── domain # Entidades, VOs, repositórios e validações
│ ├── entity
│ ├── valueobject
│ ├── repository
│ ├── validation
│ └── exception
├── infrastructure # Implementações e configurações
│ ├── persistence
│ ├── security
│ ├── config
│ ├── jwt
│ └── user
├── interfaces # Camada de entrada (API REST)
│ ├── controller
│ ├── dto
│ ├── exception
│ └── valueobject
└── shared # Componentes reutilizáveis
├── exception
└── message
```

---

## 🔗 Endpoints Principais

### 🔐 Autenticação de Usuário

| Método | Endpoint         | Descrição                        |
|--------|------------------|----------------------------------|
| POST   | `/login`         | Login de usuário (JWT)           |
| POST   | `/refresh`       | Refresh de accessToken           |
| PUT    | `/logout`        | Logout com invalidação de token  |

### 👤 Cadastro

| Método | Endpoint            | Descrição                       |
|--------|---------------------|---------------------------------|
| POST   | `/register/user`    | Cadastro de novo usuário        |
| POST   | `/register/client`  | Cadastro de novo cliente OAuth2 |

### 🤝 OAuth2 - Autenticação de Cliente

| Método | Endpoint         | Descrição                                     |
|--------|------------------|-----------------------------------------------|
| POST   | `/login/client`  | Geração de token via client_credentials grant |

---

## 📃 Documentação da API

- Swagger UI: [`http://localhost:8080/swagger-ui/index.html`](http://localhost:8080/swagger-ui/index.html)
- OpenAPI JSON: [`http://localhost:8080/v3/api-docs`](http://localhost:8080/v3/api-docs)

---

## 🔧 Configuração

### JWT (RSA)

```properties
services.security.jwt.token.private-key=classpath:jwt/private.pem
services.security.jwt.token.public-key=classpath:jwt/public.pem
services.security.jwt.token.expiration-at=1h
services.security.jwt.token.expiration-rt=24h
CORS
properties
Copiar
Editar
services.security.cors.allowed-origins=http://localhost:3000
services.security.cors.allowed-methods=GET,POST,PUT,DELETE
services.security.cors.allowed-headers=Authorization,Content-Type
services.security.cors.allow-credentials=true
services.security.cors.max-age=3600
SSL
properties
Copiar
Editar
services.security.ssl.enable=true
services.security.ssl.type=PKCS12
services.security.ssl.store=classpath:keystore.p12
services.security.ssl.password=changeit
services.security.ssl.alias=3m-auth-service
Banco de Dados
properties
Copiar
Editar
spring.datasource.url=jdbc:h2:mem:authdb
spring.datasource.username=sa
spring.datasource.password=
📚 Entidades de Domínio
🧑‍💼 User
id, email, password, firstName, lastName, roles, timestamps

🔑 Token
token, userId, expiresAt, revoked

🧾 Client
clientId, clientSecret, scopes

✅ Validações
Validações com @NotBlank, @Email, etc.

Senha com @StrongPassword

Validações programáticas via Validate

🔐 Segurança
JWT assinado com RSA

Autorização por papéis (RBAC) e escopos

Senhas e segredos criptografados (bcrypt)

CSRF desabilitado (stateless APIs)

HTTPS habilitável

▶️ Como Executar
1. Pré-Requisitos
Java 17+

Maven

Banco de dados (H2, PostgreSQL, etc.)

2. Configurar
bash
Copiar
Editar
git clone https://seurepositorio.git
cd 3m-auth-service
Edite application.yml ou application.properties com suas configurações.

3. Build do Projeto
bash
Copiar
Editar
mvn clean install
4. Executar
bash
Copiar
Editar
java -jar target/3m-auth-service-*.jar
5. Acessar
API: http://localhost:8080

Swagger: http://localhost:8080/swagger-ui/index.html

🧪 Testes
Execute os testes com:

bash
Copiar
Editar
mvn test
📄 Licença
Este projeto é licenciado sob os termos definidos pela organização 3mBank. Consulte o arquivo LICENSE para mais detalhes.

💬 Contato
Para dúvidas ou sugestões:

📧 Email: suporte@3mbank.com.br

🌐 Site: www.3mbank.com.br

Feito com ❤️ pela equipe 3mBank.

yaml
Copiar
Editar

---

Se quiser que eu converta isso para HTML estilizado, adicione imagens ou badges do GitHub (como build status, cobertura, licença etc.), posso personalizar mais ainda. Deseja isso?









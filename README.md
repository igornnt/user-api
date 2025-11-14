# 🚀 User API - Desafio Progic

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen) ![Java](https://img.shields.io/badge/Java-21-blue) ![H2 Database](https://img.shields.io/badge/Database-H2-yellow) ![Status](https://img.shields.io/badge/status-Em%20Desenvolvimento-orange)

Uma API REST para gerenciamento de usuários, com autenticação básica, paginação e ordenação.

---

## 🔧 Tecnologias

* Java 21
* Spring Boot 3.5.7
* Spring Security
* H2 Database (em memória)
* Maven
* JUnit 5 / Mockito

---

## 🏃‍♂️ Como Rodar

```bash
# Clone o repositório
git clone https://github.com/igornnt/user-api.git
cd user-api

# Compile e rode
./mvnw spring-boot:run
```

A API estará disponível em: `http://localhost:8080/api/users`

---

## 👤 Usuários de Teste

| Usuário | Senha      | Perfil                    |
| ------- | ---------- | ------------------------- |
| admin   | admin123   | Admin (Acesso total)     |
| usuario | user123 | User (Somente leitura) |

---

## 📚 Endpoints

### 1️⃣ Listar usuários (com paginação e ordenação)

```
GET /api/users?page=0&size=10&sort=id&direction=asc
Authorization: Basic <base64(usuario:senha)>
```

**Exemplo cURL:**

```bash
curl -u usuario:usuario123 "http://localhost:8080/api/users?page=0&size=10&sort=id&direction=asc"
```

---

### 2️⃣ Buscar usuário por ID

```
GET /api/users/{id}
```

**Exemplo cURL:**

```bash
curl -u usuario:usuario123 "http://localhost:8080/api/users/1"
```

---

### 3️⃣ Criar usuário

```
POST /api/users
Content-Type: application/json
```

**Body JSON:**

```json
{
  "nome": "João Silva",
  "email": "joao.silva@example.com"
}
```

**Exemplo cURL:**

```bash
curl -u admin:admin123 -X POST "http://localhost:8080/api/users" \
-H "Content-Type: application/json" \
-d '{"nome":"João Silva","email":"joao.silva@example.com"}'
```

---

### 4️⃣ Atualizar usuário

```
PUT /api/users/{id}
Content-Type: application/json
```

**Body JSON:**

```json
{
  "nome": "João Silva Atualizado",
  "email": "joao.silva.atualizado@example.com"
}
```

**Exemplo cURL:**

```bash
curl -u admin:admin123 -X PUT "http://localhost:8080/api/users/1" \
-H "Content-Type: application/json" \
-d '{"nome":"João Silva Atualizado","email":"joao.silva.atualizado@example.com"}'
```

---

### 5️⃣ Deletar usuário

```
DELETE /api/users/{id}
```

**Exemplo cURL:**

```bash
curl -u admin:admin123 -X DELETE "http://localhost:8080/api/users/1"
```

---

## ⚡ Validação e Erros

* `nome` é obrigatório
* `email` é obrigatório e deve ser válido
* Respostas de erro retornam **HTTP 400 ou 404** com mensagem detalhada

---

## 🧪 Testes

Todos os endpoints possuem **testes de integração** com MockMvc e JUnit 5.

Rodar testes:

```bash
./mvnw test
```

---

## 💡 Boas práticas

* JSON retorna campos em português: `nome`, `email`, `dataCriacao`
* API segue princípios RESTful e idempotência para métodos GET e DELETE
* Autenticação básica para acesso aos endpoints
* Paginação e ordenação para listagem de usuários

# API Alunos

API REST para gerenciamento de alunos, desenvolvida com **Java 25** e **Spring Boot 4.1.1**.

O projeto foi desenvolvido com foco em **arquitetura em camadas, separação de responsabilidades, DTOs, validação de dados, tratamento global de exceções, injeção de dependências e testes automatizados**.

---

## Sobre o projeto

A **API Alunos** fornece endpoints REST para gerenciamento do cadastro acadêmico de alunos.

A aplicação permite:

* Cadastrar alunos;
* Consultar todos os alunos;
* Consultar um aluno por ID;
* Atualizar dados cadastrais;
* Alterar senha;
* Remover alunos;
* Validar dados de entrada;
* Tratar exceções de forma centralizada;
* Testar as camadas de Controller e Service;
* Validar os endpoints através de Postman/Newman;
* Consultar e testar a API através do Swagger UI.

---

## Tecnologias utilizadas

| Tecnologia            | Versão / Utilização                    |
| --------------------- | -------------------------------------- |
| **Java**              | 25                                     |
| **Spring Boot**       | 4.1.1                                  |
| **Spring MVC / Web**  | Desenvolvimento da API REST            |
| **Spring Validation** | Validação dos dados de entrada         |
| **Springdoc OpenAPI** | Documentação da API / Swagger UI       |
| **JUnit**             | Testes automatizados                   |
| **Mockito**           | Mock e isolamento de dependências      |
| **Maven**             | Build e gerenciamento de dependências  |
| **Postman**           | Testes e validação dos endpoints       |
| **Newman**            | Execução dos testes do Postman via CLI |

---

# Arquitetura

O projeto utiliza uma arquitetura baseada em camadas, com responsabilidades separadas entre **Controller, Service, DTO, Model e Exception**.

```text
Client
  │
  ▼
Controller
  │
  ▼
Service
  │
  ├── Model
  │
  └── Regras de negócio
  │
  ▼
Response
```

### Responsabilidades

**Controller**

Responsável por receber as requisições HTTP, validar os dados de entrada e retornar as respostas da API.

**Service**

Responsável pelas regras de negócio e pelo processamento das operações relacionadas aos alunos.

**DTO**

Responsável pela representação dos dados utilizados nas requisições e respostas da API.

**Model**

Representa a entidade principal utilizada pela aplicação.

**Exception**

Centraliza o tratamento das exceções e a construção das respostas de erro.

---

# Estrutura do projeto

```text
src
├── main
│   └── java
│       └── com.botelho.loester.api_alunos
│           ├── controller
│           │   └── AlunoController.java
│           │
│           ├── dto
│           │   ├── request
│           │   │   ├── AlunoRequest.java
│           │   │   └── AlteraSenhaRequest.java
│           │   │
│           │   └── response
│           │       ├── AlunoResponse.java
│           │       └── ErroResponse.java
│           │
│           ├── exception
│           │   ├── RegistroNaoEncontradoException.java
│           │   └── GlobalExceptionHandler.java
│           │
│           ├── model
│           │   └── Aluno.java
│           │
│           ├── service
│           │   └── AlunoService.java
│           │
│           └── ApiAlunosApplication.java
│
└── test
    └── java
        └── com.botelho.loester.api_alunos
            ├── controller
            │   └── AlunoControllerTest.java
            │
            └── service
                └── AlunoServiceTest.java
```

---

# Endpoints

A API disponibiliza operações de **CRUD** e gerenciamento de senha dos alunos.

| Método   | Endpoint             | Descrição                  | Status           |
| -------- | -------------------- | -------------------------- | ---------------- |
| `GET`    | `/alunos`            | Lista todos os alunos      | `200 OK`         |
| `GET`    | `/alunos/{id}`       | Busca um aluno pelo ID     | `200 OK`         |
| `POST`   | `/alunos`            | Cadastra um novo aluno     | `201 Created`    |
| `PUT`    | `/alunos/{id}`       | Atualiza os dados do aluno | `200 OK`         |
| `PATCH`  | `/alunos/{id}/senha` | Altera a senha do aluno    | `204 No Content` |
| `DELETE` | `/alunos/{id}`       | Remove um aluno            | `204 No Content` |

---

# Exemplos de utilização

## 1. Listar alunos

```http
GET /alunos
```

Resposta:

```json
[
  {
    "id": 1,
    "nome": "João Silva"
  }
]
```

---

## 2. Buscar aluno por ID

```http
GET /alunos/1
```

Resposta:

```json
{
  "id": 1,
  "nome": "João Silva"
}
```

---

## 3. Cadastrar aluno

```http
POST /alunos
Content-Type: application/json
```

Exemplo de requisição:

```json
{
  "nome": "João Silva"
}
```

Resposta:

```http
201 Created
```

---

## 4. Atualizar aluno

```http
PUT /alunos/1
Content-Type: application/json
```

Exemplo:

```json
{
  "nome": "João da Silva"
}
```

Resposta:

```http
200 OK
```

---

## 5. Alterar senha

```http
PATCH /alunos/1/senha
Content-Type: application/json
```

A alteração de senha utiliza o DTO `AlteraSenhaRequest`.

Resposta:

```http
204 No Content
```

---

## 6. Remover aluno

```http
DELETE /alunos/1
```

Resposta:

```http
204 No Content
```

---

# Tratamento de exceções

A aplicação possui uma exceção customizada:

```text
RegistroNaoEncontradoException
```

Ela é utilizada quando um aluno solicitado não é encontrado.

O tratamento das exceções é centralizado através do:

```text
GlobalExceptionHandler
```

Essa abordagem mantém o tratamento de erros separado das regras do Controller.

---

# Swagger / OpenAPI

A API possui documentação interativa utilizando **Springdoc OpenAPI** e **Swagger UI**.

Com a aplicação em execução, acesse:

### Swagger UI

```text
http://localhost:8080/swagger-ui/index.html
```

### OpenAPI JSON

```text
http://localhost:8080/v3/api-docs
```

O Swagger UI permite:

* Visualizar os endpoints;
* Consultar os métodos HTTP;
* Visualizar parâmetros;
* Consultar os modelos de Request e Response;
* Executar requisições diretamente pelo navegador;
* Visualizar as respostas da API.

---

# Testes automatizados

O projeto possui testes automatizados separados por responsabilidade.

### `AlunoServiceTest`

Testa diretamente as regras de negócio da camada Service.

```text
AlunoServiceTest
      │
      ├── Regras de negócio
      ├── Operações com alunos
      ├── Validação de cenários
      └── Exceções
```

### `AlunoControllerTest`

Testa a camada HTTP utilizando recursos do Spring MVC e Mockito.

```text
Request
   │
   ▼
Controller
   │
   ▼
Service Mock
   │
   ▼
Response
```

Entre os recursos utilizados estão:

* `@WebMvcTest`
* `MockMvc`
* `@MockitoBean`
* `ObjectMapper`

Os testes validam o comportamento dos endpoints e seus respectivos códigos HTTP.

---

# Postman / Newman

A API também possui testes automatizados utilizando **Postman** e **Newman**.

O Newman permite executar uma coleção do Postman diretamente através da linha de comando, facilitando a automação dos testes da API.

Exemplo:

```bash
newman run api-alunos.postman_collection.json
```

---

# Como executar o projeto

## Pré-requisitos

Antes de executar o projeto, certifique-se de possuir:

* **Java 25**
* **Maven 3.9+**
* **Node.js**, caso queira executar os testes com Newman

---

## 1. Clonar o projeto

```bash
git clone https://github.com/LoesterBotelho/api-alunos.git
```

---

## 2. Acessar o diretório

```bash
cd api-alunos
```

---

## 3. Executar a aplicação

```bash
mvn spring-boot:run
```

A API estará disponível em:

```text
http://localhost:8080
```

---

# Comandos Maven

| Comando               | Descrição                                             |
| --------------------- | ----------------------------------------------------- |
| `mvn clean install`   | Limpa, compila, executa os testes e instala o projeto |
| `mvn clean package`   | Limpa, compila, executa os testes e gera o `.jar`     |
| `mvn clean test`      | Limpa o projeto e executa os testes                   |
| `mvn test`            | Executa os testes                                     |
| `mvn spring-boot:run` | Inicia a aplicação                                    |
| `mvn clean`           | Remove os arquivos gerados pelo Maven                 |

---

# Gerar o projeto

Para realizar uma compilação completa:

```bash
mvn clean install
```

Para compilar, executar os testes e gerar o `.jar`:

```bash
mvn clean package
```

O arquivo gerado estará no diretório:

```text
target/
```

---

# Executar os testes

Para executar todos os testes:

```bash
mvn clean test
```

Ou:

```bash
mvn test
```

O Maven exibirá o resultado dos testes no console.

Exemplo:

```text
Tests run: X, Failures: 0, Errors: 0, Skipped: 0
```

---

# Objetivos do projeto

O projeto foi desenvolvido para praticar e demonstrar conhecimentos em:

* Java 25;
* Spring Boot 4.1.1;
* Spring MVC;
* APIs REST;
* Controllers;
* Services;
* DTOs;
* Model;
* Injeção de dependências;
* Separação de responsabilidades;
* Validação de dados;
* Tratamento global de exceções;
* Exceções customizadas;
* JUnit;
* Mockito;
* Testes unitários;
* Testes de Controller;
* `MockMvc`;
* `@WebMvcTest`;
* `@MockitoBean`;
* Springdoc OpenAPI;
* Swagger UI;
* Postman;
* Newman;
* Maven.

---

# Status do projeto

**Em desenvolvimento / projeto de estudos**

Novas funcionalidades, melhorias na arquitetura e novos cenários de testes poderão ser adicionados futuramente.

---

## Autor

**Loester Botelho**

Full-Stack Developer | Java | Spring | Angular

# health-manager-service
Sistema para gerenciamento de pacientes 

## Tecnologias Utilizadas
- **Java 17** 
- **Spring Boot 3.4**

## Nomenclaturas
No projeto, as nomenclaturas de classes, variáveis e métodos são todas realizadas em inglês, 
como por exemplo: **Doctor**, **Nurse**, **Patient**

## Arquitetura
Este projeto usa a **Clean Architecture** para organizacao de código e ajudar a separar responsabilidades 
e não depender de uma tecnologia específica, dividindo tudo em camadas bem definidas.

### Estrutura do Projeto
```plaintext
src/main/java/com/healthmanagerservice/health-manager-service
├── application
│   ├── service
│   │   ├── PatientService.java
│   │   ├── EnfermeiroService.java
│   │   ├── DoctorService.java
│   │   └── AuthService.java
│   └── converter
│       ├── PatientMapper.java
│       ├── EnfermeiroMapper.java
│       └── DoctorMapper.java
├── config
│   ├── SecurityConfig.java
│   ├── OpenApiConfig.java
│   └── AppConfig.java
├── domain
│   ├── entities
│   │   ├── Patient.java
│   │   ├── Enfermeiro.java
│   │   └── Doctor.java
│   ├── enums
│   │   └── UF.java
│   ├── exceptions
│   │   ├── ResourceNotFoundException.java
│   │   ├── ValidationException.java
│   │   └── UnauthorizedAccessException.java
│   └── interfaces
│       ├── PatientRepository.java
│       ├── EnfermeiroRepository.java
│       └── DoctorRepository.java
├── infrastructure
│   ├── repository
│   │   ├── PatientRepositoryImpl.java
│   │   ├── EnfermeiroRepositoryImpl.java
│   │   └── DoctorRepositoryImpl.java
│   ├── external
│   │   └── IBGEService.java
│   └── util
│       ├── CPFEncryptionUtil.java
│       └── PasswordEncoderUtil.java
└── presentation 
    ├── controllers
    │   ├── PatientController.java
    │   ├── EnfermeiroController.java
    │   ├── DoctorController.java
    │   ├── AuthController.java
    ├── dto
    │   ├── request
    │   │   ├── PatientRequestDTO.java
    │   │   ├── EnfermeiroRequestDTO.java
    │   │   ├── DoctorRequestDTO.java
    │   │   └── AuthRequestDTO.java
    │   └── response
    │       ├── PatientResponseDTO.java
    │       ├── EnfermeiroResponseDTO.java
    │       ├── DoctorResponseDTO.java
    │       └── AuthResponseDTO.java
```

## Como Rodar o Projeto
1. Ter o Java 17 e p gradle na sua máquina.
2. Clone o repositório:

   ```bash
   git clone https://github.com/lucascvasconcelos/health-manager-service.git
   ```

3. Navegue até o diretório do projeto:

   ```bash
   cd health-manager-service
   ```

4. builde o projeto:

   ```bash
   ./gradlew clean build
   ```

5. Execute o projeto com o Gradle:

   ```bash
   ./gradlew bootRun
   ```

6. aplicação estará disponível em `http://localhost:8080`.

## Setup Inicial do Banco de Dados
O projeto tem dois scripts SQL localizados em `src/main/resources/` para configurar o banco de dados e serao executados automaticamente ao ser iniciado:

- **schema.sql**: Criacao das tabelas.

- **data.sql**: Insere usuário Administrador.
As credenciais desse usuário são:
     - **Username:** `admin`
     - **Senha:** `youx-group-challenger`

É importante usar essas credenciais para acessar o sistema inicialmente e configurar usuários adicionais conforme necessário.

## Autenticação
Gerar jwt:
```bash
curl --location 'http://localhost:8080/auth/login' \
--header 'Content-Type: application/json' \
--data '{
    "username": "admin",
    "password": "youx-group-challenger"
}'
```

### Nota:
Após realizar o login, utilizar o token nas próximas requisicoes

## Usuário

### Listar Usuários

```bash
curl --location 'http://localhost:8080/users' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer seuTokenJWT'
```

### Criar Usuário

- **ROLE_ADMIN**: Acesso total.
- **ROLE_NURSE**: Acesso aos pacientes.
- **ROLE_DOCTOR**: Acesso a usuarios e pacientes.

```bash
curl --location 'http://localhost:8080/users' \
--header 'Content-Type: application/json' \
--header 'Authorization:  Bearer seuTokenJWT'
--data '{
    "name": "enfermeiro",
    "cpf": "10278029404",
    "password": "123",
    "role": "ROLE_NURSE"
}'
```

## Pacientes

### Criar Paciente
```bash
curl --location 'http://localhost:8080/patients' \
--header 'Content-Type: application/json' \
--header 'Authorization:  Bearer seuTokenJWT' \
--data '{
  "name": "paciente 1",
  "cpf": "10278029403",
  "birthDate": "1980-01-01",
  "weight": 70.5,
  "height": 1.75,
  "state": "SP"
}'
```

### Listar Pacientes
```bash
curl --location 'http://localhost:8080/patients' \
--header 'Authorization:  Bearer seuTokenJWT'
```

### Recuperar pelo id
```bash
curl --location 'http://localhost:8080/patients/24' \
--header 'Authorization:  Bearer seuTokenJWT'
```

### Atualizar Paciente
```bash
curl --location --request PUT 'http://localhost:8080/patients/129' \
--header 'Content-Type: application/json' \
--header 'Authorization:  Bearer seuTokenJWT' \
--data '{
  "name": "Lucas update",
  "cpf": "12345678901",
  "birthDate": "1980-01-01",
  "weight": 75.0,
  "height": 1.80,
  "state": "RJ"
}'
```

### Deletar Paciente

```bash
curl --location --request DELETE 'http://localhost:8080/patients/161' \
--header 'Authorization:  Bearer seuTokenJWT'
```

## Importante
É necessário que todas as requisições para os endpoints `/users` E `/patients`,  incluam o token JWT no cabeçalho `Authorization` após fazer login. Este token é necessário para validar e autorizar a operação solicitada.

## Criptografia
Optei por utilizar algoritmo de criptografia AES  porque é bastante utilizado  em aplicações que  trabalham com  dados sensível e é bastante seguro.

## Links Úteis

- [Documentação do Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Documentação do Java 17](https://docs.oracle.com/en/java/javase/17/)
---


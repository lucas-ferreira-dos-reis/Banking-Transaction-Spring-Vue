[![en](https://img.shields.io/badge/lang-en-red.svg)](README.md)
[![pt-br](https://img.shields.io/badge/lang-pt--br-green.svg)](README.pt-BR.md)

_Leia isto em outros idiomas: [English](README.md)_

---

# 🏦 Banking Transaction

Sistema web full-stack para agendamento de transferências financeiras com **cálculo automático de taxas dinâmicas**.
Este projeto foi desenvolvido como parte de um desafio técnico para um processo seletivo, com o objetivo de demonstrar minhas habilidades em desenvolvimento de software, aplicação de regras de negócio e utilização de boas práticas de programação.

---

## 📌 Visão Geral do Projeto

A aplicação permite que os usuários agendem transferências financeiras informando a conta de origem, a conta de destino, o valor da transferência e a data agendada. O sistema calcula automaticamente a taxa aplicável com base na quantidade de dias entre a data do agendamento e a data da transferência.

## 🛠️ Tecnologias

### Backend

- **Java 11** & **Spring Boot 2.7.18** (o desafio exigia Java 11)
- **Spring Data JPA / Hibernate**
- **H2 Database** (um banco de dados em memória, como exigido pelo desafio)
- **Spring Validation**
- **JUnit 5 & Mockito**
- **OpenAPI / Swagger UI**

### Frontend

- **Vue.js 3**
- **Vite**
- **jQuery**
- **Bootstrap**

---

## 📐 Arquitetura

1. **Arquitetura em Camadas (Backend):**
   - A aplicação segue um padrão arquitetural em camadas (Controller, Service, Repository, Model), garantindo baixo acoplamento e alta coesão.
   - As regras de negócio complexas, como a validação de contas de origem e destino idênticas e o cálculo de taxas com base nos intervalos de dias, são isoladas na camada Service. Isso permite testes unitários eficientes e impede que chamadas diretas à API contornem a lógica de negócio.
2. **Tratamento Global de Exceções:**
   - Uso do @ControllerAdvice para tratar exceções de validação (MethodArgumentNotValidException) e de negócio (BusinessException), retornando mensagens de erro amigáveis e códigos de status HTTP padronizados (400 Bad Request).
3. **Arquitetura Modular (Frontend):**
   - O frontend em Vue.js utiliza Single File Components (SFCs) organizados em diretórios modulares (components/, services/), separando a camada de comunicação com a API (transferService.js, utilizando jQuery) dos componentes visuais.
4. **Testes Unitários:**
   - A suíte de testes cobre os principais cenários de cálculo de taxas e validações de negócio utilizando JUnit e Mockito, garantindo a confiabilidade do software.

---

## 🚀 Executando o Projeto

### Pré-requisitos

- **Java Development Kit (JDK) 11**
- **Node.js**
- **Maven**

---

### 1️⃣ Backend (Spring Boot)

1. Acesse o diretório do backend:

```bash
cd backend
```

2. Execute a aplicação utilizando o Maven Wrapper:

- Linux / macOS (Terminal):

```bash
./mvnw spring-boot:run
```

- Windows (PowerShell):

```bash
.\mvnw spring-boot:run
```

- Windows (Command Prompt):

```bash
mvnw spring-boot:run
```

3. A API estará disponível em:

```
http://localhost:8080
```

4. Para acessar o Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

### 2️⃣ Frontend (Vue.js)

1. Abra um novo terminal e acesse o diretório do frontend:

```bash
cd frontend
```

2. Instale as dependências:

```bash
npm install
```

3. Inicie o servidor de desenvolvimento:

```bash
npm run dev
```

4. Acesse a aplicação pelo navegador:

```
http://localhost:5173
```

## 🧪 Executando os Testes

A partir do diretório do backend, execute:

```bash
./mvnw test
```

## 📄 Licença

Este projeto foi desenvolvido como parte de um desafio técnico Full Stack para um processo seletivo.

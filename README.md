# 🎮 GamefinderApi

O **GamefinderApi** é o backend do projeto **Game Finder**, desenvolvido em **Java + Spring Boot** como parte de uma atividade acadêmica da disciplina de **Java Advanced**.

A função desta API é receber o nome de um jogo enviado pelo frontend, consultar a **IGDB**, analisar os dados retornados e gerar uma recomendação para o usuário.

---

## 📘 Sobre o projeto

Este repositório contém apenas a parte **backend** da aplicação.

O sistema completo é dividido em duas partes:

- **Frontend (Angular):** responsável pela interface com o usuário
- **Backend (Java + Spring Boot):** responsável por consultar a API externa, aplicar a lógica de recomendação e devolver o resultado

O backend é a parte central da aplicação, pois é ele quem realiza a integração com a API externa e processa as regras de negócio.

---

## 🎯 Objetivo da aplicação

A API foi desenvolvida para:

1. Receber o nome de um jogo via requisição HTTP
2. Consultar a **IGDB** para buscar dados sobre esse jogo
3. Interpretar os dados retornados
4. Aplicar a lógica de recomendação
5. Retornar uma resposta estruturada em JSON para o frontend

---

## 🛠️ Tecnologias utilizadas

- Java
- Spring Boot
- Gradle
- REST API
- IGDB API
- Twitch Authentication

---

## 🔄 Como a API funciona

O fluxo do backend é o seguinte:

1. O frontend envia o nome de um jogo para a API
2. O backend gera um token de acesso da Twitch
3. O backend consulta a IGDB usando esse token
4. O backend extrai os dados principais do jogo
5. O backend aplica a regra de recomendação
6. O backend devolve um JSON com:
   - nome do jogo
   - nota
   - quantidade de avaliações
   - ano de lançamento
   - recomendação final

---

## ⭐ Regra de recomendação

A recomendação é baseada na análise dos dados retornados pela IGDB.

### ⭐⭐⭐ Altamente recomendado
Quando o jogo possui:
- nota **4.5 ou mais**
- muitas avaliações
- lançamento recente

### ⭐⭐ Vale a pena
Quando o jogo possui:
- nota **entre 3.5 e 4.4**

### ⭐ Melhor ver um filme
Quando o jogo possui:
- nota **menor que 3.5**

### ⚠️ Dados insuficientes para recomendar
Quando a API não retorna informações suficientes, como nota ou quantidade de avaliações, o sistema informa que não há dados suficientes para recomendar.

---
## 🔧 Setup do projeto

O sistema **Game Finder** é composto por dois repositórios:

- **Frontend (Angular):** https://github.com/annabonfim/gamefinder-web
- **Backend (Java + Spring Boot):** este repositório

Para que a aplicação funcione corretamente, é necessário configurar e executar os dois projetos.

---

## 📋 Pré-requisitos

Antes de rodar o backend, verifique se você tem instalado:

- Java 17 ou superior
- Gradle ou o Gradle Wrapper do projeto
- Client ID e Client Secret válidos para integração com a IGDB/Twitch
- Node.js e Angular CLI, caso também queira executar o frontend

---

## 🚀 Como executar o backend

### 1. Clone este repositório

```bash
git clone https://github.com/annabonfim/gamefinder-api.git
```

### 2. Acesse a pasta do projeto

```bash
cd gamefinder-api
```

### 3. Verifique a configuração da aplicação

As credenciais necessárias para integração com a **IGDB/Twitch** já estão configuradas no projeto.

Caso seja necessário alterar ou atualizar essas credenciais, verifique o arquivo de configuração da aplicação antes de executar o backend.


Essas credenciais são necessárias para gerar o token de autenticação da Twitch e permitir a consulta à IGDB.

### 4. Execute a aplicação

No macOS/Linux:

```bash
./gradlew bootRun
```

No Windows:

```bash
gradlew.bat bootRun
```

### 5. Verifique se a API está rodando

A aplicação deve iniciar localmente em:

```bash
http://localhost:8080
```

---

## 🌐 Como executar o frontend

O frontend está disponível em:

```bash
https://github.com/annabonfim/gamefinder-web
```

### Passos básicos para rodar o frontend

```bash
git clone https://github.com/annabonfim/gamefinder-web
cd gamefinder-web
npm install
ng serve
```

Depois, acesse:

```bash
http://localhost:4200
```

---

## 🔗 Integração entre frontend e backend

O frontend consome o backend localmente por meio do endpoint:

```bash
GET /games/recommend?name=nome-do-jogo
```

Exemplo:

```bash
http://localhost:8080/games/recommend?name=Hades
```

Para que a aplicação completa funcione:

- o **backend** deve estar rodando na porta `8080`
- o **frontend** deve estar rodando na porta `4200`

Se o backend não estiver ativo, o frontend não conseguirá buscar os dados dos jogos.

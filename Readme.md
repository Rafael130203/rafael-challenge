# 📦 Marketplace Webhook Challenge

Esse projeto simula a integração entre dois sistemas via **webhook**, bem parecido com o que acontece em marketplaces reais.

Temos duas APIs conversando entre si:

- **Marketplace API** → onde os pedidos acontecem  
- **Receiver API** → que recebe os eventos e guarda o histórico

O fluxo principal é simples:

> Um pedido é criado no Marketplace → Um evento é gerado → O Receiver recebe via webhook → O evento fica salvo para consulta

---

## 🧱 Arquitetura

O projeto é dividido em dois serviços independentes:

| Serviço | Porta | Responsabilidade |
|--------|------|------------------|
| **marketplace-api** | `8080` | Criar pedidos, atualizar status e disparar webhooks |
| **receiver-api** | `8081` | Receber eventos e armazenar o histórico |
| **MongoDB** | `27017` | Banco de dados usado pelos serviços |

A comunicação entre eles é feita via **HTTP (REST)**.

---

## ⚙️ Tecnologias utilizadas

- Java 17  
- Spring Boot  
- Spring Web  
- Spring Data MongoDB  
- Docker + Docker Compose  
- JUnit + Mockito (testes unitários)

---

## 🚀 Como rodar o projeto

### ✅ Pré-requisitos

Você precisa ter instalado:

- Docker  
- Docker Compose (ou Docker com suporte ao comando `docker compose`)  

> Não é obrigatório ter Java instalado se for rodar tudo via Docker.

---

### ▶️ Subindo tudo com Docker (recomendado)

Na **raiz do projeto**:

```bash
docker compose up --build
```

Isso vai subir:

- Marketplace API → http://localhost:8080  
- Receiver API → http://localhost:8081  
- MongoDB  

---

## 🧪 Fluxo completo de teste

Aqui está o passo a passo para testar o fluxo inteiro de webhook.

---

### 1️⃣ Cadastrar um webhook no Marketplace

Estamos dizendo ao Marketplace para avisar o Receiver sempre que houver eventos da loja `store-1`.

```bash
curl -X POST http://localhost:8080/webhooks \
  -H "Content-Type: application/json" \
  -d '{"storeIds":["store-1"],"callbackUrl":"http://receiver:8081/events"}'
```

---

### 2️⃣ Criar um pedido

```bash
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{"storeId":"store-1"}'
```

Guarde o **`id`** retornado — vamos usar no próximo passo.

---

### 3️⃣ Atualizar o status do pedido para **PAID**

Aqui usamos **PATCH**, porque estamos alterando apenas um campo do pedido (o status).

```bash
curl -X PATCH http://localhost:8080/orders/{ORDER_ID}/status \
  -H "Content-Type: application/json" \
  -d '{"status":"PAID"}'
```

Substitua `{ORDER_ID}` pelo ID real retornado na criação do pedido.

---

### 📩 O que acontece agora?

O Marketplace envia eventos para o Receiver, como por exemplo:

- `order.created`  
- `order.paid`

Esses eventos são recebidos e armazenados pelo **receiver-api**.

---

## 🔎 Consultando os eventos recebidos

### Todos os eventos

```bash
curl http://localhost:8081/events | jq
```

---

### Eventos por loja

```bash
curl http://localhost:8081/events/store/store-1 | jq
```

---

### Eventos por pedido

```bash
curl http://localhost:8081/events/order/{ORDER_ID} | jq
```

---

## 🧪 Rodando os testes

Entre na pasta do **receiver-api**:

```bash
cd receiver-api
mvn test
```

Os testes cobrem:

- Processamento de eventos  
- Controller de recebimento de webhook  
- Controller de consulta de eventos  

---

## 🛑 Parar os containers

```bash
docker compose down
```

Se quiser remover também os volumes do banco:

```bash
docker compose down -v
```

---

## 📌 Decisões de implementação

Alguns pontos importantes da solução:

- Cada evento salvo no Receiver contém um **snapshot do pedido** no momento do recebimento;  
- O Receiver **não acessa o banco do Marketplace**, apenas consome a API dele; 
- A comunicação entre serviços é desacoplada via **webhook HTTP**;
- Os testes unitários garantem a regra de negócio sem depender de subir a aplicação inteira;  

---

## ✅ Status do projeto

✔ Fluxo completo funcionando  
✔ Webhooks enviados e recebidos  
✔ Consulta de eventos disponível via API  
✔ Testes unitários passando  

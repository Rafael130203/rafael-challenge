# 📦 Marketplace Webhook Challenge

Este projeto simula a comunicação entre duas APIs:

- **Marketplace API** → Responsável por gerenciar pedidos e disparar webhooks  
- **Receiver API** → Responsável por receber os eventos e armazenar o histórico

O fluxo principal é:

> Um pedido é criado no Marketplace → Um evento é gerado → O Receiver recebe o webhook → O evento fica disponível para consulta

---

## 🧱 Arquitetura

O projeto é dividido em dois serviços independentes:

| Serviço | Porta | Responsabilidade |
|--------|------|------------------|
| **marketplace-api** | `8080` | Criar pedidos, alterar status e disparar webhooks |
| **receiver-api** | `8081` | Receber eventos e armazenar histórico |
| **MongoDB** | `27017` | Banco de dados compartilhado |

Toda a comunicação entre os serviços acontece via **HTTP (REST)**.

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
- Java 17 (opcional, apenas se quiser rodar sem Docker)

---

### ▶️ Subindo tudo com Docker (RECOMENDADO)

Na raiz do projeto:

```bash
docker compose up --build
```

Isso irá subir:

- Marketplace API → http://localhost:8080  
- Receiver API → http://localhost:8081  
- MongoDB  

---

## 🧪 Fluxo completo de teste

### 1️⃣ Cadastrar um webhook no Marketplace

```bash
curl -X POST http://localhost:8080/webhooks \
-H "Content-Type: application/json" \
-d '{
  "storeIds": ["store-1"],
  "callbackUrl": "http://receiver:8081/events"
}'
```

---

### 2️⃣ Criar um pedido

```bash
curl -X POST http://localhost:8080/orders \
-H "Content-Type: application/json" \
-d '{"storeId":"store-1"}'
```

Guarde o `id` retornado.

---

### 3️⃣ Marcar o pedido como pago

```bash
curl -X POST http://localhost:8080/orders/{ORDER_ID}/pay
```

Substitua `{ORDER_ID}` pelo ID real do pedido.

---

### 📩 O que acontece agora?

O Marketplace envia dois eventos para o Receiver:

- `order.created`
- `order.paid`

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

Entre no diretório do **receiver-api**:

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

Para remover volumes também:

```bash
docker compose down -v
```

---

## 📌 Decisões de implementação

- Os eventos são armazenados com um **snapshot do pedido** no momento do recebimento  
- O Receiver não depende do banco do Marketplace — apenas da API  
- A comunicação entre serviços é desacoplada via webhook HTTP  
- Os testes unitários garantem que a lógica de negócio funciona independentemente do Spring  

---

## ✅ Status do projeto

✔ Fluxo completo funcionando  
✔ Webhooks enviados e recebidos  
✔ Consulta de eventos disponível via API  
✔ Testes unitários passando  

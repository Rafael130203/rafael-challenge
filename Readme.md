# 🛒 Marketplace Webhook Challenge

Este projeto simula um **marketplace** que gerencia pedidos e dispara **webhooks** para sistemas externos quando eventos importantes acontecem (como criação e pagamento de pedidos).

Ele é composto por **dois serviços** independentes que se comunicam via HTTP.

---

## 🧩 Arquitetura

| Serviço | Porta | Função |
|--------|------|--------|
| **Marketplace API** | 8080 | Cria pedidos, altera status e envia eventos |
| **Receiver API** | 8081 | Recebe webhooks e armazena os eventos |
| **MongoDB** | 27017 | Banco de dados usado pelo Receiver |

Fluxo geral:

```
Cliente → Marketplace → Webhook HTTP → Receiver → MongoDB
```

Quando um pedido muda de status, o Marketplace envia um **POST** para a URL cadastrada como webhook.

---

## 🚀 Como rodar o projeto

### ✅ Pré-requisitos

- Docker  
- Docker Compose (ou `docker compose`)

---

### ▶️ Subindo os serviços

Na raiz do projeto:

```bash
docker compose up --build
```

Após subir:

- Marketplace → http://localhost:8080  
- Receiver → http://localhost:8081  

---

## 🔔 Fluxo completo de teste

### 1️⃣ Cadastrar um webhook

```bash
curl -X POST http://localhost:8080/webhooks \
  -H "Content-Type: application/json" \
  -d '{"storeIds":["store-1"],"callbackUrl":"http://receiver:8081/webhook/events"}'
```

Esse webhook diz ao Marketplace para onde enviar os eventos da loja `store-1`.

---

### 2️⃣ Criar um pedido

```bash
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{"storeId":"store-1"}'
```

Resposta esperada:

```json
{
  "id": "ID_DO_PEDIDO",
  "storeId": "store-1",
  "status": "CREATED",
  "createdAt": "2026-..."
}
```

Guarde o **id** retornado.

---

### 3️⃣ Atualizar o status do pedido

```bash
curl -X PATCH http://localhost:8080/orders/ID_DO_PEDIDO/status \
  -H "Content-Type: application/json" \
  -d '{"status":"PAID"}'
```

Essa ação dispara um **webhook** para o Receiver.

---

### 4️⃣ Consultar os eventos recebidos

```bash
curl http://localhost:8081/events
```

Resposta esperada:

```json
[
  {
    "id": "...",
    "event": "order.created",
    "orderId": "...",
    "storeId": "store-1",
    "receivedAt": "...",
    "orderSnapshot": {
      "id": "...",
      "storeId": "store-1",
      "status": "CREATED",
      "createdAt": "..."
    }
  },
  {
    "id": "...",
    "event": "order.paid",
    "orderId": "...",
    "storeId": "store-1",
    "receivedAt": "...",
    "orderSnapshot": {
      "id": "...",
      "storeId": "store-1",
      "status": "PAID",
      "createdAt": "..."
    }
  }
]
```

---

## 📦 Eventos enviados pelo Marketplace

| Evento | Quando ocorre |
|--------|---------------|
| `order.created` | Quando um pedido é criado |
| `order.paid` | Quando o status muda para **PAID** |

Cada evento contém um **snapshot do pedido** no momento do disparo.

---

## 📬 Endpoint de Webhook do Receiver

O Receiver expõe o endpoint que recebe os webhooks:

```
POST /webhook/events
```

Os eventos recebidos podem ser consultados em:

```
GET /events
```

---

## 🛠 Tecnologias utilizadas

- Java 17  
- Spring Boot  
- Spring WebFlux (Marketplace)  
- Spring MVC (Receiver)  
- Spring Data MongoDB  
- Docker + Docker Compose  

---

## 🧪 Dicas de Debug

Ver logs do Marketplace:

```bash
docker logs rafael-challenge-marketplace-1
```

Ver logs do Receiver:

```bash
docker logs rafael-challenge-receiver-1
```

Se os eventos não aparecerem:

- Verifique se o webhook foi cadastrado com  
  `http://receiver:8081/webhook/events`  
- Confirme se os containers estão rodando (`docker ps`)  
- Veja se o status do pedido realmente foi alterado para **PAID**

---

## ✅ Status do Projeto

✔ Criação de pedidos  
✔ Atualização de status  
✔ Disparo de webhooks  
✔ Persistência de eventos no Receiver  
✔ Consulta de eventos via API  

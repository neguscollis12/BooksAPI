# Simple Books API

This API allows you to reserve a book.

**Base URL:** `https://simple-books-api.click`

## Table of Contents

- [Endpoints Overview](#endpoints-overview)
- [Status](#status)
- [List of Books](#list-of-books)
- [Get a Single Book](#get-a-single-book)
- [Submit an Order](#submit-an-order)
- [Get All Orders](#get-all-orders)
- [Get an Order](#get-an-order)
- [Update an Order](#update-an-order)
- [Delete an Order](#delete-an-order)
- [API Authentication](#api-authentication)
- [Possible Errors](#possible-errors)

---

## Endpoints Overview

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|:-------------:|
| GET | `/status` | Returns the status of the API | No |
| GET | `/books` | Returns a list of books | No |
| GET | `/books/:bookId` | Retrieve detailed information about a book | No |
| POST | `/orders` | Submit a new order | Yes |
| GET | `/orders` | View all orders | Yes |
| GET | `/orders/:orderId` | View an existing order | Yes |
| PATCH | `/orders/:orderId` | Update an existing order | Yes |
| DELETE | `/orders/:orderId` | Delete an existing order | Yes |
| POST | `/api-clients/` | Register an API client and get an access token | No |

---

## Status

**`GET /status`**

Returns the status of the API.

---

## List of Books

**`GET /books`**

Returns a list of books.

### Optional Query Parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| `type` | String | `fiction` or `non-fiction` |
| `limit` | Integer | A number between 1 and 20 |

---

## Get a Single Book

**`GET /books/:bookId`**

Retrieve detailed information about a book.

---

## Submit an Order

**`POST /orders`**

Allows you to submit a new order. **Requires authentication.**

The request body needs to be in JSON format and include the following properties:

| Property | Type | Required |
|----------|------|:--------:|
| `bookId` | Integer | Yes |
| `customerName` | String | Yes |

### Example

```http
POST /orders/
Authorization: Bearer <YOUR TOKEN>

{
  "bookId": 1,
  "customerName": "John"
}
```

The response body will contain the order Id.

---

## Get All Orders

**`GET /orders`**

Allows you to view all orders. **Requires authentication.**

---

## Get an Order

**`GET /orders/:orderId`**

Allows you to view an existing order. **Requires authentication.**

---

## Update an Order

**`PATCH /orders/:orderId`**

Update an existing order. **Requires authentication.**

The request body needs to be in JSON format and allows you to update the following properties:

| Property | Type |
|----------|------|
| `customerName` | String |

### Example

```http
PATCH /orders/PF6MflPDcuhWobZcgmJy5
Authorization: Bearer <YOUR TOKEN>

{
  "customerName": "John"
}
```

---

## Delete an Order

**`DELETE /orders/:orderId`**

Delete an existing order. **Requires authentication.**

The request body needs to be empty.

### Example

```http
DELETE /orders/PF6MflPDcuhWobZcgmJy5
Authorization: Bearer <YOUR TOKEN>
```

---

## API Authentication

To submit or view an order, you need to register your API client.

**`POST /api-clients/`**

The request body needs to be in JSON format and include the following properties:

| Property | Type |
|----------|------|
| `clientName` | String |
| `clientEmail` | String |

### Example

```json
{
   "clientName": "Postman",
   "clientEmail": "valentin@example.com"
}
```

The response body will contain the access token. The access token is valid for **7 days**.

---

## Possible Errors

| Status Code | Message | Resolution |
|:-----------:|---------|------------|
| 409 | "API client already registered." | Try changing the values for `clientEmail` and `clientName` to something else. |

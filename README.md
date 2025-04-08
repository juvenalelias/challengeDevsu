# Description

This is a backend application that simulates the management of financial transactions system and processes events asynchronously.

Here is explained how to use the endpoints to allow users to create, getting and management the movements in the process.

Also is included a **SpringBoot Microservices.postman_collection.json** file to allow import it and test the app functionality.

---

## Topics
1. [Endpoints](#endpoints)
2. [Instructions of Usage](#instructions-of-usage)
   
   **Clients:**

   - [Create Clients](#1-create-clients)
   - [Get all Clients](#2-get-all-clients)
   - [Get Client by id](#3-get-client-by-id)
   - [Update Clients](#4-update-clients)
   - [Delete Clients](#5-delete-clients)

   **Accounts:**

   - [Create Accounts](#6-create-accounts)
   - [Get all Accounts](#7-get-all-accounts)
   - [Get Account by id](#8-get-account-by-id)
   - [Update Account](#9-update-account)
   - [Delete Account](#10-delete-account)

   **Movements:**   

   - [Create Movements](#11-create-movements)
   - [Get Movement by id](#12-get-movement-by-id)

   **Reports:** 
    
   - [Get Report by Client & Dates](#13-get-report-by-client-dates)
   - [Get Report by Client](#14-get-report-by-client)
---

## Endpoints

1. **Create clients**  
   - **URL**: `http://localhost:8081/clientes`  
   - **Method**: `POST`  
   - **Description**: Creates a client with some basic data (detailed next below).

2. **Get all clients**  
   - **URL**: `http://localhost:8081/clientes`  
   - **Method**: `GET`  
   - **Description**: Returns all systems clients.

3. **Get client by id**  
   - **URL**: `http://localhost:8081/clientes/:id`  
   - **Method**: `GET`  
   - **Description**: Returns a specific clients.

4. **Update clients**
   - **URL**: `http://localhost:8081/clientes`
   - **Method**: `PUT`
   - **Description**: Returns an updated specific clients.

5. **Delete client**
   - **URL**: `http://localhost:8081/clientes/:id`
   - **Method**: `DELETE`
   - **Description**: Delete a specific clients.

6. **Create Account**
   - **URL**: `http://localhost:8084/cuentas`
   - **Method**: `POST`
   - **Description**: Creates a account with some basic data (detailed next below).

7. **Get all Accounts**
   - **URL**: `http://localhost:8084/cuentas`
   - **Method**: `GET`
   - **Description**: Returns all systems accounts.

8. **Get Account by id**
   - **URL**: `http://localhost:8084/cuentas/:id`
   - **Method**: `GET`
   - **Description**: Returns a specific accounts.

9. **Update Account**
   - **URL**: `http://localhost:8084/cuentas`
   - **Method**: `PUT`
   - **Description**: Returns an updated specific accounts.

10. **Delete Account**
    - **URL**: `http://localhost:8084/cuentas/:id`
    - **Method**: `DELETE`
    - **Description**: Delete a specific accounts.

11. **Create Movements**
    - **URL**: `http://localhost:8084/movimientos`
    - **Method**: `POST`
    - **Description**: Creates a Movements with some basic data (detailed next below).

12. **Get all Movements**
    - **URL**: `http://localhost:8084/movimientos`
    - **Method**: `GET`
    - **Description**: Returns all systems movements.

13. **Get Movement by id**
    - **URL**: `http://localhost:8081/movimientos/:id`
    - **Method**: `GET`
    - **Description**: Returns a specific movements.

14. **Get Report by Client & Dates**
    - **URL**: `http://localhost:8084/reportes?desde={fechaIni}&hasta={fechaFin}&clienteId={id}`
    - **Method**: `GET`
    - **Description**: Returns a specific reports accounts movements of a client.

15. **Get Report by Client**
    - **URL**: `http://localhost:8084/reportes/:id`
    - **Method**: `GET`
    - **Description**: Returns a specific reports accounts movements of a client.

---

## Instructions of Usage

### 1. create clients

**Request**  
- **URL**: `http://localhost:8081/clientes`  
- **Method**: `POST`  
- **Request Body** (JSON format):  
  ```json
  {
    "nombre": "Carlos Rivas",
    "genero": "M",
    "edad": 29,
    "identificacion": "2147385",
    "direccion": "Street 17",
    "telefono": "1234574",
    "password": "NassytynYZ",
    "estado": true
  }


### 2. Get all clients
**Request**  

- **URL**: `http://localhost:8081/clientes`
- **Method**: `GET`

### 3. Get client by Id
**Request**  

 **URL**: `http://localhost:8081/clientes/:id`
- **Method**: `GET`
- **Path Parameter**: `id (Long): The client identifier. Example: 1119`

### 4. Update clients
**Request**

**URL**: `http://localhost:8081/clientes`
- **Method**: `PUT`
- **Request Body** (JSON format):
  ```json
  {
    "id": 1,
    "nombre": "Carlos Rivas",
    "genero": "M",
    "edad": 29,
    "identificacion": "2147385",
    "direccion": "Street 17",
    "telefono": "1234574",
    "password": "NassytynYZ",
    "estado": true
  }

### 5. Delete clients
**Request**

**URL**: `http://localhost:8081/clientes/:id`
- **Method**: `DELETE`
- **Path Parameter**: `id (Long): The client identifier. Example: 10`


### 6. Create Accounts

**Request**
- **URL**: `http://localhost:8084/cuentas`
- **Method**: `POST`
- **Request Body** (JSON format):
  ```json
  {
    "numeroCuenta": "00972005428",
    "tipoCuenta": "Ahorros Bancolombia",
    "saldoInicial": 1000000,
    "estado": true,
    "clienteId": 3
  }


### 7. Get all Accounts
**Request**

- **URL**: `http://localhost:8084/cuentas`
- **Method**: `GET`


### 8. Get Account by Id
**Request**

**URL**: `http://localhost:8084/cuentas/:id`
- **Method**: `GET`
- **Path Parameter**: `id (Long): The account identifier. Example: 1119`

### 9. Update Account
**Request**

**URL**: `http://localhost:8084/cuentas`
- **Method**: `PUT`
- **Request Body** (JSON format):
  ```json
  {
    "numeroCuenta": "00972005428",
    "tipoCuenta": "Corriente",
    "saldoInicial": 12000000,
    "estado": true,
    "clienteId": 3
  }

### 10. Delete Account
**Request**

**URL**: `http://localhost:8084/cuentas/:id`
- **Method**: `DELETE`
- **Path Parameter**: `id (Long): The account identifier. Example: 9`


### 11. Create Movements

**Request**
- **URL**: `http://localhost:8084/movimientos`
- **Method**: `POST`
- **Request Body** (JSON format):
  ```json
  {
    "numeroCuenta": "00972005428",
    "tipoMovimiento": "Consignación",
    "valor": -200.0
  }


### 12. Get Movement by Id
**Request**

**URL**: `http://localhost:8084/movimientos/:id`
- **Method**: `GET`
- **Path Parameter**: `id (Long): The movement identifier. Example: 8`


### 13. Get Report by Client Dates
**Request**

**URL**: `http://localhost:8084/reportes?desde={fechaIni}&hasta={fechaFin}&clienteId={id}`
- **Method**: `GET`
- **RequestParam**: `clientId (Long): The client identifier. Example: 8`
- **RequestParam**: `desde (String): Initial date (YYYY-MM-DD)`
- **RequestParam**: `hasta (String): End date (YYYY-MM-DD)`


### 14. Get Report by Client
**Request**

**URL**: `http://localhost:8084/reportes/:id`
- **Method**: `GET`
- **Path Parameter**: `id (Long): The client identifier. Example: 8`



# Minimum tools required

- Docker compose

# Tech Stack

- Spring Boot 3
- Java 17
- kafka
- postgres

# Run the app

- execute the follow command on the project root folder

    ```console
    docker compose up -d


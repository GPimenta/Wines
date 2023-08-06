# Wine Service
A microservice responsible for handling CRUD operations related to the wine and customer databases.

## Built With
- [Scala](https://www.scala-lang.org/)
- [Akka HTTP](https://doc.akka.io/docs/akka-http/current/index.html)
- [PostgreSQL](https://www.postgresql.org/)
- [JDBC](https://jdbc.postgresql.org/documentation/)

## Table of Contents
1. [Wines Service REST API](#wines-service-rest-api)
   - [Retrieve Wines](#retrieve-wines)
   - [Retrieve a Specific Wine](#retrieve-a-specific-wine)
   - [Insert Wine](#insert-wine)
   - [Update Wine](#update-wine)
   - [Delete Wine](#delete-wine)
2. [Customers Service REST API](#customers-service-rest-api)
   - [Retrieve Customers](#retrieve-customers)
   - [Retrieve a Specific Customer](#retrieve-a-specific-customer)
   - [Insert Customer](#insert-customer)
   - [Update Customer](#update-customer)
   - [Delete Customer](#delete-customer)
3. [Database Schema](#database-schema)

## Wines Service REST API

### Retrieve Wines
Returns a list of all wines in the database.

**HTTP Request:** `GET /wines`

**Returns:**
- A list of wines - 200 OK
- An empty list if the database is empty - 200 OK

**Example Response:**
```json
HTTP/1.1 200 OK
Content-Type: application/json

{
    "wines": [
        {
            "id": 1, 
            "wineName": "Chardonnay",
            "grapeVariety": "Chardonnay",
            "vintageYear": 2020,
            "wineryName": "Winery A",
            "price": 25.99
        },
        {
            "id": 2,        
            "wineName": "Merlot",
            "grapeVariety": "Merlot",
            "vintageYear": 2018,
            "wineryName": "Winery B",
            "price": 18.99
        }
        // ... more wines
    ]
}
```
### Retrieve a Specific Wine
Returns details of a specific wine by its ID.

**HTTP Request:** `GET /wines/{id}`

**Parameters:**
- **id** (Path Parameter) - ID of the wine.

**Returns:**
- A wine - 200 OK
- Error message if the wine with the given ID is not found - 404 Not Found

**Example Response:**
```json
HTTP/1.1 200 OK
Content-Type: application/json

{
    "id": 1,
    "wineName": "Chardonnay",
    "grapeVariety": "Chardonnay",
    "vintageYear": 2020,
    "wineryName": "Winery A",
    "price": 25.99
}

```
### Insert Wine
Inserts a new wine into the database.

**HTTP Request:** `POST /wines`

**Parameters:**
- None

**Body:**
- **id** (String, Required) - ID of the wine.
- **wineName** (String, Required) - Name of the wine.
- **grapeVariety** (String, Required) - Grape variety of the wine.
- **vintageYear** (Number, Required) - Vintage year of the wine.
- **wineryName** (String, Required) - Name of the winery producing the wine.
- **price** (Number, Required) - Price of the wine.

**Returns:**
- Success message if the wine is inserted - 201 Created
- Error message if a wine with the given ID already exists or if the request content is malformed - 400 Bad Request

**Example Request:**
```json
POST /wines
Content-Type: application/json

{
    "id": "3",
    "wineName": "Chardonnay",
    "grapeVariety": "Chardonnay",
    "vintageYear": 2020,
    "wineryName": "Winery Z",
    "price": 18.99
}
```
### Update Wine
Updates information for an existing wine in the database.

**HTTP Request:** `PUT /wines/{id}`

**Parameters:**

**Body:**
- **id** (String, Required) - ID of the wine.
- **wineName** (String, Required) - Updated name of the wine.
- **grapeVariety** (String, Required) - Updated grape variety of the wine.
- **vintageYear** (Number, Required) - Updated vintage year of the wine.
- **wineryName** (String, Required) - Updated name of the winery producing the wine.
- **price** (Number, Required) - price of the wine.
- **newPrice** (Number, Required) - Updated price of the wine.

**Returns:**
- Success message if the wine is updated - 200 OK
- Error message if the wine with the specified ID does not exist - 404 Not Found

**Example Request:**
```json
PUT /wines
Content-Type: application/json

{
    "wineName": "Updated Chardonnay",
    "grapeVariety": "Updated Chardonnay",
    "vintageYear": 2021,
    "wineryName": "Updated Winery Z",
    "price": 21.99,
    "newPrice": 21.99
}
```
**Example Response (Success):**

```json
HTTP/1.1 200 OK
Content-Type: application/json

{
    "message": "Wine updated successfully."
}
```
**Example Response (Error - Wine Not Found):**

```json
HTTP/1.1 404 Not Found
Content-Type: application/json

{
"error": "The wine does not exist."
}
```
### Delete Wine
Deletes a wine from the database.

**HTTP Request:** `DELETE /wines`

**Parameters:**

**Body:**
- **id** (String, Required) - ID of the wine.
- **wineName** (String, Required) - Updated name of the wine.
- **grapeVariety** (String, Required) - Updated grape variety of the wine.
- **vintageYear** (Number, Required) - Updated vintage year of the wine.
- **wineryName** (String, Required) - Updated name of the winery producing the wine.
- **price** (Number, Required) - price of the wine.

**Returns:**
- Success message if the wine is deleted - 200 OK
- Error message if the wine with the specified ID does not exist - 404 Not Found

**Example Request:**
DELETE /wines

**Example Response (Success):**
```json
HTTP/1.1 200 OK
Content-Type: application/json

{
    "message": "Wine deleted successfully."
}
```
**Example Response (Error - Wine Not Found):**

```json
HTTP/1.1 404 Not Found
Content-Type: application/json

{
"error": "The wine does not exist."
}
```

## Customers Service REST API

### Retrieve Customers
Returns a list of all customers in the database.

**HTTP Request:** `GET /customers`

**Parameters:**
- None

**Returns:**
- A list of customers - 200 OK
- An empty list if the database is empty - 404 Not Found

**Example Response:**
```json
HTTP/1.1 200 OK
Content-Type: application/json

{
    "customers": [
        {
            "first_name": "John",
            "last_name": "Doe",
            "email": "JD@mail.com"
        },
        {
            "first_name": "Jonas",
            "last_name": "Doe",
            "email": "JnD@mail.com"
        }
    ]
}
```

### Retrieve a Specific Customer
Returns information about a specific customer based on their ID.

**HTTP Request:** `GET /customers/{customer_id}`

**Parameters:**
- **customer_id** (String, Path, Required) - ID of the customer to retrieve.

**Returns:**
- Customer information - 200 OK
- Error message if the customer with the specified ID does not exist - 404 Not Found

**Example Request:**
GET /customers/2

**Example Response (Success):**
```json
HTTP/1.1 200 OK
Content-Type: application/json

{
      "first_name": "John",
      "last_name": "Doe",
      "email": "JD@mail.com"
}
```
**Example Response (Error - Customer Not Found):**
```json
HTTP/1.1 404 Not Found
Content-Type: application/json

{
    "error": "The customer with ID 2 does not exist."
}
```
### Insert Customer
Adds a new customer to the database.

**HTTP Request:** `POST /customers`

**Parameters:**
- None

**Body:**
- **first_name** (String, Required) - First name of the customer.
- **last_name** (String, Required) - Last name of the customer.
- **email** (String, Required) - Email of the customer.

**Returns:**
- Success message if the customer was inserted - 201 Created
- Error message if a customer was not inserted - 400 Bad Request

**Example Request:**
POST /customers
```json
{
   "first_name": "John",
   "last_name": "Doe",
   "email": "JD@mail.com"
}
```

**Example Response (Success):**
```json
HTTP/1.1 201 Created
Content-Type: application/json

{
    "message": "Customer created successfully."
}
```
**Example Response (Error - Customer was not inserted):**

```json
HTTP/1.1 400 Bad Request
Content-Type: application/json

{
"error": "Customer was not inserted."
}
```

### Update Customer
Updates information about a specific customer in the database.

**HTTP Request:** `PUT /customers`

**Parameters:**

**Body:**
- **first_name** (String, Required) - First name of the customer.
- **last_name** (String, Required) - Last name of the customer.
- **email** (String, Required) - Email of the customer.

**Returns:**
- Success message if the customer was updated - 200 OK
- Error message if a customer was not updated - 404 Not Found

**Example Request:**
PUT /customers
```json
{
   "first_name": "John",
   "last_name": "Doe",
   "email": "JD@mail.com",
   "newEmail": "JDD@mail.com"
}
```

**Example Response (Success):**
```json
HTTP/1.1 200 OK
Content-Type: application/json

{
    "message": "Customer updated successfully."
}
```

**Example Response (Error - Customer Not Updated):**

```json
HTTP/1.1 404 Not Found
Content-Type: application/json

{
"error": "Customer was not updated."
}
```

### Delete Customer
Deletes a specific customer from the database.

**HTTP Request:** `DELETE /customers

**Parameters:**

**Body**
- **first_name** (String, Required) - First name of the customer.
- **last_name** (String, Required) - Last name of the customer.
- **email** (String, Required) - Email of the customer.

**Returns:**
- Success message if the customer was deleted - 200 OK
- Error message if a customer was not deleted - 404 Not Found

**Example Request:**
DELETE /customers


**Example Response (Success):**
```json
HTTP/1.1 200 OK
Content-Type: application/json

{
    "message": "Customer deleted successfully."
}
```
**Example Response (Error - Customer Not Found):**

```json
HTTP/1.1 404 Not Found
Content-Type: application/json

{
"error": "Customer not deleted."
}
```

## Database Schema

The database schema for the Wine Service consists of two main tables: `wines` and `customers`.

### Wines Table

| Column Name | Data Type  | Description                     |
|----------|------------|---------------------------------|
| id       | Integer     | Unique identifier for the wine. |
| wine_name | String     | Name of the wine.               |
| grape_variety | String     | Variety of grape used.          |
| vintage_year | Integer    | Year the grapes were harvested.|
| winery_name | String     | Name of the winery.             |
| price    | Decimal    | Price of the wine.              |

### Customers Table

| Column Name | Data Type | Description                       |
|-------|-----------|-----------------------------------|
| id    | Integer   | Unique identifier for the customer. |
| first_name | String    | First name of the customer.       |
| last_name | String    | Last name of the customer.        |
| email | String    | Email of the customer.            |

[Jump to the table of contents](#table-of-contents)

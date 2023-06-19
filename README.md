# Customers Service
A microservice responsible to handle CRUD operations related to the wine database.

# Built with
- [Scala](https://www.scala-lang.org/)
- [Akka HTTP](https://doc.akka.io/docs/akka-http/current/index.html)
- [PostgreSQL](https://www.postgresql.org/)
- [JDBC](https://jdbc.postgresql.org/documentation/)


# Table of Contents

1. [Wines Service REST API](#wines-service-rest-api)
    1. [Retrieve wines](#retrieve-wines)
    2. [Retrieve a specific wine](#retrieve-a-specific-wine)
    3. [Insert wine](#insert-wine)
    4. [Update wine](#update-wine)
    5. [Delete wine](#delete-wine)
2. [Database schema](#database-schema)


<br>

# Wines Service REST API
## Retrieve wines
Returns all customers in the database.

### Parameters
- N/A

### Body
- N/A

### Returns
- **A list of customers** - 200 OK
- **An empty list**, if the database is empty - 200 OK

### Example

`GET /customers`

    marionete.tech/customers/    

#### Response

    akka-http/10.2.9
    Date: Mon, 26 Sep 2022 15:36:34 GMT
    Status: 200 OK    
    Content-Type: application/json
    Content-Length: 509

    {
        "customers": [
            {
                "customer_flight": "BA5000",
                "customer_flight_date": "2022-09-26 12:40:34.024594",
                "customer_id": "2",
                "customer_name": "Steve Doe"
            },
            {
                "customer_flight": "BA5000",
                "customer_flight_date": "2022-09-26 12:40:59.785958",
                "customer_id": "3",
                "customer_name": "Steve John"
            },
            {
                "customer_flight": "BA5000",
                "customer_flight_date": "2022-09-26 12:41:12.998693",
                "customer_id": "4",
                "customer_name": "Doe John"
            },
            {
                "customer_flight": "BA5000",
                "customer_flight_date": "2022-09-26 15:50:00.0",
                "customer_id": "1",
                "customer_name": "John Doe"
            }
        ]
}

[Jump to the table of contents](#table-of-contents)

---

## Retrieve a specific wine
Returns one specific wine passing the wine's id as a parameter.

### Parameters
- **CustomerId** - the id which identifies the wine.

### Body
- N/A

### Returns
- **A wine** - 200 OK
- **Error message: "The wine with id X does not exist."**, if a wine with the given id doesn't exist - 404 Not Found

### Example

`GET /customers/{customerId}`

    marionete.tech/customers/2    

#### Response

    akka-http/10.2.9
    Date: Mon, 26 Sep 2022 15:53:15 GMT
    Status: 200 OK    
    Content-Type: application/json
    Content-Length: 126

    {
        "customer_flight": "BA5000",
        "customer_flight_date": "2022-09-26 12:40:34.024594",
        "customer_id": "2",
        "customer_name": "Steve Doe"
    }


[Jump to the table of contents](#table-of-contents)


---
## Validate wine
Returns a json object containing a message and a boolean describing if a wine is valid.

### Parameters
- **customer_id** - the id which identifies the wine.
- **flight_id** - the id which identifies the flight.

### Body
- N/A

### Returns
- **Is valid** - 200 OK
- **Error message: "Missing paramaters"**, if a parameter is missing - 400 Bad Request

### Example

`GET /customers/?customer_id={c_id}&flight_id={f_id}`

    marionete.tech/customers/?customer_id=1&flight_id=BA5000 

#### Response

    akka-http/10.2.9
    Date: Mon, 26 Sep 2022 15:53:15 GMT
    Status: 200 OK    
    Content-Type: application/json
    Content-Length: 126

    {
        message: "Valid wine.",
        isValid: true
    }


[Jump to the table of contents](#table-of-contents)


---
## Insert Wine
To insert a new wine into the database you must specify in the body all the necessary fields.

### Parameters
- N/A

### Body
- **id** - the id which identifies the wine.
- **name** - the wine's name
- **flight_id** - the wine's flight
- **flight_date** - the date of the flight

All fields are string type.

### Returns
- **Successfully message: "Wine created successfully."**, if the wine was inserted - 201 Created
- **Error message: "Wine with the given id already exists."**, if a wine already exists - 400 Bad Request
- **Error message: "The request content was malformed:"**, when one or more paramaters are missing - 400 Bad Request


### Example

`POST /customers`

    marionete.tech/customers/  

    body: {
        "id": "1",
        "name": "Paul Jones",
        "flight_id": "BA5000",
        "flight_date": "2022-09-21 11:50"
    }

#### Response

    akka-http/10.2.9
    Date: Mon, 26 Sep 2022 15:53:15 GMT
    Status: 201 Created   
    Content-Type: application/json
    Content-Length: 45

    {
      "message": "Wine created successfully."
    }

[Jump to the table of contents](#table-of-contents)

---
<br>

## Update Wine
To update wine information you just have to identify the wine you want to change.

### Parameters
- **CustomerId** - the id which identifies the wine.

### Body
- **name** - the wine's name
- **flight_id** - the wine's flight
- **flight_date** - the flight date

All parameters are string type, if you don't wish to update some fields you can pass an empty string to them.

### Returns
- **Successfully message: "Wine updated successfully."**, if the wine was updated - 200 OK
- **Error message: "The wine with id X does not exist.""**, if there isn't a wine with the specified id - 404 Not Found

### Example

`PUT /customers/{customerId}`

    marionete.tech/customers/1

    body: {
        "name": "",
        "flight_id": "",
        "flight_date": "2022-09-26 16:50"
    }

#### Response

    akka-http/10.2.9
    Date: Mon, 19 Sep 2022 15:09:18 GMT
    Status: 200 OK   
    Content-Type: application/json
    Content-Length: 45

    {
      "message": "Wine updated successfully..
    }

[Jump to the table of contents](#table-of-contents)

---
<br>

## Delete Wine
To delete a wine you just have to identify the wine id.

### Parameters
- **CustomerId** - the id which identifies the wine.

### Body
- N/A

### Returns
- **Successfully message: "The wine was deleted"**, if the wine was deleted - 200 OK
- **Error message: "Wine with the given id does not exist.""**, if there isn't a wine with the specified id - 404 Not Found

### Example

`DELETE /customers/{customerId}`

    marionete.tech/customers/1    

#### Response

    akka-http/10.2.9
    Date: Mon, 19 Sep 2022 15:09:18 GMT
    Status: 200 OK   
    Content-Type: application/json
    Content-Length: 45

    {
      "message": "Wine deleted successfully."
    }

[Jump to the table of contents](#table-of-contents)

---
<br>

# Database schema

## Wine Table

    {    
        "customer_id": String,
        "customer_name": String,
        "customer_flight": String,
        "customer_flight_date": Timestamp
    }

[Jump to the table of contents](#table-of-contents)
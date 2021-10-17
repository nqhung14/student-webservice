# REST API

The REST API to the example app is described below.

## Object Model:
```json
"student":
{
"id":"ab71a315-47bd-4493-b6b4-26f4e97f4e82",
"name":"Foo",
"gender":"Male",
"birthYear":"1988",
"address":"Foo"
}
```

## Get list of student by name

### Request

`GET /student/api/{name}`

    name: Name of student
    Request Header: Token = <value of token>


### Response

    200 OK : return a list of students
    401 UNAUTHORIZED : token is invalid or missing token
    500: INTERNAL_SERVER_ERROR

## Create a new Student

### Request

`POST /student/api/`

    Request Header: Token = <value of token>
    Request Body: Student Object

### Response

    200 OK : create Student successfuly
    400 BAD_REQUEST : missing name or gender
    401 UNAUTHORIZED : token is invalid or missing token
    500: INTERNAL_SERVER_ERROR

## Get student by id

### Request

`GET /student/api/{id}`

    id: Student id
    Request Header: Token = <value of token>


### Response

    200 OK : return a student with id
    400 BAD_REQUEST : invalid id
    404 NOT_FOUND : no student with given id is found
    401 UNAUTHORIZED : token is invalid or missing token
    500: INTERNAL_SERVER_ERROR


## Update a student

### Request

`PUT /student/api/{id}`

    id: Student id
    Request Header: Token = <value of token>
    Request Body: Student object to be updated

### Response

    200 OK : Update Successfully
    401 UNAUTHORIZED : token is invalid or missing token
    400 BAD_REQUEST : invalid id, id is not matched
    500: INTERNAL_SERVER_ERROR

## Partial update a student

### Request

`PATCH /student/api/{id}`

    id: Student id
    Request Header: Token = <value of token>
    Request Body: Student object to be updated

### Response

    200 OK : Update Successfully
    401 UNAUTHORIZED : token is invalid or missing token
    400 BAD_REQUEST : invalid id, id is not matched
    500: INTERNAL_SERVER_ERROR

## Delete a student

### Request

`DELETE /student/api/{id}`

    id: Student id
    Request Header: Token = <value of token>


### Response

    200 OK : Delete Successfully
    401 UNAUTHORIZED : token is invalid or missing token
    400 BAD_REQUEST : invalid id
    500: INTERNAL_SERVER_ERROR
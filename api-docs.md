# Macro Tracker API Documentation

## Base URL
```
http://localhost:8080/api
```

## Authentication
No authentication required for development environment.

## Endpoints

### Create Food
```http
POST /foods
Content-Type: application/json

{
    "name": "Chicken Breast",
    "fat": 3.6,
    "carbohydrates": 0.0,
    "protein": 31.0,
    "calories": 165.0
}
```

### Get All Foods
```http
GET /foods
```

### Get Food by ID
```http
GET /foods/{id}
```

### Update Food
```http
PUT /foods/{id}
Content-Type: application/json

{
    "name": "Chicken Breast",
    "fat": 4.0,
    "carbohydrates": 0.0,
    "protein": 32.0,
    "calories": 170.0
}
```

### Delete Food
```http
DELETE /foods/{id}
```

## Response Codes
- 200: Success
- 201: Created
- 204: No Content
- 400: Bad Request
- 404: Not Found

## Database Access
H2 Console available at: `http://localhost:8080/h2-console`

Connection details:
```
JDBC URL: jdbc:h2:mem:macrodb
Username: sa
Password: password
```
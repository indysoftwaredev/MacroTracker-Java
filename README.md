# Macro Tracker API

A Spring Boot REST API for tracking nutritional information of foods, including macronutrients (protein, carbohydrates, fat) and calories.

## Features

- CRUD operations for food items
- Nutritional information tracking
- In-memory H2 database for development
- Input validation
- RESTful endpoints
- Exception handling

## Technology Stack

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 Database
- Maven
- Jakarta Validation

## Getting Started

### Prerequisites

- JDK 17 or higher
- Maven 3.x

### Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/macrotracker.git
cd macrotracker
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

### Database Access

H2 Console is available at `http://localhost:8080/h2-console` with these credentials:
```
JDBC URL: jdbc:h2:mem:macrodb
Username: sa
Password: password
```

## API Documentation

For detailed API documentation and example requests, see [API_DOCUMENTATION.md](API_DOCUMENTATION.md)

A Postman collection is available in the repository: `macrotracker-api-collection.json`

## Project Structure

```
src/main/java/com/example/macrotracker/
├── MacrotrackerApplication.java
├── controller/
│   └── FoodController.java
├── entity/
│   └── Food.java
├── repository/
│   └── FoodRepository.java
├── service/
│   ├── FoodService.java
│   └── FoodServiceImpl.java
└── exception/
    └── GlobalExceptionHandler.java
```

## Contributing

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

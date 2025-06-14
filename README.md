# Trade Verification API

A robust Spring Boot REST API for managing trade inquiry verification processes. Built with Java 17, Spring Boot 3.2, and Gradle.

## 🚀 Features

- **Complete CRUD Operations** for trade inquiries
- **Status Management** with validation (PENDING_VERIFICATION, VERIFIED, REJECTED)
- **Filtering & Search** capabilities
- **Bean Validation** for data integrity
- **Global Exception Handling** with meaningful error messages
- **Sample Data** for immediate testing
- **Comprehensive API Documentation**

## 📋 Requirements

- Java 17 or higher
- Gradle 7.x or higher
- IDE with Spring Boot support (IntelliJ IDEA, VS Code, Eclipse)

## 🛠️ Setup Instructions

### 1. Project Structure
Create the following directory structure:
```
trade-verification-api/
├── build.gradle
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── rsa/
│       │           └── tradeverification/
│       │               ├── TradeVerificationApplication.java
│       │               ├── config/
│       │               │   └── DataInitializer.java
│       │               ├── controller/
│       │               │   └── TradeInquiryController.java
│       │               ├── dto/
│       │               │   ├── ApiResponse.java
│       │               │   ├── CreateInquiryRequest.java
│       │               │   └── StatusUpdateRequest.java
│       │               ├── entity/
│       │               │   └── TradeInquiry.java
│       │               ├── exception/
│       │               │   ├── GlobalExceptionHandler.java
│       │               │   ├── InquiryNotFoundException.java
│       │               │   └── InvalidStatusException.java
│       │               ├── repository/
│       │               │   └── TradeInquiryRepository.java
│       │               └── service/
│       │                   └── TradeInquiryService.java
│       └── resources/
│           └── application.properties
└── test_api.sh
```

### 2. Build and Run
```bash
# Build the project
./gradlew build

# Run the application
./gradlew bootRun
```

The API will be available at `http://localhost:8080`

### 3. Database Access
- **H2 Console**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:tradedb`
- **Username**: `sa`
- **Password**: `password`

## 📚 API Endpoints

### Base URL: `http://localhost:8080/api/inquiries`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/inquiries` | Create new inquiry |
| `GET` | `/api/inquiries` | Get all inquiries |
| `GET` | `/api/inquiries?status={status}` | Filter inquiries by status |
| `GET` | `/api/inquiries/{id}` | Get specific inquiry |
| `PUT` | `/api/inquiries/{id}/status` | Update inquiry status |
| `GET` | `/api/inquiries/statistics` | Get inquiry statistics |
| `DELETE` | `/api/inquiries/{id}` | Delete inquiry |
| `GET` | `/api/inquiries/health` | Health check |

## 📝 API Usage Examples

### Create New Inquiry
```bash
curl -X POST "http://localhost:8080/api/inquiries" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Kenyan Avocados Export",
    "description": "Premium Hass avocados for European markets",
    "submittingPartner": "KEPROBA",
    "estimatedValue": 250000.00
  }'
```

### Get All Inquiries
```bash
curl -X GET "http://localhost:8080/api/inquiries"
```

### Filter by Status
```bash
curl -X GET "http://localhost:8080/api/inquiries?status=PENDING_VERIFICATION"
```

### Update Status
```bash
curl -X PUT "http://localhost:8080/api/inquiries/1/status" \
  -H "Content-Type: application/json" \
  -d '{"status": "VERIFIED"}'
```

## 🔧 Configuration

### Database Configuration
The application uses H2 in-memory database for development. For production, update `application.properties`:

```properties
# PostgreSQL Example
spring.datasource.url=jdbc:postgresql://localhost:5432/tradedb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

### Valid Status Values
- `PENDING_VERIFICATION` (default for new inquiries)
- `VERIFIED`
- `REJECTED`

## 🧪 Testing

### Run the Test Suite
```bash
chmod +x test_api.sh
./test_api.sh
```

### Manual Testing
1. Start the application: `./gradlew bootRun`
2. Visit `http://localhost:8080/api/inquiries/health` to verify it's running
3. Use the provided cURL examples or tools like Postman

## 📊 Sample Data

The application automatically loads sample data on startup:
- 5 sample trade inquiries
- Different statuses for testing
- Various African trade partners
- Realistic trade scenarios

## 🛡️ Error Handling

The API provides comprehensive error handling:
- **400 Bad Request**: Validation errors, invalid status
- **404 Not Found**: Inquiry not found
- **500 Internal Server Error**: Unexpected errors

Example error response:
```json
{
  "success": false,
  "error": "Inquiry not found with id: 999"
}
```

## 🔄 Business Logic

### Status Transitions
- New inquiries default to `PENDING_VERIFICATION`
- Analysts can change status to `VERIFIED` or `REJECTED`
- Certain transitions are restricted (e.g., VERIFIED → PENDING_VERIFICATION)

### Validation Rules
- `title` and `submittingPartner` are required
- `estimatedValue` must be positive or zero
- `status` must be one of the valid values
# Habit Journal API Documentation

## Base URL
```
http://localhost:8080/api
```

## Authentication

The API uses JWT (JSON Web Token) authentication. Include the token in the Authorization header:

```
Authorization: Bearer <your-jwt-token>
```

### Public Endpoints (No Authentication Required)

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and receive JWT token

### Protected Endpoints

All other endpoints require a valid JWT token.

---

## Authentication Endpoints

### Register User

**POST** `/api/auth/register`

Register a new user account and automatically create their journal.

**Request Body:**
```json
{
  "username": "johndoe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "password": "securePassword123",
  "confirmPassword": "securePassword123"
}
```

**Response:** `201 CREATED`
```json
{
  "message": "User registered successfully",
  "username": "johndoe",
  "email": "john@example.com"
}
```

**Validation Rules:**
- Username: 3-50 characters, required
- Password: Minimum 6 characters, required
- Password and confirmPassword must match
- Email must be valid format

---

### Login

**POST** `/api/auth/login`

Authenticate and receive a JWT token.

**Request Body:**
```json
{
  "username": "johndoe",
  "password": "securePassword123"
}
```

**Response:** `200 OK`
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "username": "johndoe",
  "email": "john@example.com"
}
```

---

## User Endpoints

### Get All Users (Admin Only)

**GET** `/api/users`

Retrieve all registered users.

**Response:** `200 OK`
```json
[
  {
    "id": "507f1f77bcf86cd799439011",
    "username": "johndoe",
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "roles": ["ROLE_USER"],
    "enabled": true,
    "createdAt": "2025-11-14T10:30:00",
    "lastLogin": "2025-11-14T15:45:00"
  }
]
```

---

### Get User by ID

**GET** `/api/users/{id}`

**Response:** `200 OK`

---

### Get User by Username

**GET** `/api/users/username/{username}`

**Response:** `200 OK`

---

### Update User

**PUT** `/api/users/{id}`

Update user profile information.

**Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Smith",
  "email": "johnsmith@example.com"
}
```

**Response:** `200 OK`

---

### Delete User (Admin Only)

**DELETE** `/api/users/{id}`

**Response:** `204 NO CONTENT`

---

## Journal Endpoints

### Create Journal

**POST** `/api/journals?username={username}`

Create a new journal for a user (automatically done during registration).

**Response:** `201 CREATED`

---

### Get Journal by ID

**GET** `/api/journals/{id}`

**Response:** `200 OK`
```json
{
  "id": "507f1f77bcf86cd799439011",
  "username": "johndoe",
  "entries": ["entry_id_1", "entry_id_2"],
  "habits": ["habit_id_1", "habit_id_2"],
  "milestones": ["milestone_id_1"]
}
```

---

### Get Journal by Username

**GET** `/api/journals/user/{username}`

**Response:** `200 OK`

---

### Get All Habits in Journal

**GET** `/api/journals/{id}/habits`

**Response:** `200 OK`

---

### Get All Entries in Journal

**GET** `/api/journals/{id}/entries`

**Response:** `200 OK`

---

### Get All Milestones in Journal

**GET** `/api/journals/{id}/milestones`

**Response:** `200 OK`

---

### Delete Journal

**DELETE** `/api/journals/{id}`

**Response:** `204 NO CONTENT`

---

## Habit Endpoints

### Create Habit

**POST** `/api/habits`

Create a new habit.

**Request Body:**
```json
{
  "name": "Morning Exercise",
  "journalId": "507f1f77bcf86cd799439011",
  "description": "30 minutes of cardio",
  "goal": "Daily workout",
  "recurring": true,
  "active": true
}
```

**Response:** `201 CREATED`
```json
{
  "id": "507f191e810c19729de860ea",
  "name": "Morning Exercise",
  "journalId": "507f1f77bcf86cd799439011",
  "description": "30 minutes of cardio",
  "goal": "Daily workout",
  "recurring": true,
  "active": true
}
```

---

### Get Habit by ID

**GET** `/api/habits/{id}`

**Response:** `200 OK`

---

### Get All Habits (with filters)

**GET** `/api/habits?journalId={journalId}&activeOnly={true/false}&recurringOnly={true/false}`

**Query Parameters:**
- `journalId` (optional) - Filter by journal
- `activeOnly` (optional, default: false) - Only return active habits
- `recurringOnly` (optional, default: false) - Only return recurring habits

**Response:** `200 OK`

---

### Update Habit

**PUT** `/api/habits/{id}`

**Request Body:**
```json
{
  "name": "Evening Exercise",
  "description": "45 minutes of yoga",
  "active": true
}
```

**Response:** `200 OK`

---

### Toggle Habit Active Status

**PATCH** `/api/habits/{id}/toggle-active`

Toggle the active status of a habit.

**Response:** `200 OK`

---

### Delete Habit

**DELETE** `/api/habits/{id}`

**Response:** `204 NO CONTENT`

---

## Entry Endpoints

### Create Entry

**POST** `/api/entries`

Create a journal entry.

**Request Body:**
```json
{
  "journalId": "507f1f77bcf86cd799439011",
  "habitId": "507f191e810c19729de860ea",
  "body": "Completed my morning workout. Feeling energized!",
  "timestamp": "2025-11-14T07:30:00"
}
```

**Response:** `201 CREATED`

---

### Get Entry by ID

**GET** `/api/entries/{id}`

**Response:** `200 OK`

---

### Get All Entries (with filters)

**GET** `/api/entries?journalId={journalId}&habitId={habitId}&startDate={startDate}&endDate={endDate}`

**Query Parameters:**
- `journalId` (optional) - Filter by journal
- `habitId` (optional) - Filter by habit
- `startDate` (optional, ISO 8601 format) - Filter entries after this date
- `endDate` (optional, ISO 8601 format) - Filter entries before this date

**Example:**
```
GET /api/entries?journalId=123&startDate=2025-11-01T00:00:00&endDate=2025-11-30T23:59:59
```

**Response:** `200 OK`

---

### Update Entry

**PUT** `/api/entries/{id}`

**Request Body:**
```json
{
  "body": "Updated entry text",
  "habitId": "new_habit_id"
}
```

**Response:** `200 OK`

---

### Delete Entry

**DELETE** `/api/entries/{id}`

**Response:** `204 NO CONTENT`

---

### Delete Multiple Entries

**DELETE** `/api/entries/bulk?ids=id1,id2,id3`

**Query Parameters:**
- `ids` - Comma-separated list of entry IDs to delete

**Response:** `204 NO CONTENT`

---

## Milestone Endpoints

### Create Milestone

**POST** `/api/milestones`

Create a new milestone for tracking habit progress.

**Request Body:**
```json
{
  "name": "30 Day Exercise Streak",
  "description": "Complete workout for 30 consecutive days",
  "habitId": "507f191e810c19729de860ea",
  "journalId": "507f1f77bcf86cd799439011",
  "goalUnits": 30,
  "dueDate": "2025-12-14",
  "status": "NOT_STARTED"
}
```

**Response:** `201 CREATED`
```json
{
  "id": "507f1f77bcf86cd799439012",
  "name": "30 Day Exercise Streak",
  "description": "Complete workout for 30 consecutive days",
  "habitId": "507f191e810c19729de860ea",
  "journalId": "507f1f77bcf86cd799439011",
  "goalUnits": 30,
  "completedUnits": 0,
  "startDate": "2025-11-14",
  "dueDate": "2025-12-14",
  "status": "NOT_STARTED",
  "completedDate": null
}
```

---

### Get Milestone by ID

**GET** `/api/milestones/{id}`

**Response:** `200 OK`

---

### Get All Milestones (with filters)

**GET** `/api/milestones?journalId={journalId}&habitId={habitId}&status={status}`

**Query Parameters:**
- `journalId` (optional) - Filter by journal
- `habitId` (optional) - Filter by habit
- `status` (optional) - Filter by status (NOT_STARTED, IN_PROGRESS, COMPLETED, FAILED, CANCELLED)

**Response:** `200 OK`

---

### Update Milestone

**PUT** `/api/milestones/{id}`

**Request Body:**
```json
{
  "name": "Updated milestone name",
  "goalUnits": 45,
  "dueDate": "2025-12-31"
}
```

**Response:** `200 OK`

---

### Increment Milestone Progress

**PATCH** `/api/milestones/{id}/increment`

Increment the completed units by 1. Automatically updates status when goal is reached.

**Response:** `200 OK`
```json
{
  "id": "507f1f77bcf86cd799439012",
  "completedUnits": 15,
  "goalUnits": 30,
  "status": "IN_PROGRESS"
}
```

---

### Update Milestone Status

**PATCH** `/api/milestones/{id}/status`

Manually update milestone status.

**Request Body:**
```json
"COMPLETED"
```

**Valid Status Values:**
- `NOT_STARTED`
- `IN_PROGRESS`
- `COMPLETED`
- `FAILED`
- `CANCELLED`

**Response:** `200 OK`

---

### Delete Milestone

**DELETE** `/api/milestones/{id}`

**Response:** `204 NO CONTENT`

---

## Error Responses

All errors follow this format:

```json
{
  "timestamp": "2025-11-14T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Habit not found with id: '123'",
  "path": "/api/habits/123",
  "details": []
}
```

### Common HTTP Status Codes

- `200 OK` - Successful GET, PUT, PATCH requests
- `201 CREATED` - Successful POST (resource created)
- `204 NO CONTENT` - Successful DELETE
- `400 BAD REQUEST` - Invalid request data or validation errors
- `401 UNAUTHORIZED` - Missing or invalid JWT token
- `403 FORBIDDEN` - Insufficient permissions
- `404 NOT FOUND` - Resource not found
- `409 CONFLICT` - Duplicate resource (e.g., username already exists)
- `500 INTERNAL SERVER ERROR` - Server error

---

## Validation Error Example

When validation fails, you'll receive a `400 BAD REQUEST` with detailed field errors:

```json
{
  "timestamp": "2025-11-14T10:30:00",
  "status": 400,
  "error": "Validation Failed",
  "message": "Input validation failed",
  "path": "/api/habits",
  "details": [
    "name: Name is required.",
    "description: Description is required."
  ]
}
```

---

## Environment Variables

The following environment variables can be configured:

| Variable | Description | Default |
|----------|-------------|---------|
| `MONGODB_URI` | MongoDB connection string | MongoDB Atlas instance |
| `MONGODB_DATABASE` | Database name | `habitjournal` |
| `JWT_SECRET` | Secret key for JWT signing | Dev default (change in production!) |
| `JWT_EXPIRATION` | Token expiration in milliseconds | 86400000 (24 hours) |
| `PORT` | Server port | 8080 |

---

## Getting Started

1. **Register a new user:**
   ```bash
   curl -X POST http://localhost:8080/api/auth/register \
     -H "Content-Type: application/json" \
     -d '{
       "username": "johndoe",
       "email": "john@example.com",
       "firstName": "John",
       "lastName": "Doe",
       "password": "password123",
       "confirmPassword": "password123"
     }'
   ```

2. **Login to receive JWT token:**
   ```bash
   curl -X POST http://localhost:8080/api/auth/login \
     -H "Content-Type: application/json" \
     -d '{
       "username": "johndoe",
       "password": "password123"
     }'
   ```

3. **Use the token for authenticated requests:**
   ```bash
   curl -X GET http://localhost:8080/api/habits \
     -H "Authorization: Bearer <your-jwt-token>"
   ```

---

## Notes

- All timestamps use ISO 8601 format: `2025-11-14T10:30:00`
- All IDs are MongoDB ObjectId strings
- Passwords are hashed using BCrypt
- JWT tokens expire after 24 hours by default
- The journal is automatically created when a user registers

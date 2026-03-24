# Fitness Class Booking System - Backend API Documentation

**Version:** 1.0
**Last Updated:** March 23, 2026
**Base URL:** `http://localhost:8080`

---

## Table of Contents

1. [Overview](#1-overview)
2. [User Roles](#2-user-roles)
3. [UML Class Diagram](#3-uml-class-diagram)
4. [API Endpoints](#4-api-endpoints)
   - [Instructor Management](#instructor-management)
   - [Student Management](#student-management)
   - [Class Template Management](#class-template-management)
   - [Class Session Management](#class-session-management)
   - [Enrollment Management](#enrollment-management)
   - [Student Schedule Management](#student-schedule-management)
   - [Review Management](#review-management)
5. [Use Case Mapping](#5-use-case-mapping)

---

## 1. Overview

The Fitness Class Booking System Backend API provides a RESTful interface for managing:

- **User Accounts**: Instructor and Student roles
- **Class Templates**: Reusable class definitions for fitness offerings
- **Class Sessions**: Scheduled offerings of class templates at specific dates and times
- **Enrollments**: Student registration records for class sessions
- **Student Schedules**: Booking records and booking status tracking for students
- **Reviews**: Student feedback on classes and instructor replies

---

## 2. User Roles

The API supports two primary user roles:

| Role | Description | Primary Responsibilities |
|------|-------------|-------------------------|
| **INSTRUCTOR** | Creator and manager of fitness classes | Create/manage class templates, manage sessions, view rosters, reply to reviews |
| **STUDENT** | Participant in fitness classes | Register account, enroll in classes, manage schedule, write reviews |

---

## 3. UML Class Diagram

![UML Class Diagram](../docs/uml-class.png)

## 4. API Endpoints

**Note:** Users are created through role-specific endpoints (`/instructors` and `/api/students`), not through a generic `/users` endpoint. This ensures proper role assignment and role-specific attributes.

### Instructor Management

#### Create Instructor

**Endpoint:** `POST /instructors`
**Use Case:** US-INS-001 (Register as Instructor)
**Description:** Create a new instructor account.

```http
POST /instructors
Content-Type: application/json

{
  "email": "alice@uncg.edu",
  "name": "Alice Smith",
  "passwordHash": "alicehash",
  "role": "INSTRUCTOR"
}
```

**Response:**

```json
{
  "userId": 1,
  "email": "alice@uncg.edu",
  "passwordHash": "alicehash",
  "role": "INSTRUCTOR",
  "name": "Alice Smith"
}
```

**Status Code:** `201 Created`

---

#### Get All Instructors

**Endpoint:** `GET /instructors`
**Use Case:** Browse instructors
**Description:** Retrieve all instructor accounts.

```http
GET /instructors
```

**Status Code:** `200 OK`

---

#### Get Instructor by ID

**Endpoint:** `GET /instructors/{userId}`
**Use Case:** Instructor profile view
**Description:** Retrieve specific instructor by ID.

```http
GET /instructors/1
```

**Status Code:** `200 OK` or `404 Not Found`

---

#### Update Instructor

**Endpoint:** `PUT /instructors/{userId}`
**Use Case:** US-INS-001 (Update Profile)
**Description:** Update instructor profile information.

```http
PUT /instructors/2
Content-Type: application/json

{
  "email": "bob_new@uncg.edu"
}
```

**Response:** Updated instructor object

**Status Code:** `200 OK` or `404 Not Found`

---

#### Delete Instructor

**Endpoint:** `DELETE /instructors/{userId}`
**Use Case:** Account deletion
**Description:** Delete instructor account.

```http
DELETE /instructors/3
```

**Status Code:** `204 No Content` or `404 Not Found`

---

### Student Management

#### Create Student

**Endpoint:** `POST /api/students`
**Use Case:** US-STU-001 (Register as Student)
**Description:** Create a new student account.

```http
POST /api/students
Content-Type: application/json

{
  "email": "dana@uncg.edu",
  "name": "Dana Green",
  "passwordHash": "danahash",
  "role": "STUDENT"
}
```

**Response:**

```json
{
  "userId": 4,
  "email": "dana@uncg.edu",
  "passwordHash": "danahash",
  "role": "STUDENT",
  "name": "Dana Green",
  "studentSchedules": [],
  "reviews": []
}
```

**Status Code:** `201 Created`

---

#### Get All Students

**Endpoint:** `GET /api/students`
**Use Case:** Student management
**Description:** Retrieve all student accounts.

```http
GET /api/students
```

**Status Code:** `200 OK`

---

#### Get Student by ID

**Endpoint:** `GET /api/students/{id}`
**Use Case:** Student profile view
**Description:** Retrieve specific student by ID.

```http
GET /api/students/4
```

**Status Code:** `200 OK` or `404 Not Found`

---

#### Get Student by Email

**Endpoint:** `GET /api/students/email/{email}`
**Use Case:** Student lookup
**Description:** Retrieve student by email address.

```http
GET /api/students/email/dana@uncg.edu
```

**Status Code:** `200 OK` or `404 Not Found`

---

#### Update Student

**Endpoint:** `PUT /api/students/{id}`
**Use Case:** US-STU-001 (Update Profile)
**Description:** Update student profile information.

```http
PUT /api/students/5
Content-Type: application/json

{
  "email": "eli_new@uncg.edu"
}
```

**Response:** Updated student object

**Status Code:** `200 OK`, `400 Bad Request`, or `404 Not Found`

---

#### Delete Student

**Endpoint:** `DELETE /api/students/{id}`
**Use Case:** Account deletion
**Description:** Delete student account.

```http
DELETE /api/students/6
```

**Status Code:** `204 No Content`

---

### Class Template Management

#### Create Class Template

**Endpoint:** `POST /classTemplates`
**Use Case:** US-INS-002 (Create Class Template)
**Description:** Create a new class template.

```http
POST /classTemplates
Content-Type: application/json

{
  "classType": "YOGA",
  "description": "Beginner yoga class",
  "duration": 60,
  "instructor": {
    "userId": 1
  },
  "intensity": "MEDIUM",
  "price": 19.99,
  "title": "Intro Yoga"
}
```

**Response:**

```json
{
  "classTemplateId": 1,
  "instructor": {
    "userId": 1
  },
  "title": "Intro Yoga",
  "classType": "YOGA",
  "intensity": "MEDIUM",
  "duration": 60,
  "createdAt": "2026-03-23T10:30:00",
  "updatedAt": "2026-03-23T10:30:00",
  "price": 19.99,
  "description": "Beginner yoga class"
}
```

**Status Code:** `201 Created`

---

#### Get All Class Templates

**Endpoint:** `GET /classTemplates`
**Use Case:** Browse class templates
**Description:** Retrieve all class templates.

```http
GET /classTemplates
```

**Status Code:** `200 OK`

---

#### Get Class Template by ID

**Endpoint:** `GET /classTemplates/{templateId}`
**Use Case:** View class template details
**Description:** Retrieve specific class template.

```http
GET /classTemplates/1
```

**Status Code:** `200 OK` or `404 Not Found`

---

#### Get Class Templates by Instructor ID

**Endpoint:** `GET /classTemplates/instructor/{userId}`
**Use Case:** US-INS-003 (View Instructor Templates)
**Description:** Retrieve all class templates created by a specific instructor.

```http
GET /classTemplates/instructor/1
```

**Response:** Array of class templates

**Status Code:** `200 OK`

---

#### Update Class Template

**Endpoint:** `PUT /classTemplates/{templateId}`
**Use Case:** US-INS-004 (Update Class Template)
**Description:** Update class template information.

```http
PUT /classTemplates/2
Content-Type: application/json

{
  "description": "Updated cardio workout description"
}
```

**Response:** Updated class template object

**Status Code:** `200 OK` or `404 Not Found`

---

#### Delete Class Template

**Endpoint:** `DELETE /classTemplates/{templateId}`
**Use Case:** Class template removal
**Description:** Delete class template.

```http
DELETE /classTemplates/3
```

**Status Code:** `204 No Content` or `404 Not Found`

---

### Class Session Management

#### Create Class Session

**Endpoint:** `POST /classSessions`
**Use Case:** US-INS-005 (Schedule Class Session)
**Description:** Create a new scheduled class session.

```http
POST /classSessions
Content-Type: application/json

{
  "classTemplate": {
    "classTemplateId": 1
  },
  "scheduledAt": "2026-04-20T10:00:00"
}
```

**Response:**

```json
{
  "classSessionId": 1,
  "scheduledAt": "2026-04-20T10:00:00",
  "classTemplate": {
    "classTemplateId": 1
  }
}
```

**Status Code:** `201 Created` or `400 Bad Request`

---

#### Get All Class Sessions

**Endpoint:** `GET /classSessions`
**Use Case:** Browse scheduled sessions
**Description:** Retrieve all class sessions.

```http
GET /classSessions
```

**Status Code:** `200 OK`

---

#### Get Class Session by ID

**Endpoint:** `GET /classSessions/{classSessionId}`
**Use Case:** Session detail view
**Description:** Retrieve specific class session by ID.

```http
GET /classSessions/1
```

**Status Code:** `200 OK` or `404 Not Found`

---

#### Get Class Sessions by Class Template ID

**Endpoint:** `GET /classSessions/classTemplate/{classTemplateId}`
**Use Case:** View session schedule for a class template
**Description:** Retrieve all scheduled sessions for a specific class template.

```http
GET /classSessions/classTemplate/1
```

**Response:** Array of class sessions

**Status Code:** `200 OK`

---

#### Update Class Session

**Endpoint:** `PUT /classSessions/{classSessionId}`
**Use Case:** US-INS-006 (Reschedule Class Session)
**Description:** Update class session information.

```http
PUT /classSessions/2
Content-Type: application/json

{
  "scheduledAt": "2026-04-21T16:00:00"
}
```

**Response:** Updated class session object

**Status Code:** `200 OK` or `404 Not Found`

---

#### Delete Class Session

**Endpoint:** `DELETE /classSessions/{classSessionId}`
**Use Case:** Session cancellation
**Description:** Delete class session.

```http
DELETE /classSessions/3
```

**Status Code:** `204 No Content` or `404 Not Found`

---

### Enrollment Management

#### Create Enrollment

**Endpoint:** `POST /enrollments`
**Use Case:** US-STU-002 (Enroll in Class Session)
**Description:** Create a new enrollment for a student in a class session.

```http
POST /enrollments
Content-Type: application/json

{
  "classSession": {
    "classSessionId": 1
  },
  "student": {
    "userId": 4
  }
}
```

**Response:**

```json
{
  "enrollmentId": 1,
  "classSession": {
    "classSessionId": 1
  },
  "student": {
    "userId": 4
  }
}
```

**Status Code:** `201 Created` or `400 Bad Request`

---

#### Get All Enrollments

**Endpoint:** `GET /enrollments`
**Use Case:** Enrollment management
**Description:** Retrieve all enrollments.

```http
GET /enrollments
```

**Status Code:** `200 OK`

---

#### Get Enrollments by Class Session ID

**Endpoint:** `GET /enrollments/classSession/{classSessionId}`
**Use Case:** US-INS-007 (View Session Roster)
**Description:** Retrieve all enrollments for a specific class session.

```http
GET /enrollments/classSession/1
```

**Response:** Array of enrollments

**Status Code:** `200 OK`

---

#### Get Enrollments by Student ID

**Endpoint:** `GET /enrollments/student/{studentId}`
**Use Case:** US-STU-003 (View Student Enrollments)
**Description:** Retrieve all enrollments for a specific student.

```http
GET /enrollments/student/5
```

**Response:** Array of enrollments

**Status Code:** `200 OK`

---

#### Delete Enrollment

**Endpoint:** `DELETE /enrollments/{enrollmentId}`
**Use Case:** US-STU-004 (Drop Class Session)
**Description:** Delete an enrollment record.

```http
DELETE /enrollments/1
```

**Status Code:** `204 No Content`

---

### Student Schedule Management

#### Create Student Schedule

**Endpoint:** `POST /api/student-schedules`
**Use Case:** US-STU-005 (Add Session to Schedule)
**Description:** Create a student schedule entry with booking status.

```http
POST /api/student-schedules
Content-Type: application/json

{
  "bookingStatus": "ENROLLED",
  "classSession": {
    "classSessionId": 1
  },
  "enrolledAt": "2026-04-20T09:00:00",
  "student": {
    "userId": 4
  }
}
```

**Response:**

```json
{
  "studentScheduleId": 1,
  "student": {
    "userId": 4
  },
  "classSession": {
    "classSessionId": 1
  },
  "enrolledAt": "2026-04-20T09:00:00",
  "bookingStatus": "ENROLLED"
}
```

**Status Code:** `201 Created` or `400 Bad Request`

---

#### Get All Student Schedules

**Endpoint:** `GET /api/student-schedules`
**Use Case:** Student schedule overview
**Description:** Retrieve all student schedule records.

```http
GET /api/student-schedules
```

**Status Code:** `200 OK`

---

#### Get Student Schedule by ID

**Endpoint:** `GET /api/student-schedules/{id}`
**Use Case:** Student schedule detail view
**Description:** Retrieve specific student schedule record.

```http
GET /api/student-schedules/1
```

**Status Code:** `200 OK` or `404 Not Found`

---

#### Delete Student Schedule

**Endpoint:** `DELETE /api/student-schedules/{id}`
**Use Case:** Remove class from schedule
**Description:** Delete student schedule entry.

```http
DELETE /api/student-schedules/2
```

**Status Code:** `204 No Content`

---

### Review Management

#### Create Review

**Endpoint:** `POST /api/reviews`
**Use Case:** US-STU-006 (Write a Review)
**Description:** Create a new review for a class template.

```http
POST /api/reviews
Content-Type: application/json

{
  "classTemplate": {
    "classTemplateId": 1
  },
  "comment": "Great class for beginners",
  "rating": 5,
  "replyText": null,
  "student": {
    "userId": 4
  }
}
```

**Response:**

```json
{
  "reviewId": 1,
  "student": {
    "userId": 4
  },
  "classTemplate": {
    "classTemplateId": 1
  },
  "rating": 5,
  "comment": "Great class for beginners",
  "replyText": null
}
```

**Status Code:** `201 Created`

---

#### Get All Reviews

**Endpoint:** `GET /api/reviews`
**Use Case:** US-STU-007 (Read Reviews)
**Description:** Retrieve all reviews in the system.

```http
GET /api/reviews
```

**Status Code:** `200 OK`

---

#### Get Review by ID

**Endpoint:** `GET /api/reviews/{id}`
**Use Case:** Review detail view
**Description:** Retrieve specific review.

```http
GET /api/reviews/1
```

**Status Code:** `200 OK` or `404 Not Found`

---

#### Get Reviews by Class Template ID

**Endpoint:** `GET /api/reviews/class-template/{classTemplateId}`
**Use Case:** US-STU-007 (Read Reviews for Class Template)
**Description:** Retrieve all reviews for a specific class template.

```http
GET /api/reviews/class-template/1
```

**Response:** Array of reviews

**Status Code:** `200 OK`

---

#### Update Review

**Endpoint:** `PUT /api/reviews/{id}`
**Use Case:** US-INS-008 (Reply to Reviews), US-STU-008 (Edit Review)
**Description:** Update a review's rating, comment, or reply text.

```http
PUT /api/reviews/1
Content-Type: application/json

{
  "replyText": "Thank you for the feedback!"
}
```

**Response:** Updated review object

**Status Code:** `200 OK`, `400 Bad Request`, or `404 Not Found`

---

#### Delete Review

**Endpoint:** `DELETE /api/reviews/{id}`
**Use Case:** Review removal
**Description:** Delete a review.

```http
DELETE /api/reviews/2
```

**Status Code:** `204 No Content`

---

## 5. Use Case Mapping

The API endpoints are designed to support the following use cases:

### Student Use Cases

| Use Case | Description | Related Endpoints |
|----------|-------------|-------------------|
| **US-STU-001** | Register & manage student profile | `POST /api/students`, `GET /api/students/{id}`, `GET /api/students/email/{email}`, `PUT /api/students/{id}`, `DELETE /api/students/{id}` |
| **US-STU-002** | Enroll in a class session | `POST /enrollments` |
| **US-STU-003** | View student enrollments | `GET /enrollments/student/{studentId}` |
| **US-STU-004** | Drop a class | `DELETE /enrollments/{enrollmentId}` |
| **US-STU-005** | Manage booked sessions in schedule | `POST /api/student-schedules`, `GET /api/student-schedules`, `GET /api/student-schedules/{id}`, `DELETE /api/student-schedules/{id}` |
| **US-STU-006** | Write a review | `POST /api/reviews` |
| **US-STU-007** | Read reviews | `GET /api/reviews`, `GET /api/reviews/{id}`, `GET /api/reviews/class-template/{classTemplateId}` |
| **US-STU-008** | Edit or remove a review | `PUT /api/reviews/{id}`, `DELETE /api/reviews/{id}` |

### Instructor Use Cases

| Use Case | Description | Related Endpoints |
|----------|-------------|-------------------|
| **US-INS-001** | Register & manage instructor profile | `POST /instructors`, `GET /instructors/{userId}`, `PUT /instructors/{userId}`, `DELETE /instructors/{userId}` |
| **US-INS-002** | Create class template | `POST /classTemplates` |
| **US-INS-003** | View instructor-owned templates | `GET /classTemplates/instructor/{userId}` |
| **US-INS-004** | Update class template information | `PUT /classTemplates/{templateId}` |
| **US-INS-005** | Schedule class session | `POST /classSessions` |
| **US-INS-006** | Reschedule or update a session | `PUT /classSessions/{classSessionId}` |
| **US-INS-007** | View enrolled students for a session | `GET /enrollments/classSession/{classSessionId}` |
| **US-INS-008** | Reply to student reviews | `PUT /api/reviews/{id}` |

### General System Operations

| Use Case | Description | Related Endpoints |
|----------|-------------|-------------------|
| **SYS-001** | Browse all instructors and students | `GET /instructors`, `GET /api/students` |
| **SYS-002** | Browse all class templates and sessions | `GET /classTemplates`, `GET /classTemplates/{templateId}`, `GET /classSessions`, `GET /classSessions/{classSessionId}`, `GET /classSessions/classTemplate/{classTemplateId}` |
| **SYS-003** | Browse all enrollments and schedules | `GET /enrollments`, `GET /api/student-schedules` |
| **SYS-004** | Browse all reviews | `GET /api/reviews` |

---

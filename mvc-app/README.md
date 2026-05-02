# Gym Class Hub - MVC Application

A Spring MVC web application managing gym classes, allowing students to browse classes, enroll in sessions, and leave reviews.

## Architecture Overview

This application follows the **Model-View-Controller (MVC)** pattern:

### Models (Entities)
Located in `src/main/java/edu/UNCG/sp26team7/entity/`

- **User** - Base entity for authentication (abstract parent class)
- **Instructor** - 
- **Student** - Extends User; manages class enrollments and reviews
- **ClassSession** - 
- **ClassTemplate** - 
- **StudentSchedule** - Tracks student enrollments in class sessions
- **Review** - Customer feedback on class templates with instructor replies

### navbar
Located in `src/main/resources/static/assets`

**navbar:**
- `js/include-navbar.js` - Reusable navigation partial (macro-based for instructor/student)
- `partials/nav-root.html` - navigation bar (root)
- `partials/nav-student.html` - navigation bar (student)

### Views (Templates)
Located in `src/main/resources/templates/`

**Student Views:**
- `student/home.ftlh` - Student home page
- `student/browse.ftlh` - Browse available class sessions
- `student/class-detail.ftlh` - View class details and reviews
- `student/my-classes.ftlh` - View enrollments and cancel
- `student/leave-review.ftlh` - Student submitted reviews with instructor replies
- `student/edit-profile.ftlh` - Edit student profile
- `student/profile.ftlh` - View student profile

**Instructor Views:**
- `farmer/dashboard.ftlh` - Farm stats, latest reviews, subscription overview
- `farmer/product-management.ftlh` - Manage produce boxes with edit/delete modals
- `farmer/farm-settings.ftlh` - Update farm information
- `farmer/profile-settings.ftlh` - Update farmer profile
- `farmer/review-management.ftlh` - View all farm reviews and add replies
- `farmer/edit-box.ftlh` - Edit produce box details
- `farmer/new-box.ftlh` - Create new produce box
- `farmer/farm-setup.ftlh` - Initial farm setup

**Public Pages:**
- `signin.ftlh` - Authentication page
- `signup.ftlh` - User registration page

### Controllers

**API Controllers** - RESTful endpoints for data operations:
- `StudentController` - Customer CRUD operations
- `StudentScheduleController` - Student schedule CRUD operations
- `ReviewController` - Review management with replies

**UI Controllers** - Page rendering and navigation:
- `AppUiController` - Public pages (auth)
- `FarmerUiController` - Farmer dashboard and all farmer views
- `StudentUiController` - Student home and all student views

### Services
Located in `src/main/java/edu/UNCG/sp26team7/service/`

Business logic layer providing CRUD operations and domain-specific functionality:
- `StudentService` - Student registration, profile updates, account management
- `StudentScheduleService` - Enrollment creation and cancellation
- `ReviewService` - Review submission and retrieval

### Repositories
Located in `src/main/java/edu/UNCG/sp26team7/repository/`

Data access layer interfacing with the database (Spring Data JPA):
- `StudentRepository` - Student lookups and queries
- `StudentScheduleRepository` - Student schedule queries
- `ReviewRepository` - Review queries

## Key Features

### User Roles & Authentication
- **Student**: Browse classes, enroll in classes, leave reviews
- **Farmer**: Create/manage produce boxes, view farm reviews with customer replies, track statistics

### Student Flow
1. Sign up and create student profile
2. Browse available clasess
3. Enroll in a class
4. Manage enrollments (cancel or view)
5. Leave reviews with 5-star ratings and comments
6. View instructors responses to reviews

### Farmer Flow
1. Sign up and complete farm setup
2. Create and manage produce boxes (title, price, season, description)
3. View all subscriptions to their products
4. Monitor farm statistics and metrics
5. View customer reviews for their farm
6. Reply to customer reviews in real-time

### Navigation
All pages use a unified FreeMarker macro-based navbar that automatically adjusts based on:
- User role (instructor/student)
- Authentication status
- Responsive design (Bootstrap 5.3.2)

## Session Management
- Uses `HttpSession` for storing `studentId` and `farmerId`
- Automatic redirect to signin for unauthenticated access to protected pages
- Session validation on all sensitive endpoints

## Database Relationships
- **One-to-Many**: Student → StudentSchedule, ClassTempate → Reviews
- **Many-to-One**: StudentSchedule → Student/ClassSession, Review → Student/ClassTemplate
- **Cascade Operations**: Automatic cascading for related entity changes
- **JsonIgnoreProperties**: Prevents circular reference serialization
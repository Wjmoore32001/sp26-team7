# Gym Class Hub

Gym Class Hub is a semester-long collaborative software engineering project for managing gym class scheduling, enrollment, instructor workflows, and customer reviews.

The application is designed for a local gym with multiple studio rooms. Instead of every class being managed directly by the gym, fitness instructors can create and host their own classes, while gym members can browse available classes, reserve spots, manage enrollments, and leave reviews after attending.

This project was developed as part of CSC 340 at the University of North Carolina at Greensboro.

## Project Overview

Gym Class Hub focuses on the relationship between two main user roles:

* **Students / Customers** — gym members who want to find and reserve fitness classes.
* **Instructors / Providers** — fitness instructors who want to create classes, schedule sessions, view enrollments, and respond to reviews.

The goal of the system is to make class discovery, scheduling, enrollment, and instructor management easier while accounting for time, room availability, class capacity, and user feedback.

## Team Members

* William Moore
* Chloe Rhodes

## My Primary Focus

My main focus for the project was the instructor/provider side of the system, along with supporting backend and integration work.

Areas I worked on included:

* Instructor account and profile flow
* Instructor dashboard and navigation
* Instructor class creation and management
* Scheduling class sessions
* Viewing enrollments for instructor-created classes
* Viewing and replying to reviews
* Class and instructor image support
* Browse/class detail updates across user states
* Safer delete handling for class sessions and templates
* Supporting documentation, testing scenarios, and presentation planning

## Core Features

### Student / Customer Features

Students can:

* Create an account
* Edit profile details
* Browse available fitness classes
* Search or filter classes
* View class details, including time, room, instructor, price, capacity, and reviews
* Reserve a spot in a class session
* Manage upcoming enrolled classes
* View class history
* Leave reviews for classes they have attended
* View instructor replies to reviews

### Instructor / Provider Features

Instructors can:

* Create an instructor account
* Set up and edit an instructor profile
* Add profile images
* Create class templates
* Add class details such as title, type, intensity, duration, price, description, and image
* Schedule created classes into available room/time slots
* View scheduled sessions
* View enrolled students
* Reschedule or cancel sessions
* View customer reviews for their classes
* Reply to customer reviews
* Browse other available classes while hiding their own classes from browse results

### Public / Not Logged In Features

Users who are not logged in can:

* View the public home page
* Access sign-up and login pages
* Browse class information where allowed
* Be redirected to login when trying to perform protected actions such as enrolling or leaving a review

## Tech Stack

### Backend / MVC Application

* Java
* Spring Boot
* Spring MVC
* Spring Data JPA
* PostgreSQL
* H2 Database for testing
* Lombok
* Maven

### Frontend / Templates

* FreeMarker templates
* HTML
* CSS
* Bootstrap
* JavaScript

### Development & Documentation

* Git
* GitHub
* Markdown documentation
* User stories
* Acceptance criteria
* API documentation
* Testing scenarios
* High-fidelity prototype

## Repository Structure

```text
sp26-team7/
├── backend-api/
│   ├── src/
│   ├── requests/
│   ├── README.md
│   └── pom.xml
│
├── docs/
│   ├── GymClassHub.md
│   ├── GCH Reqs Testing Plan.md
│   ├── presentation_guide.md
│   ├── written_scenario.md
│   └── notes.md
│
├── high-fidelity-prototype/
│   ├── assets/
│   ├── customer/
│   ├── instructor/
│   ├── browse.html
│   ├── class-details.html
│   ├── index.html
│   ├── login.html
│   ├── sign-up.html
│   └── README.md
│
├── mvc-app/
│   ├── src/
│   ├── README.md
│   └── pom.xml
│
├── ChloeRhodes.md
├── WilliamMoore.md
└── README.md
```

## Application Architecture

The main application follows a Model-View-Controller architecture.

### Models / Entities

The application uses entities to represent the main data objects in the system, including:

* User
* Student
* Instructor
* ClassTemplate
* ClassSession
* StudentSchedule
* Review

These entities support relationships such as:

* Students enrolling in class sessions
* Class sessions belonging to class templates
* Reviews belonging to class templates and students
* Instructors creating and managing class templates
* Students maintaining schedules through enrollment records

### Controllers

The project includes both UI controllers and API-style controllers.

Controller responsibilities include:

* Rendering public pages
* Handling sign-up and login navigation
* Routing students to student-specific pages
* Routing instructors to instructor-specific pages
* Managing class browse/detail views
* Supporting student enrollments
* Supporting review submission and instructor replies
* Supporting class/session management actions

### Services

Service classes handle business logic such as:

* Student registration and profile updates
* Enrollment creation and cancellation
* Review submission and retrieval
* Class/session management
* Delete flows that require related records to be handled safely

### Repositories

Spring Data JPA repositories handle database access for the application’s entities, including student, schedule, review, class, and instructor-related data.

## High-Fidelity Prototype

The `high-fidelity-prototype/` folder contains a static click-through prototype used to plan the user experience before the full MVC implementation.

The prototype includes:

* Public landing page
* Login page
* Sign-up page
* Browse classes page
* Class details page
* Customer dashboard
* Customer profile
* Customer class history
* Customer review form
* Instructor dashboard
* Instructor profile
* Instructor created classes page
* Instructor scheduled classes page
* Create class form
* Schedule class form
* Shared navigation partials for public, customer, and instructor states

This prototype was used to map out the major user flows before connecting the full backend and database-backed application logic.

## Backend API Summary

The backend API supports the main data operations for the application.

Major API areas include:

### Instructor Management

* Create instructor
* Get all instructors
* Get instructor by ID
* Update instructor profile
* Delete instructor

### Student Management

* Create student
* Get all students
* Get student by ID
* Get student by email
* Update student profile
* Delete student

### Class Template Management

* Create class template
* Get all class templates
* Get class template by ID
* Get class templates by instructor
* Update class template
* Delete class template

### Class Session Management

* Create class session
* Get all class sessions
* Get class session by ID
* Get class sessions by class template
* Update class session
* Delete class session

### Enrollment / Schedule Management

* Create enrollment
* Get enrollments by session
* Get enrollments by student
* Delete enrollment
* Create student schedule record
* Get student schedules
* Delete student schedule record

### Review Management

* Create review
* Get all reviews
* Get review by ID
* Get reviews by class template
* Update review or instructor reply
* Delete review

## Requirements and User Stories

The project was planned using functional requirements and user stories.

### Customer Stories

Customer stories included:

* Register an account
* Manage profile information
* Browse or search available classes
* Enroll in a class
* Leave a review after attending a class

### Instructor Stories

Instructor stories included:

* Register an instructor account
* Update instructor profile
* Create a class
* View enrollments and waitlists
* Reply to customer reviews

### Non-Functional Requirements

The project also included non-functional requirements related to:

* Performance
* Reliability
* Role-based access
* Usability
* Privacy and account protection

## Testing Plan

The project includes documented testing scenarios for major user flows.

Test scenarios include:

* Instructor profile setup
* Instructor profile updates
* Instructor class creation
* Instructor class publishing
* Instructor session scheduling
* Instructor enrollment viewing
* Instructor review replies
* Instructor session rescheduling and cancellation
* Customer account creation
* Customer profile editing
* Customer class browsing
* Customer enrollment
* Customer review submission
* Redirect behavior for protected actions
* Basic performance and usability expectations

## Notable Implementation Work

During the final project increment, additional work included:

* Instructor dashboard page
* Instructor profile page
* Instructor browse page
* Instructor-specific navigation updates
* Instructor browse filters
* Logic to hide an instructor’s own classes from instructor browse results
* Instructor review viewing and reply flow
* Instructor profile image support
* Class image support
* Shared instructor sign-up and login flow
* Safer delete logic for class sessions
* Safer delete logic for class templates
* Transaction handling for delete flows
* Image path handling through the static assets folder
* Student browse updates
* Student class detail updates
* Display of instructor replies on student class detail pages
* Redirect logic for not-logged-in users attempting protected actions
* Functional browse filters for not-logged-in users
* Image display fixes across browse and class detail pages

## Current Limitations

This was a semester-long course project, so some features are intentionally limited or out of scope.

Known limitations and deferred features include:

* No real payment processing
* No real refund system
* No instructor credential verification
* No full production authentication/authorization system
* No deployed production version
* Limited production-level security hardening
* Some prototype content is static or mock data
* Some features were built to satisfy course requirements rather than production deployment requirements

## Future Improvements

Possible future improvements include:

* Deploying a live version of the application
* Improving authentication and authorization
* Adding stronger password handling and account security
* Adding instructor verification
* Adding real payment integration
* Adding class waitlist logic
* Adding email confirmations
* Adding calendar integration
* Improving UI polish and responsiveness
* Adding stronger validation and error handling
* Expanding automated testing
* Adding admin/gym owner functionality
* Improving database seeding and setup documentation

## How to Run Locally

The project contains multiple parts. The most complete application code is located in the `mvc-app/` folder.

### Prerequisites

Install:

* Java 25 or compatible project JDK
* Maven
* PostgreSQL
* Git

### Clone the Repository

```bash
git clone https://github.com/Wjmoore32001/sp26-team7.git
cd sp26-team7
```

### Run the MVC Application

```bash
cd mvc-app
./mvnw spring-boot:run
```

On Windows:

```bash
cd mvc-app
mvnw.cmd spring-boot:run
```

### Database Setup

The application uses PostgreSQL at runtime and H2 for testing.

Before running locally, check the application configuration inside the MVC project and make sure the PostgreSQL database name, username, and password match your local environment.

A typical local setup requires:

* PostgreSQL installed and running
* A local database created for the project
* Application properties updated for your local database credentials

### Run Tests

From the relevant project folder:

```bash
./mvnw test
```

On Windows:

```bash
mvnw.cmd test
```

## Documentation

Additional documentation can be found in the `docs/` folder:

* `GymClassHub.md` — requirements, scope, roles, user stories, and acceptance criteria
* `GCH Reqs Testing Plan.md` — functional and non-functional testing scenarios
* `presentation_guide.md` — final presentation flow
* `notes.md` — implementation notes and final increment changes

The `backend-api/README.md` file documents the REST-style endpoints and use case mapping for the API portion of the project.

The `high-fidelity-prototype/README.md` file documents the static prototype structure and page purposes.

## What I Learned

This project gave me experience with the full software development lifecycle, including:

* Turning an idea into requirements
* Writing user stories and acceptance criteria
* Designing user flows
* Building a high-fidelity prototype
* Working with MVC architecture
* Implementing backend entities and relationships
* Connecting controllers, services, repositories, and templates
* Handling role-based flows for different users
* Working with Git and GitHub in a team project
* Debugging integration issues across frontend, backend, and database-backed logic
* Writing project documentation and testing scenarios

## Project Status

This project is complete for CSC 340 course requirements, but it is not a production application.

The repository is being kept as a portfolio project to demonstrate software engineering coursework, team-based development, Java/Spring MVC experience, backend API design, database-backed application logic, and project documentation.

## License

This repository was created for educational coursework. No formal license has been added.

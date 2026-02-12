
# Requirements

**Project Name:** Gym Class Hub \
**Team:** William Moore - Customer; Chloe Rhodes - Instructor \
**Course:** CSC 340-02\
**Version:** 1.0\
**Date:** 2026-01-30

---

## 1. Overview
**Vision.** Gym Class Hub is a web app for a local gym that has multiple studio rooms.
Instead of the gym running every class, members/fitness instructors can create and host their own classes (yoga, strength, HIIT, etc), view enrollments, and reply to reviews.
Gym members can browse classes, reserve a spot, and leave reviews after attending.
The main goal is to make scheduling and hosting gym classes easy while managing time and room availability.

**Glossary** Terms used in the project
- **Enrolled:** Customer who has reserved their spot in a gym class.
- **Capacity:** Max number of spots available for a given class.
- **Waitlist:** A queue of customers requesting enrollment in a class that is already at max capacity.
- **Class Session:** An instance of a scheduled class that has a date, time, room, student list, and instructor.
- **Student:** Can refer to a customer. In the case of an Instructor viewing the people attending their class, they will be presented as students.

**Primary Users / Roles.**
- **Customer(Takes Gym Classes)** — Gym members who want to browse, reserve, and review fitness classes offered by instructors.
- **Instructor (Offers Gym Classes)** — Fitness instructors who create and manage classes, view enrollments, and engage with customers through reviews.

**Scope (this semester).**
- Create and manage Customer and Instructor accounts.
- Browse classes via search and/or filtering.
- Scheduling of classes sessions with respect to gym room available affected by other classes.
- Reviews; Leaving reviews for customer and replying to reviews for Instructors.

**Out of scope (deferred).**
- Verifying Instructors a proper fitness professionals, qualified to teach classes.
- Completing online payments and refunding.
- Gym equipment allocation/availability between classes.

> This document is **requirements‑level** and solution‑neutral; design decisions (UI layouts, API endpoints, schemas) are documented separately.

---

## 2. Functional Requirements (User Stories)
Write each story as: **As a `<role>`, I want `<capability>`, so that `<benefit>`.** Each story includes at least one **Given/When/Then** scenario.

### 2.1 Customer Stories
- **US-CUST-001 — Register Account**
  _Story_: As a customer, I want to create an account using my username, email, and password so that I can access the system and enroll in classes.
  _Acceptance:_
  ```gherkin
  Scenario: Successful account registration
    Given I am on the registration page
    When I enter a valid username, email, and password
    Then my account is created and I can access the system
  ```
  
- **US-CUST-002 — Manage Profile**
  _Story_: As a customer, I want to edit my profile information so that I can keep my account details up to date.
  _Acceptance:_
  ```gherkin
  Scenario: Update profile information
    Given I have a customer account
    When I update my profile information, like email or password
    Then my updated information is saved to my account
  ```
  
- **US‑CUST‑003 — Search/Filter Classes**  
  _Story:_ As a customer, I want to search and filter classes by type and time so that I can find a class that fits my schedule.
  _Acceptance:_
  ```gherkin
  Scenario: Search classes by search and/or filter
    Given there are multiple upcoming classes
    When  I search a class name or filter by class type
    Then  I only see classes matching my search criteria
  ```
  
  - **US‑CUST‑004 — Join Waitlist**  
    _Story:_ As a customer, I want to join a waitlist when a class is full so that I still have a chance to attend.
    _Acceptance:_
  ```gherkin
  Scenario: Join waitlist for a full class
    Given a class is at max capacity
    When  I attempt to reserve a spot
    Then  I am given the option to join the waitlist
  ```
  
- **US‑CUST‑005 — Leave Reviews on Classes**
- _Story:_ As a customer, I want to leave reviews on classes I have attended, so that I can aid the community in knowing about a class from experience.
  _Acceptance:_
  ```gherkin
  Scenario: Leave a review
    Given I have enrolled in a class and then later taken that class
    When  I leave a review on a class I have taken
    Then it is added to the review section of that class
  ```

### 2.2 Provider(instructor) Stories
- **US‑INST‑001 — Register instructor account**  
  _Story:_ As a instructor, I want to create a instructor account using my username, email, and password so that I can create classes for others to enroll in.  
  _Acceptance:_
  ```gherkin
  Scenario: Successful instructor account registration
    Given I am on the registration page
    When  I enter a valid username, email, and password
    Then  my instructor account is created and I can create classes
  ```
  - **US‑INST‑002 — update instructor profile**  
  _Story:_ As a instructor, I want to update my profile so that my profile is up to date with any changes I may make.  
  _Acceptance:_
  ```gherkin
  Scenario: Update instructor profile
    Given I am a verified instructor
    When  I update my profile information
    Then  my updated information is saved to my account
  ```

- **US‑INST‑003 — Create a class**  
  _Story:_ As a instructor, I want to create classes so that customers can take my classes. 
  _Acceptance:_
  ```gherkin
  Scenario: created a new class
    Given I am a instructor
    When  I create a new gym class
    Then  my class appears on the list of available classes
  ```

- **US‑INST‑004 — View enrollments**  
  _Story:_ As a instructor, I want to view customers enrolled so that I can manage my waitlist and see my stats.  
  _Acceptance:_
  ```gherkin
  Scenario: view enrollments
    Given my class is listed
    When  I view enrollments/waitlist
    Then  I see how many customers enrolled and waitlisted
  ```

-- **US‑INST‑005 — Reply to customer reviews**  
  _Story:_ As a instructor, I want to reply to reviews so that customers know I value their input. 
  _Acceptance:_
  ```gherkin
  Scenario: reply to reviews
    Given my class is over and I have customer reviews
    When  I reply to a review
    Then  the reply is under the review
  ```

---

## 3. Non‑Functional Requirements (make them measurable)
- **Performance:** 95% of class search and browse requests respond in ≤ 1.5s under typical load. 
- **Availability/Reliability:** ≥ 99% monthly uptime (excluding maintenance); reliably stores class information.
- **Security/Privacy:** Password-protected accounts; role based access for instructors and customers.
- **Usability:** New users complete class browsing and RSVP in ≤ 5 minutes.

---

## 4. Assumptions, Constraints, and Policies
- Users have internet access and use a modern browser.
- Development is limited by course requirements and timeline.
- Instructors manage their own classes; user content must follow basic guidelines.

---

## 5. Milestones (course‑aligned)
- **M2 Requirements** — this file + stories opened as issues. 
- **M3 High‑fidelity prototype** — core customer/provider flows fully interactive. 
- **M4 Design** — architecture, schema, API outline. 
- **M5 Backend API** — key endpoints + tests. 
- **M6 Increment** — ≥2 use cases end‑to‑end. 
- **M7 Final** — complete system & documentation. 

---

## 6. Change Management
- Stories are living artifacts; changes are tracked via repository issues and linked pull requests.  
- Major changes should update this SRS.

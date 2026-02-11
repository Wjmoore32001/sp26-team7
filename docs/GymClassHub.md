
# Requirements

**Project Name:** Gym Class Hub \
**Team:** William Moore - Customer; Chloe Rhodes - Provider \
**Course:** CSC 340-01\
**Version:** 1.0\
**Date:** 2026-01-30

---

## 1. Overview
**Vision.** Gym Class Hub is a web app for a local gym that has multiple studio rooms.
Instead of the gym running every class, members/fitness instuctors can create and host their own classes (yoga, strenth, HIIT, etc), view RSVPs, and reply to reviews.
Gym members can browse classes, reserve a spot, and leave reviews after attending.
The main goal is to make scheduling and hosting gym classes easy while managing time and room availability.

**Glossary** Terms used in the project
- **Enrolled:** Customer who has reserved their spot in a gym class.
- **Capacity:** Max number of spots available for a given class.
- **Waitlist:** A queue of customers requesting enrollment in a class that is already at max capacity.
- **Class Session:** An instance of a scheduled class that has a date, time, room, student list, and instructor.

**Primary Users / Roles.**
- **Customer(Takes Gym Classes)** — Gym members who want to browse, reserve, and review fitness classes offered by instructors.
- **Instructor (Offers Gym Classes)** — Fitness instructors who create and manage classes, track RSVPs, and engage with customers through reviews.

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

### 2.1 Student Stories
- **US‑CUST‑001 — Register & manage profile**  
  _Story:_ As a customer, I want to create and manage my profile information (username, email, and password) so that  I can keep my profile information up to date or if I put info in incorrectly during sign up.
  _Acceptance:_
  ```gherkin
  Scenario: Register an Account
    Given I don't have an account
    When  I sign up with valid credentials
    Then  I have a customer profile that I can also update
  ```

- **US‑CUST‑002 — Browse and Attend Classes**  
  _Story:_ As a customer, I want browse available classes so that I can find a class to reserve a spot in it.  
  _Acceptance:_
  ```gherkin
  Scenario: Find and Attend gym class
    Given there is a class available with a spot available or a waitlist
    When  I find the class via some search tye
    Then  I can reserve a spot or enter waitlist, and later attend a class
  ```

- **US‑CUST‑003 — Leave Reviews on Classes**
- _Story:_ As a customer, I want to leave reviews on classes I have attended, so that I can aid the community in knowing about a class from experience.
  _Acceptance:_
  ```gherkin
  Scenario: Leave a review
    Given I have enrolled in a class and then later taken that class
    When  Leave a review on a class I have taken
    Then It is added to the review section of that class
  ```

### 2.2 Provider Stories
- **US‑PROV‑001 — <short title>**  
  _Story:_ As a provider, I want … so that …  
  _Acceptance:_
  ```gherkin
  Scenario: <happy path>
    Given <preconditions>
    When  <action>
    Then  <observable outcome>
  ```

- **US‑PROV‑002 — <short title>**  
  _Story:_ As a provider, I want … so that …  
  _Acceptance:_
  ```gherkin
  Scenario: <happy path>
    Given <preconditions>
    When  <action>
    Then  <observable outcome>
  ```

### 2.3 SysAdmin Stories
- **US‑ADMIN‑001 — <short title>**  
  _Story:_ As a sysadmin, I want … so that …  
  _Acceptance:_
  ```gherkin
  Scenario: <happy path>
    Given <preconditions>
    When  <action>
    Then  <observable outcome>
  ```

- **US‑ADMIN‑002 — <short title>**  
  _Story:_ As a sysadmin, I want … so that …  
  _Acceptance:_
  ```gherkin
  Scenario: <happy path>
    Given <preconditions>
    When  <action>
    Then  <observable outcome>
  ```

---

## 3. Non‑Functional Requirements (make them measurable)
- **Performance:** description 
- **Availability/Reliability:** description
- **Security/Privacy:** description
- **Usability:** description

---

## 4. Assumptions, Constraints, and Policies
- list any rules, policies, assumptions, etc.

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

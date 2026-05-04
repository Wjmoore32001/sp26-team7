**Project Name:** Gym Class Hub  
**Version:** 1.0  
**Date:** 2026-05-01  
**Purpose:** This document outlines comprehensive test scenarios for each functional requirement (user story) in the Gym Class Hub system.

## Actors
- Provider P: Instructor
- Customer C: Student
- Service S: Class

## Use Cases

#### 1. Provider: US-INST-001 — Register instructor account, US-INST-002 — update instructor profile

1. Instructor P1 logs in for the first time and creates a profile.
2. P1 updates their instructor profile information.
3. P1 sets or updates a profile image.
4. P1 verifies the new profile information is saved.

#### 2. Provider: US-INST-003 — Create a class

1. Instructor P1 opens My Classes.
2. P1 creates a new class S1.
3. P1 enters values for the class fields, including title, description, type, intensity, duration, price, and image.
4. P1 verifies S1 appears in My Classes.

#### 3. Provider: US-INST-003 — Create a class, US-INST-004 — View enrollments

1. P1 opens My Classes for S1.
2. P1 publishes S1.
3. P1 schedules a session for S1.
4. P1 verifies the scheduled session appears under S1.
5. P1 views the enrollments for that session.

#### 4. Provider: US-INST-005 — Reply to customer reviews

1. P1 opens My Classes.
2. P1 opens the reviews section for S1.
3. P1 reads a customer review for S1.
4. P1 replies to the review.
5. P1 verifies the reply is saved and displayed.

#### 5. Provider: US-INST-003 — Manage scheduled classes

1. P1 opens My Classes for S1.
2. P1 views the scheduled sessions listed under S1.
3. P1 reschedules an existing session for S1.
4. P1 verifies the updated session time is saved.
5. P1 cancels a session for S1.
6. P1 verifies the cancelled session is removed.

#### 6. Customer: US-CUST-001 — Register, US-CUST-002 — manage profile

1. Student C1 logs in for the first time and creates a profile.
2. C1 edits their profile and changes the email.
3. C1 exists.

#### 7. Customer: US-CUST-001 — Register, US-CUST-002 — manage profile, US-CUST-003 — views available classes, US-CUST-004 — Enroll.

1. Student C2 logs in for the first time and creates a new profile.
2. C2 views available classes S1 and S2.
3. C2 enrolls to S1.

#### 8. Customer: US-CUST-005 — Write a review

1. C2 log in and views their classes.
2. C2 writes a positive review of a class S1. C2 exits.

## CROSS-CUTTING TEST SCENARIOS (Non-Functional Requirements)

### Performance Requirements

**Scenario P1: View my classes response time < 1.5 seconds**
- **Setup:** Server under typical load
- **Steps:**
  1. Measure response time for "my classes" page load with 5 enrollments, 10+ classes
  2. Repeat 10 times
- **Expected Outcome:** 95% of requests ≤ 1.5 seconds

**Scenario P2:**
- **Setup:** 
- **Steps:**
  1. x
  2. y
- **Expected Outcome:** 

### Security & Privacy Requirements

**Scenario S1: Student cannot enroll without signing in**
- **Setup: Student opens page without signing in** 
- **Steps:**
  1. Student browses classes
  2. Attempts to enroll
- **Expected Outcome:** 
  - Student is redirected to the signin page

**Scenario S2:**
- **Setup:** 
- **Steps:**
  1. x
  2. y
- **Expected Outcome:**

### Usability Requirements

**Scenario U1: New user completes first enrollment in ≤ 2 minutes**
- **Setup:** New user participates in test
- **Steps:**
  1. User logs in (account pre-created)
  2. User browses classes
  3. User clicks join button to enroll
  4. Record total time
- **Expected Outcome:** Time to complete subscription ≤ 2 minutes

**Scenario U2:**
- **Setup:** 
- **Steps:**
  1. x
  2. y
- **Expected Outcome:** 

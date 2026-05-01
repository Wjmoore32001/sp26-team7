**Project Name:** Gym Class Hub  
**Version:** 1.0  
**Date:** 2026-05-01  
**Purpose:** This document outlines comprehensive test scenarios for each functional requirement (user story) in the Gym Class Hub system.

## Actors
- Provider P: Instructor
- Customer C: Student
- Service S: Class

## Use Cases
#### 1. Customer: US‑CUST‑001 — Register, US‑CUST‑002 — manage profile
1. Student C1 logs in for the first time and creates a profile.
2. C1 edits their profile and changes the email.
3. C1 exists.

#### 2. Customer:  US‑CUST‑001 — Register, US‑CUST‑002 — manage profile, US-CUST‑003 — views available classes, US‑CUST‑004 — Enroll.
1. Student C2 logs in for the first time and creates a new profile.
2. C2 views available classes S1 and S2.
3. C2 enrolls to S1.

#### 3. Customer: US‑CUST‑005 — Write a review
1. C2 log in and views their classes.
2. C2 writes a positive review of a class S1. C2 exits.

#### 6. Provider: 
1.
2.

#### 7. Provider: 
1.
2.

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
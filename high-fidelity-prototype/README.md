# High-Fidelity Prototype – GymClassHub

This folder contains a static, click-through UI prototype for GymClassHub.
There is no backend logic, pages use hard-coded HTML content and links to simulate the main user flows for:

- **Customer** (gym member)
- **Instructor** (class provider)

---

# Directory Structure

## high-fidelity-prototype
    - home.html
    - login.html
    - sign-up.html
    - browse.html
    - class-details.html
### assets
    images
    css
    partials
        - nav-root.html
        - nav-customer.html
        - nav-instructor.html
    js
        - include-nav.js
### customer
    - dashboard.html
    - profile.html
    - my-classes.html
    - class-history.html
    - leave-review.html
### instructor
    - dashboard.html
    - profile.html
    - created-classes.html
    - scheduled-classes.html
    - create-class.html
    - schedule-class.html

# File Purposes/Content

## Shared Files

### home.html
    Purpose: 
        Landing page for people not signed in
    Content:
        What the site is for
        Mock classes
        show features like creating classes for instructor and reserving for customer

### login.html
    Purpose:
        Prototype login (no real backend)
    Content:
        Boxes to enter:
            Email
            Password

### sign-up
    Purpose:
        Prototype sign up (no real backend)
    Content:
        Boxes to enter:
            First Name
            Last Name
            Email
            Password
            Selector for instructor or customer (student)

### browse.html
    Purpose:
        search/filter for classes
    Content:
        Search bar 
        filter (drop down selectors)
            time
            type

### class-details.html
    Purpose:
        View detailed information on a selected class
        With option to enroll
    Content:
        Time/Date
        Room Number
        Type
        Slots
        Review Section (static/hard-coded)
        Button to reserve if logged in
        Button to schedule if instructor
        Button to login/sign-up if not logged in

## assets/css
    For Styling css files
## assets/images
    For any images used
## assets/js
    For java script(tiny amount)
### include-nav.js
    Purpose:
        Puts the correct nav bar depending on login status on all html pages
        To avoid a lot of copy pasting
## assets/partials
    Purpose:
        html files that are part of another html file that needs to be inserted
        mainly used for the nav bar elements that are different between not logged in, customer, and isntructor
### nav-root.html
    Purpose:
        Nav bar elements that are the same among all user types
### nav-customer.html
    Purpose:
        Customer specific nav bar
### nav-instructor.html
    Purpose:
        Instructor specific nav bar

## Customer Files
### dashboard.html
    Purpose:
        Customer home page after logging in
    Content:
        Upcoming reserved class
        Suggested classes to schedule (previously taken)
        Quick link to leave a review to most recently taken class

### profile.html:
    Purpose:
        Customer profile view/edit
    Content: 
        Basic account info + optional edit fields
    
### my-classes.html
    Purpose:
        Customer’s upcoming reserved classes
    Content:
        List/table of upcoming sessions with time/room

### class-history.html
    Purpose:
        Customer’s past/attended classes (supports review)
    Content:
        Completed sessions list, each with a “Leave Review” link to leave-review.html

### leave-review.html
    Purpose:
        Review submission form
    Content:
        Rating input(0-5 stars) + comment box


## Instructor Files
### dashboard.html
    Purpose:
        Instructor home page after login
    Content:
        Upcoming scheduled sessions they teach + quick links to Created Classes / Schedule

### profile.html
    Purpose:
        Instructor profile view/edit
    Content:
        Instructor-specific info (bio/specialties/certifications) + optional edit fields

### created-classes.html
    Purpose:
        Instructor’s created class listings (classes they have made but are not necessarily scheduled)
    Content:
        List of created classes + button linking to create-class.html
        Button on created class to schedule that class

### create-class.html
    Purpose:
        Create a new class (template)
    Content:
        title 
        type 
        duration 
        cost 
        description

### schedule-class.html
    Purpose:
        Schedule a created class into a room/time slot
    Content:
        Created class dropdown + room dropdown + timeslot grid (AVAILABLE/RESERVED/CLOSED)

### scheduled-classes.html
    Purpose:
        Instructor’s scheduled sessions (what they are actually teaching on the calendar)
    Content:
        List/table of scheduled sessions with date/time, room, roster of students, and status


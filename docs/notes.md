# Things I added for milestone7

## Instructor-only

- Added an instructor dashboard page.
- Added an instructor profile page.
- Added an instructor browse page.
- Added instructor browse filters:
  - search
  - class type
  - intensity
  - max price
- Made instructor browse hide the instructor's own classes.
- Updated the instructor navbar:
  - changed Home to Dashboard
  - added Browse
  - added Profile
- Changed the instructor brand/logo link to stay on the instructor side.
- Changed the instructor Home link to stay on the instructor side.
- Added viewing reviews for each instructor class from My Classes.
- Added replying to reviews from My Classes.
- Added instructor profile image support:
  - save image path
  - show image on profile page
  - use placeholder if none is set
- Added class image support on the instructor side:
  - save image path in class edit
  - show image on My Classes
  - use placeholder if none is set

## Project-wide / backend changes

- Added shared instructor signup/login flow into the app.
- Added safer delete logic for class sessions so related rows are removed first.
- Added safer delete logic for class templates so related rows are removed first.
- Added transaction handling for the delete flows that needed it.
- Added image fields in the backend:
  - instructor profile image path
  - class template image path
- Changed image use to relative paths from the project's static folder.
- Set the default placeholder image to:
  - `/assets/images/placeholdergeneral.png`

## Changes I made to the student side

- Changed student browse to show published class templates instead of the old session-based browse.
- Changed the student details flow so students open a class template details page.
- Updated the student class details page so it shows:
  - class info
  - available sessions
  - reviews
- Added display of instructor replies on the student class details page.


## New Thing Changed/Added 05/04/26
- If a not logged in user clicks browse it redirects to the login page instead of letting them see classes
  - It now redirects if they try to join a session or leave a review.
- if on the login page and browse is hit, you get a white label error 
  - fixed that endpoint
- because the app was setup with freemarker and not from a perspective of working with how instructor was programmed, browse for a not logged in user can't use the filtered functionality of the instructors filter code in browse.
  - had to fix student browse functionality so it actual has functioning filters so a not logged in user can use it.
- Since the not logged in users pages are handled in the Student controller for some reason a number of changes needed to be made 
  since browse is also for student and not logged in reason i had to modify browse.ftlh to have filters and allow the not logged in user to use it.
- Had to actually rewrite almost the entire browse.ftlh so that it uses the filters, and added a js script in it for event listeners.
- browse and class details for student/not-logged-in/instructor didn't display class or instructor images
  - had to update browse.ftlh/class-details.ftlh/browse.js to get the images to show.
- Since browse is both for not logged in and student it need to pass in a login state so made another fix to studentuicontroller

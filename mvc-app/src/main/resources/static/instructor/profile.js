const profileMessage = document.getElementById("profile-message");
const profileContent = document.getElementById("profile-content");
const profileRole = document.getElementById("profile-role");
const profileInstructorId = document.getElementById("profile-instructor-id");

loadProfile();

function loadProfile() {
  fetch("/api/session-user")
    .then(function (response) {
      if (!response.ok) {
        throw new Error("Could not read session user.");
      }
      return response.json();
    })
    .then(function (sessionUser) {
      if (!sessionUser.loggedIn) {
        window.location.href = "/signin";
        return;
      }

      if (sessionUser.role !== "INSTRUCTOR" || !sessionUser.instructorId) {
        profileMessage.innerHTML = '<div class="alert alert-danger">You are not signed in as an instructor.</div>';
        return;
      }

      profileRole.textContent = sessionUser.role;
      profileInstructorId.textContent = sessionUser.instructorId;
      profileContent.classList.remove("d-none");
    })
    .catch(function (error) {
      profileMessage.innerHTML = '<div class="alert alert-danger">Could not load instructor profile.</div>';
      console.log(error);
    });
}

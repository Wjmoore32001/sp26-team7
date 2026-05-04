const profileMessage = document.getElementById("profile-message");
const profileContent = document.getElementById("profile-content");
const profileName = document.getElementById("profile-name");
const profileRole = document.getElementById("profile-role");
const profileInstructorId = document.getElementById("profile-instructor-id");
const profileImage = document.getElementById("profile-image");
const profileImageUrlInput = document.getElementById("profile-image-url");
const saveProfileImageButton = document.getElementById("save-profile-image-button");

const DEFAULT_IMAGE_PATH = "/assets/images/placeholdergeneral.png";

let currentInstructorId = null;

loadProfile();

profileImage.addEventListener("error", function () {
  profileImage.src = DEFAULT_IMAGE_PATH;
});

saveProfileImageButton.addEventListener("click", function () {
  saveProfileImage();
});

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
        return null;
      }

      if (sessionUser.role !== "INSTRUCTOR" || !sessionUser.instructorId) {
        profileMessage.innerHTML = '<div class="alert alert-danger">You are not signed in as an instructor.</div>';
        return null;
      }

      currentInstructorId = sessionUser.instructorId;
      profileRole.textContent = sessionUser.role;
      profileInstructorId.textContent = sessionUser.instructorId;

      return fetch("/instructors/" + sessionUser.instructorId);
    })
    .then(function (response) {
      if (response === null) {
        return null;
      }

      if (!response.ok) {
        throw new Error("Could not load instructor profile.");
      }

      return response.json();
    })
    .then(function (instructor) {
      if (instructor === null) {
        return;
      }

      const resolvedImagePath =
        instructor.profileImageUrl && instructor.profileImageUrl.trim() !== ""
          ? instructor.profileImageUrl
          : DEFAULT_IMAGE_PATH;

      profileName.textContent = instructor.name || "";
      profileImage.src = resolvedImagePath;
      profileImageUrlInput.value = resolvedImagePath;
      profileContent.classList.remove("d-none");
    })
    .catch(function (error) {
      profileMessage.innerHTML = '<div class="alert alert-danger">Could not load instructor profile.</div>';
      console.log(error);
    });
}

function saveProfileImage() {
  if (!currentInstructorId) {
    return;
  }

  fetch("/instructors/" + currentInstructorId, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      profileImageUrl: profileImageUrlInput.value
    })
  })
    .then(function (response) {
      if (!response.ok) {
        throw new Error("Could not save profile image.");
      }

      return response.json();
    })
    .then(function (updatedInstructor) {
      const resolvedImagePath =
        updatedInstructor.profileImageUrl && updatedInstructor.profileImageUrl.trim() !== ""
          ? updatedInstructor.profileImageUrl
          : DEFAULT_IMAGE_PATH;

      profileImage.src = resolvedImagePath;
      profileImageUrlInput.value = resolvedImagePath;
      profileMessage.innerHTML = '<div class="alert alert-success">Profile image saved successfully.</div>';
    })
    .catch(function (error) {
      profileMessage.innerHTML = '<div class="alert alert-danger">' + error.message + '</div>';
      console.log(error);
    });
}

const form = document.getElementById("create-class-form");
const messageDiv = document.getElementById("form-message");
const submitButton = document.getElementById("submit-button");

form.addEventListener("submit", function (event) {
  event.preventDefault();

  messageDiv.innerHTML = "";
  submitButton.disabled = true;
  submitButton.textContent = "Creating...";

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
        throw new Error("You are not signed in as an instructor.");
      }

      const classTemplate = {
        instructor: {
          userId: sessionUser.instructorId
        },
        title: document.getElementById("title").value,
        classType: document.getElementById("classType").value,
        intensity: document.getElementById("intensity").value,
        duration: parseInt(document.getElementById("duration").value),
        price: parseFloat(document.getElementById("price").value),
        description: document.getElementById("description").value
      };

      return fetch("/classTemplates", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(classTemplate)
      });
    })
    .then(function (response) {
      if (response === null) {
        return null;
      }

      if (!response.ok) {
        throw new Error("Could not create class.");
      }

      return response.json();
    })
    .then(function (data) {
      if (data === null) {
        return;
      }

      messageDiv.innerHTML = '<div class="alert alert-success mb-0">Class created successfully.</div>';

      setTimeout(function () {
        window.location.href = "/instructor/my-classes.html";
      }, 800);
    })
    .catch(function (error) {
      messageDiv.innerHTML = '<div class="alert alert-danger mb-0">' + error.message + '</div>';
      console.log(error);

      submitButton.disabled = false;
      submitButton.textContent = "Create Class";
    });
});

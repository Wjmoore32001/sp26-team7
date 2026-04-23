const form = document.getElementById("create-class-form");
const messageDiv = document.getElementById("form-message");
const submitButton = document.getElementById("submit-button");

form.addEventListener("submit", function (event) {
  event.preventDefault();

  messageDiv.innerHTML = "";
  submitButton.disabled = true;
  submitButton.textContent = "Creating...";

  const classTemplate = {
    instructor: {
      userId: CURRENT_INSTRUCTOR_ID
    },
    title: document.getElementById("title").value,
    classType: document.getElementById("classType").value,
    intensity: document.getElementById("intensity").value,
    duration: parseInt(document.getElementById("duration").value),
    price: parseFloat(document.getElementById("price").value),
    description: document.getElementById("description").value
  };

  fetch("/classTemplates", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(classTemplate)
  })
    .then(function (response) {
      if (!response.ok) {
        throw new Error("Could not create class.");
      }
      return response.json();
    })
    .then(function () {
      messageDiv.innerHTML = '<div class="alert alert-success mb-0">Class created successfully.</div>';

      setTimeout(function () {
        window.location.href = "/instructor/my-classes.html";
      }, 800);
    })
    .catch(function (error) {
      messageDiv.innerHTML = '<div class="alert alert-danger mb-0">Could not create class.</div>';
      console.log(error);

      submitButton.disabled = false;
      submitButton.textContent = "Create Class";
    });
});

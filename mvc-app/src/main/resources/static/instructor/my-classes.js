const classList = document.getElementById("class-list");
const messageArea = document.getElementById("message-area");

loadPage();

function loadPage() {
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
        messageArea.innerHTML = '<div class="alert alert-danger">You are not signed in as an instructor.</div>';
        classList.innerHTML = "";
        return;
      }

      loadClassTemplates(sessionUser.instructorId);
    })
    .catch(function (error) {
      messageArea.innerHTML = '<div class="alert alert-danger">Could not load session user.</div>';
      console.log(error);
    });
}

function loadClassTemplates(instructorId) {
  messageArea.innerHTML = "";
  classList.innerHTML = `
        <div class="col-12">
            <div class="p-4 bg-body-tertiary border border-primary-subtle rounded-3 text-start">
                Loading classes...
            </div>
        </div>
    `;

  fetch("/classTemplates/instructor/" + instructorId)
    .then(function (response) {
      if (!response.ok) {
        throw new Error("Could not load classes.");
      }
      return response.json();
    })
    .then(function (classes) {
      displayClasses(classes);
    })
    .catch(function (error) {
      classList.innerHTML = "";
      messageArea.innerHTML = '<div class="alert alert-danger">Could not load classes.</div>';
      console.log(error);
    });
}

function displayClasses(classes) {
  classList.innerHTML = "";

  if (!classes || classes.length === 0) {
    classList.innerHTML = `
            <div class="col-12">
                <div class="p-4 bg-body-tertiary border border-primary-subtle rounded-3 text-start">
                    <p class="mb-0">No classes found yet.</p>
                </div>
            </div>
        `;
    return;
  }

  classes.forEach(function (classTemplate) {
    const col = document.createElement("div");
    col.className = "col-12";

    const isPublished = classTemplate.published === true;
    const statusText = isPublished ? "Published" : "Hidden";
    const statusClass = isPublished ? "success" : "secondary";
    const toggleButtonText = isPublished ? "Unpublish" : "Publish";
    const toggleButtonClass = isPublished ? "btn-outline-warning" : "btn-primary";

    col.innerHTML = `
            <div class="p-4 bg-body-tertiary border border-primary-subtle rounded-3 text-start">
                <div class="d-flex justify-content-between align-items-start flex-wrap gap-2 mb-3">
                    <h4 class="mb-0">${escapeHtml(classTemplate.title || "Untitled Class")}</h4>
                    <span class="badge text-bg-${statusClass}">${statusText}</span>
                </div>

                <p class="mb-2"><strong>Type:</strong> ${formatText(classTemplate.classType)}</p>
                <p class="mb-2"><strong>Duration:</strong> ${classTemplate.duration || 0} min</p>
                <p class="mb-2"><strong>Intensity:</strong> ${formatText(classTemplate.intensity)}</p>
                <p class="mb-2"><strong>Price:</strong> $${formatPrice(classTemplate.price)}</p>
                <p class="mb-3"><strong>Description:</strong> ${escapeHtml(classTemplate.description || "No description.")}</p>

                <div class="d-flex flex-wrap gap-2">
                    <button
                        class="btn ${toggleButtonClass} publish-toggle-button"
                        data-template-id="${classTemplate.classTemplateId}"
                        data-published="${isPublished}">
                        ${toggleButtonText}
                    </button>
                </div>
            </div>
        `;

    classList.appendChild(col);
  });

  addPublishButtonListeners();
}

function addPublishButtonListeners() {
  const buttons = document.querySelectorAll(".publish-toggle-button");

  buttons.forEach(function (button) {
    button.addEventListener("click", function () {
      const templateId = button.dataset.templateId;
      const currentlyPublished = button.dataset.published === "true";
      togglePublished(templateId, currentlyPublished);
    });
  });
}

function togglePublished(templateId, currentlyPublished) {
  fetch("/classTemplates/" + templateId)
    .then(function (response) {
      if (!response.ok) {
        throw new Error("Could not load class template.");
      }
      return response.json();
    })
    .then(function (classTemplate) {
      const updatedTemplate = {
        title: classTemplate.title,
        classType: classTemplate.classType,
        intensity: classTemplate.intensity,
        duration: classTemplate.duration,
        price: classTemplate.price,
        description: classTemplate.description,
        instructor: classTemplate.instructor,
        published: !currentlyPublished
      };

      return fetch("/classTemplates/" + templateId, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(updatedTemplate)
      });
    })
    .then(function (response) {
      if (!response.ok) {
        throw new Error("Could not update published status.");
      }
      return response.json();
    })
    .then(function (updatedTemplate) {
      const message = updatedTemplate.published
        ? "Class published successfully."
        : "Class hidden successfully.";

      messageArea.innerHTML = '<div class="alert alert-success">' + message + '</div>';
      loadPage();
    })
    .catch(function (error) {
      messageArea.innerHTML = '<div class="alert alert-danger">' + error.message + '</div>';
      console.log(error);
    });
}

function formatText(value) {
  if (!value) {
    return "";
  }

  return value
    .toLowerCase()
    .replaceAll("_", " ")
    .replace(/\b\w/g, function (letter) {
      return letter.toUpperCase();
    });
}

function formatPrice(price) {
  if (price === null || price === undefined || price === "") {
    return "0.00";
  }

  return Number(price).toFixed(2);
}

function escapeHtml(text) {
  return String(text)
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;");
}

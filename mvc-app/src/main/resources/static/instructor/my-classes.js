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

                <div class="d-flex flex-wrap gap-2 mb-3">
                    <button
                        class="btn ${toggleButtonClass} publish-toggle-button"
                        data-template-id="${classTemplate.classTemplateId}"
                        data-published="${isPublished}">
                        ${toggleButtonText}
                    </button>

                    <button
                        class="btn btn-outline-secondary schedule-toggle-button"
                        data-template-id="${classTemplate.classTemplateId}">
                        Schedule Session
                    </button>
                </div>

                <div id="schedule-form-${classTemplate.classTemplateId}" class="d-none border-top pt-3 mt-3">
                    <h5 class="mb-3">Create Session</h5>

                    <div class="row g-2">
                        <div class="col-md-6">
                            <label class="form-label" for="scheduledAt-${classTemplate.classTemplateId}">Date and Time</label>
                            <input type="datetime-local" class="form-control" id="scheduledAt-${classTemplate.classTemplateId}">
                        </div>

                        <div class="col-md-6 d-flex align-items-end">
                            <button
                                class="btn btn-success save-session-button"
                                data-template-id="${classTemplate.classTemplateId}">
                                Save Session
                            </button>
                        </div>
                    </div>
                </div>

                <div id="sessions-${classTemplate.classTemplateId}" class="border-top pt-3 mt-3">
                    <h5 class="mb-3">Sessions</h5>
                    <div class="session-list">Loading sessions...</div>
                </div>
            </div>
        `;

    classList.appendChild(col);
    loadSessionsForTemplate(classTemplate.classTemplateId);
  });

  addButtonListeners();
}

function addButtonListeners() {
  const publishButtons = document.querySelectorAll(".publish-toggle-button");
  publishButtons.forEach(function (button) {
    button.addEventListener("click", function () {
      const templateId = button.dataset.templateId;
      const currentlyPublished = button.dataset.published === "true";
      togglePublished(templateId, currentlyPublished);
    });
  });

  const scheduleToggleButtons = document.querySelectorAll(".schedule-toggle-button");
  scheduleToggleButtons.forEach(function (button) {
    button.addEventListener("click", function () {
      const templateId = button.dataset.templateId;
      const formArea = document.getElementById("schedule-form-" + templateId);
      formArea.classList.toggle("d-none");
    });
  });

  const saveSessionButtons = document.querySelectorAll(".save-session-button");
  saveSessionButtons.forEach(function (button) {
    button.addEventListener("click", function () {
      const templateId = button.dataset.templateId;
      createSession(templateId);
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

function createSession(templateId) {
  const scheduledAtInput = document.getElementById("scheduledAt-" + templateId);
  const scheduledAt = scheduledAtInput.value;

  if (!scheduledAt) {
    messageArea.innerHTML = '<div class="alert alert-danger">Please choose a date and time before creating a session.</div>';
    return;
  }

  const classSession = {
    scheduledAt: scheduledAt,
    classTemplate: {
      classTemplateId: parseInt(templateId)
    }
  };

  fetch("/classSessions", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(classSession)
  })
    .then(function (response) {
      if (!response.ok) {
        throw new Error("Could not create class session.");
      }
      return response.json();
    })
    .then(function () {
      messageArea.innerHTML = '<div class="alert alert-success">Class session created successfully.</div>';
      document.getElementById("schedule-form-" + templateId).classList.add("d-none");
      scheduledAtInput.value = "";
      loadSessionsForTemplate(templateId);
    })
    .catch(function (error) {
      messageArea.innerHTML = '<div class="alert alert-danger">' + error.message + '</div>';
      console.log(error);
    });
}

function loadSessionsForTemplate(templateId) {
  fetch("/classSessions/classTemplate/" + templateId)
    .then(function (response) {
      if (!response.ok) {
        throw new Error("Could not load sessions.");
      }
      return response.json();
    })
    .then(function (sessions) {
      displaySessions(templateId, sessions);
    })
    .catch(function (error) {
      const sessionsContainer = document.querySelector("#sessions-" + templateId + " .session-list");
      sessionsContainer.innerHTML = '<p class="mb-0">Could not load sessions.</p>';
      console.log(error);
    });
}

function displaySessions(templateId, sessions) {
  const sessionsContainer = document.querySelector("#sessions-" + templateId + " .session-list");

  if (!sessions || sessions.length === 0) {
    sessionsContainer.innerHTML = '<p class="mb-0">No sessions created yet.</p>';
    return;
  }

  let html = "";

  sessions.forEach(function (session) {
    html += `
            <div class="border rounded-3 p-3 mb-2">
                <p class="mb-0"><strong>Scheduled At:</strong> ${formatDateTime(session.scheduledAt)}</p>
            </div>
        `;
  });

  sessionsContainer.innerHTML = html;
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

function formatDateTime(value) {
  if (!value) {
    return "";
  }

  const date = new Date(value);
  return date.toLocaleString();
}

function escapeHtml(text) {
  return String(text)
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;");
}

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
                        class="btn btn-outline-secondary edit-toggle-button"
                        data-template-id="${classTemplate.classTemplateId}">
                        Edit
                    </button>

                    <button
                        class="btn btn-outline-secondary schedule-toggle-button"
                        data-template-id="${classTemplate.classTemplateId}">
                        Schedule Session
                    </button>

                    <button
                        class="btn btn-outline-info reviews-toggle-button"
                        data-template-id="${classTemplate.classTemplateId}">
                        View Reviews
                    </button>
                </div>

                <div id="edit-form-${classTemplate.classTemplateId}" class="d-none border-top pt-3 mt-3">
                    <h5 class="mb-3">Edit Class Template</h5>

                    <div class="mb-2">
                        <label class="form-label" for="edit-title-${classTemplate.classTemplateId}">Title</label>
                        <input type="text" class="form-control" id="edit-title-${classTemplate.classTemplateId}" value="${escapeAttribute(classTemplate.title || "")}">
                    </div>

                    <div class="mb-2">
                        <label class="form-label" for="edit-classType-${classTemplate.classTemplateId}">Class Type</label>
                        <select class="form-select" id="edit-classType-${classTemplate.classTemplateId}">
                            ${buildClassTypeOptions(classTemplate.classType)}
                        </select>
                    </div>

                    <div class="mb-2">
                        <label class="form-label" for="edit-duration-${classTemplate.classTemplateId}">Duration</label>
                        <input type="number" class="form-control" id="edit-duration-${classTemplate.classTemplateId}" value="${classTemplate.duration || 0}">
                    </div>

                    <div class="mb-2">
                        <label class="form-label" for="edit-price-${classTemplate.classTemplateId}">Price</label>
                        <input type="number" step="0.01" class="form-control" id="edit-price-${classTemplate.classTemplateId}" value="${classTemplate.price || 0}">
                    </div>

                    <div class="mb-2">
                        <label class="form-label" for="edit-intensity-${classTemplate.classTemplateId}">Intensity</label>
                        <select class="form-select" id="edit-intensity-${classTemplate.classTemplateId}">
                            ${buildIntensityOptions(classTemplate.intensity)}
                        </select>
                    </div>

                    <div class="mb-3">
                        <label class="form-label" for="edit-description-${classTemplate.classTemplateId}">Description</label>
                        <textarea class="form-control" rows="3" id="edit-description-${classTemplate.classTemplateId}">${escapeHtml(classTemplate.description || "")}</textarea>
                    </div>

                    <div class="d-flex flex-wrap gap-2">
                        <button
                            class="btn btn-warning edit-save-button"
                            data-template-id="${classTemplate.classTemplateId}">
                            Save Edit
                        </button>

                        <button
                            class="btn btn-danger delete-template-button"
                            data-template-id="${classTemplate.classTemplateId}">
                            Delete Template
                        </button>
                    </div>
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

                <div id="reviews-${classTemplate.classTemplateId}" class="d-none border-top pt-3 mt-3">
                    <h5 class="mb-3">Reviews</h5>
                    <div class="reviews-list">Loading reviews...</div>
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

  const editToggleButtons = document.querySelectorAll(".edit-toggle-button");
  editToggleButtons.forEach(function (button) {
    button.addEventListener("click", function () {
      const templateId = button.dataset.templateId;
      const formArea = document.getElementById("edit-form-" + templateId);
      formArea.classList.toggle("d-none");
    });
  });

  const editSaveButtons = document.querySelectorAll(".edit-save-button");
  editSaveButtons.forEach(function (button) {
    button.addEventListener("click", function () {
      const templateId = button.dataset.templateId;
      saveTemplateEdit(templateId);
    });
  });

  const deleteTemplateButtons = document.querySelectorAll(".delete-template-button");
  deleteTemplateButtons.forEach(function (button) {
    button.addEventListener("click", function () {
      const templateId = button.dataset.templateId;
      deleteTemplate(templateId);
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

  const reviewsToggleButtons = document.querySelectorAll(".reviews-toggle-button");
  reviewsToggleButtons.forEach(function (button) {
    button.addEventListener("click", function () {
      const templateId = button.dataset.templateId;
      toggleReviews(templateId, button);
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

function saveTemplateEdit(templateId) {
  fetch("/classTemplates/" + templateId)
    .then(function (response) {
      if (!response.ok) {
        throw new Error("Could not load class template.");
      }
      return response.json();
    })
    .then(function (classTemplate) {
      const updatedTemplate = {
        title: document.getElementById("edit-title-" + templateId).value,
        classType: document.getElementById("edit-classType-" + templateId).value,
        intensity: document.getElementById("edit-intensity-" + templateId).value,
        duration: parseInt(document.getElementById("edit-duration-" + templateId).value),
        price: parseFloat(document.getElementById("edit-price-" + templateId).value),
        description: document.getElementById("edit-description-" + templateId).value,
        instructor: classTemplate.instructor,
        published: classTemplate.published
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
        throw new Error("Could not update class template.");
      }
      return response.json();
    })
    .then(function () {
      messageArea.innerHTML = '<div class="alert alert-success">Class template updated successfully.</div>';
      loadPage();
    })
    .catch(function (error) {
      messageArea.innerHTML = '<div class="alert alert-danger">' + error.message + '</div>';
      console.log(error);
    });
}

function deleteTemplate(templateId) {
  const confirmed = window.confirm("Are you sure you want to delete this class template?");

  if (!confirmed) {
    return;
  }

  fetch("/classTemplates/" + templateId, {
    method: "DELETE"
  })
    .then(function (response) {
      if (!response.ok) {
        throw new Error("Could not delete class template.");
      }
    })
    .then(function () {
      messageArea.innerHTML = '<div class="alert alert-success">Class template deleted successfully.</div>';
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
                <p class="mb-2"><strong>Scheduled At:</strong> ${formatDateTime(session.scheduledAt)}</p>

                <div class="d-flex flex-wrap gap-2 mb-2">
                    <button
                        class="btn btn-outline-secondary session-edit-toggle-button"
                        data-session-id="${session.classSessionId}">
                        Reschedule
                    </button>

                    <button
                        class="btn btn-outline-danger session-delete-button"
                        data-session-id="${session.classSessionId}"
                        data-template-id="${templateId}">
                        Cancel Session
                    </button>
                </div>

                <div id="session-edit-form-${session.classSessionId}" class="d-none border-top pt-3 mt-3">
                    <div class="row g-2">
                        <div class="col-md-6">
                            <label class="form-label" for="session-scheduledAt-${session.classSessionId}">New Date and Time</label>
                            <input
                                type="datetime-local"
                                class="form-control"
                                id="session-scheduledAt-${session.classSessionId}"
                                value="${formatForDateTimeInput(session.scheduledAt)}">
                        </div>

                        <div class="col-md-6 d-flex align-items-end">
                            <button
                                class="btn btn-success session-save-button"
                                data-session-id="${session.classSessionId}"
                                data-template-id="${templateId}">
                                Save Reschedule
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        `;
  });

  sessionsContainer.innerHTML = html;
  addSessionButtonListeners();
}

function toggleReviews(templateId, button) {
  const reviewsSection = document.getElementById("reviews-" + templateId);
  const reviewsList = reviewsSection.querySelector(".reviews-list");
  const isHidden = reviewsSection.classList.contains("d-none");

  if (!isHidden) {
    reviewsSection.classList.add("d-none");
    button.textContent = "View Reviews";
    return;
  }

  reviewsSection.classList.remove("d-none");
  button.textContent = "Hide Reviews";

  if (reviewsSection.dataset.loaded === "true") {
    return;
  }

  reviewsList.innerHTML = "Loading reviews...";

  fetch("/api/reviews/class-template/" + templateId)
    .then(function (response) {
      if (!response.ok) {
        throw new Error("Could not load reviews.");
      }
      return response.json();
    })
    .then(function (reviews) {
      displayReviews(templateId, reviews);
      reviewsSection.dataset.loaded = "true";
    })
    .catch(function (error) {
      reviewsList.innerHTML = '<p class="mb-0">Could not load reviews.</p>';
      console.log(error);
    });
}

function displayReviews(templateId, reviews) {
  const reviewsList = document.querySelector("#reviews-" + templateId + " .reviews-list");

  if (!reviews || reviews.length === 0) {
    reviewsList.innerHTML = '<p class="mb-0">No reviews yet.</p>';
    return;
  }

  let html = "";

  reviews.forEach(function (review) {
    html += `
            <div class="border rounded-3 p-3 mb-2">
                <p class="mb-2"><strong>Student:</strong> ${escapeHtml(review.student && review.student.name ? review.student.name : "Unknown")}</p>
                <p class="mb-2"><strong>Rating:</strong> ${review.rating || 0}/5</p>
                <p class="mb-2"><strong>Comment:</strong> ${escapeHtml(review.comment || "")}</p>
                ${review.replyText
        ? `<p class="mb-0"><strong>Reply:</strong> ${escapeHtml(review.replyText)}</p>`
        : ""
      }
            </div>
        `;
  });

  reviewsList.innerHTML = html;
}

function addSessionButtonListeners() {
  const editButtons = document.querySelectorAll(".session-edit-toggle-button");
  editButtons.forEach(function (button) {
    button.addEventListener("click", function () {
      const sessionId = button.dataset.sessionId;
      const formArea = document.getElementById("session-edit-form-" + sessionId);
      formArea.classList.toggle("d-none");
    });
  });

  const saveButtons = document.querySelectorAll(".session-save-button");
  saveButtons.forEach(function (button) {
    button.addEventListener("click", function () {
      const sessionId = button.dataset.sessionId;
      const templateId = button.dataset.templateId;
      saveSessionEdit(sessionId, templateId);
    });
  });

  const deleteButtons = document.querySelectorAll(".session-delete-button");
  deleteButtons.forEach(function (button) {
    button.addEventListener("click", function () {
      const sessionId = button.dataset.sessionId;
      const templateId = button.dataset.templateId;
      deleteSession(sessionId, templateId);
    });
  });
}

function saveSessionEdit(sessionId, templateId) {
  const scheduledAtInput = document.getElementById("session-scheduledAt-" + sessionId);
  const scheduledAt = scheduledAtInput.value;

  if (!scheduledAt) {
    messageArea.innerHTML = '<div class="alert alert-danger">Please choose a new date and time.</div>';
    return;
  }

  fetch("/classSessions/" + sessionId)
    .then(function (response) {
      if (!response.ok) {
        throw new Error("Could not load class session.");
      }
      return response.json();
    })
    .then(function (session) {
      const updatedSession = {
        scheduledAt: scheduledAt,
        classTemplate: session.classTemplate
      };

      return fetch("/classSessions/" + sessionId, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(updatedSession)
      });
    })
    .then(function (response) {
      if (!response.ok) {
        throw new Error("Could not reschedule class session.");
      }
      return response.json();
    })
    .then(function () {
      messageArea.innerHTML = '<div class="alert alert-success">Class session rescheduled successfully.</div>';
      loadSessionsForTemplate(templateId);
    })
    .catch(function (error) {
      messageArea.innerHTML = '<div class="alert alert-danger">' + error.message + '</div>';
      console.log(error);
    });
}

function deleteSession(sessionId, templateId) {
  const confirmed = window.confirm("Are you sure you want to cancel this class session?");

  if (!confirmed) {
    return;
  }

  fetch("/classSessions/" + sessionId, {
    method: "DELETE"
  })
    .then(function (response) {
      if (!response.ok) {
        throw new Error("Could not cancel class session.");
      }
    })
    .then(function () {
      messageArea.innerHTML = '<div class="alert alert-success">Class session cancelled successfully.</div>';
      loadSessionsForTemplate(templateId);
    })
    .catch(function (error) {
      messageArea.innerHTML = '<div class="alert alert-danger">' + error.message + '</div>';
      console.log(error);
    });
}

function buildClassTypeOptions(selectedValue) {
  const options = ["CARDIO", "CYCLING", "CROSSFIT", "PILATES", "WEIGHTLIFTING", "YOGA", "ZUMBA"];
  let html = "";

  options.forEach(function (option) {
    const selected = option === selectedValue ? "selected" : "";
    html += `<option value="${option}" ${selected}>${formatText(option)}</option>`;
  });

  return html;
}

function buildIntensityOptions(selectedValue) {
  const options = ["HIGH", "MEDIUM_HIGH", "MEDIUM", "MEDIUM_LOW", "LOW"];
  let html = "";

  options.forEach(function (option) {
    const selected = option === selectedValue ? "selected" : "";
    html += `<option value="${option}" ${selected}>${formatText(option)}</option>`;
  });

  return html;
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

function formatForDateTimeInput(value) {
  if (!value) {
    return "";
  }

  const date = new Date(value);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  const hours = String(date.getHours()).padStart(2, "0");
  const minutes = String(date.getMinutes()).padStart(2, "0");

  return year + "-" + month + "-" + day + "T" + hours + ":" + minutes;
}

function escapeHtml(text) {
  return String(text)
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;");
}

function escapeAttribute(text) {
  return String(text)
    .replaceAll("&", "&amp;")
    .replaceAll('"', "&quot;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;");
}

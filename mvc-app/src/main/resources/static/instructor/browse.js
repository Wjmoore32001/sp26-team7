const browseMessage = document.getElementById("browse-message");
const browseResults = document.getElementById("browse-results");

const searchInput = document.getElementById("search-input");
const typeFilter = document.getElementById("type-filter");
const intensityFilter = document.getElementById("intensity-filter");
const priceFilter = document.getElementById("price-filter");
const applyFiltersButton = document.getElementById("apply-filters-button");
const clearFiltersButton = document.getElementById("clear-filters-button");

let allTemplates = [];
let currentInstructorId = null;

loadBrowsePage();

applyFiltersButton.addEventListener("click", function () {
  renderTemplates(getFilteredTemplates());
});

clearFiltersButton.addEventListener("click", function () {
  searchInput.value = "";
  typeFilter.value = "";
  intensityFilter.value = "";
  priceFilter.value = "";
  renderTemplates(getFilteredTemplates());
});

function loadBrowsePage() {
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
        browseMessage.innerHTML = '<div class="alert alert-danger">You are not signed in as an instructor.</div>';
        return null;
      }

      currentInstructorId = sessionUser.instructorId;
      return fetch("/classTemplates");
    })
    .then(function (response) {
      if (response === null) {
        return null;
      }

      if (!response.ok) {
        throw new Error("Could not load classes.");
      }

      return response.json();
    })
    .then(function (templates) {
      if (templates === null) {
        return;
      }

      allTemplates = templates.filter(function (template) {
        const isPublished = template.published === true;
        const isOwnClass = template.instructor && template.instructor.userId === currentInstructorId;
        return isPublished && !isOwnClass;
      });

      renderTemplates(getFilteredTemplates());
    })
    .catch(function (error) {
      browseMessage.innerHTML = '<div class="alert alert-danger">' + error.message + '</div>';
      console.log(error);
    });
}

function getFilteredTemplates() {
  const searchValue = searchInput.value.trim().toLowerCase();
  const selectedType = typeFilter.value;
  const selectedIntensity = intensityFilter.value;
  const maxPrice = priceFilter.value ? parseFloat(priceFilter.value) : null;

  return allTemplates.filter(function (template) {
    const title = (template.title || "").toLowerCase();
    const description = (template.description || "").toLowerCase();

    const matchesSearch =
      searchValue === "" ||
      title.includes(searchValue) ||
      description.includes(searchValue);

    const matchesType =
      selectedType === "" ||
      template.classType === selectedType;

    const matchesIntensity =
      selectedIntensity === "" ||
      template.intensity === selectedIntensity;

    const templatePrice = template.price === null || template.price === undefined
      ? 0
      : Number(template.price);

    const matchesPrice =
      maxPrice === null ||
      templatePrice <= maxPrice;

    return matchesSearch && matchesType && matchesIntensity && matchesPrice;
  });
}

function renderTemplates(templates) {
  browseResults.innerHTML = "";
  browseMessage.innerHTML = "";

  if (!templates || templates.length === 0) {
    browseResults.innerHTML = `
            <div class="p-4 bg-body-tertiary border border-primary-subtle rounded-3">
                <p class="mb-0">No matching published classes found.</p>
            </div>
        `;
    return;
  }

  templates.forEach(function (template) {
    const card = document.createElement("div");
    card.className = "p-4 bg-body-tertiary border border-primary-subtle rounded-3";

    card.innerHTML = `
            <div class="d-flex justify-content-between align-items-start flex-wrap gap-3">
                <div>
                    <h3 class="mb-2">${escapeHtml(template.title || "Untitled Class")}</h3>
                    <p class="mb-1"><strong>Instructor:</strong> ${escapeHtml(template.instructor && template.instructor.name ? template.instructor.name : "Unknown")}</p>
                    <p class="mb-1"><strong>Type:</strong> ${formatText(template.classType)}</p>
                    <p class="mb-1"><strong>Intensity:</strong> ${formatText(template.intensity)}</p>
                    <p class="mb-1"><strong>Duration:</strong> ${template.duration || 0} min</p>
                    <p class="mb-1"><strong>Price:</strong> $${formatPrice(template.price)}</p>
                    <p class="mb-0"><strong>Description:</strong> ${escapeHtml(template.description || "No description.")}</p>
                </div>
            </div>
        `;

    browseResults.appendChild(card);
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

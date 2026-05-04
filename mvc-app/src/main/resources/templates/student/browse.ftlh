<!doctype html>
<html lang="en" data-bs-theme="dark">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Browse</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
    integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">

  <link href="/assets/css/theme.css" rel="stylesheet">
</head>

<body>
  <nav class="navbar navbar-expand bg-body-tertiary">
    <div class="container-fluid">
      <a class="navbar-brand" href="/">GCH</a>

      <div class="collapse navbar-collapse show">
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link active" href="/">Home</a>
          </li>
          <li class="nav-item">
            <a class="nav-link active" href="/browse">Browse</a>
          </li>
        </ul>

        <ul class="navbar-nav ms-auto">
          <li class="nav-item">
            <a class="nav-link active" href="/signin">Login</a>
          </li>
          <li class="nav-item">
            <a class="nav-link active" href="/signup">Sign Up</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>

  <div class="container text-center">
    <div class="row mt-3">
      <div class="col p-1 bg-body-tertiary border border-primary-subtle rounded-3">
        <h1 class="text-light">Browse</h1>
      </div>
    </div>

    <div class="row mt-3 g-3">
      <div class="col-lg-4">
        <div class="p-4 bg-body-tertiary border border-primary-subtle rounded-3 text-start">
          <h3 class="mb-3">Filters</h3>

          <div class="mb-3">
            <label for="search-input" class="form-label">Search</label>
            <input id="search-input" type="text" class="form-control" placeholder="Search by title or description">
          </div>

          <div class="mb-3">
            <label for="type-filter" class="form-label">Class Type</label>
            <select id="type-filter" class="form-select">
              <option value="">All Types</option>
              <option value="CARDIO">Cardio</option>
              <option value="CYCLING">Cycling</option>
              <option value="CROSSFIT">CrossFit</option>
              <option value="PILATES">Pilates</option>
              <option value="WEIGHTLIFTING">Weightlifting</option>
              <option value="YOGA">Yoga</option>
              <option value="ZUMBA">Zumba</option>
            </select>
          </div>

          <div class="mb-3">
            <label for="intensity-filter" class="form-label">Intensity</label>
            <select id="intensity-filter" class="form-select">
              <option value="">All Intensities</option>
              <option value="HIGH">High</option>
              <option value="MEDIUM_HIGH">Medium High</option>
              <option value="MEDIUM">Medium</option>
              <option value="MEDIUM_LOW">Medium Low</option>
              <option value="LOW">Low</option>
            </select>
          </div>

          <div class="mb-3">
            <label for="price-filter" class="form-label">Max Price</label>
            <input id="price-filter" type="number" min="0" step="0.01" class="form-control" placeholder="Any price">
          </div>

          <div class="d-flex gap-2 flex-wrap">
            <button id="apply-filters-button" class="btn btn-primary">Apply Filters</button>
            <button id="clear-filters-button" class="btn btn-outline-secondary">Clear</button>
          </div>
        </div>
      </div>

      <div class="col-lg-8">
        <div id="browse-message" class="mb-3"></div>
        <div id="browse-results" class="d-flex flex-column gap-3">
          <#if templates?? && templates?size gt 0>
            <#list templates as template>
              <div class="p-4 bg-body-tertiary border border-primary-subtle rounded-3 text-start browse-card"
                data-title="${template.title!''}" data-description="${template.description!''}"
                data-type="${template.classType!''}" data-intensity="${template.intensity!''}"
                data-price="${template.price!0}">
                <div class="d-flex justify-content-between align-items-start flex-wrap gap-3">
                  <div>
                    <h3 class="mb-2">${template.title!"Untitled Class"}</h3>
                    <p class="mb-1"><strong>Instructor:</strong> ${template.instructor.name!""}</p>
                    <p class="mb-1"><strong>Type:</strong> ${template.classType!""}</p>
                    <p class="mb-1"><strong>Intensity:</strong> ${template.intensity!""}</p>
                    <p class="mb-1"><strong>Duration:</strong> ${template.duration!0} min</p>
                    <p class="mb-1"><strong>Price:</strong> $${template.price!0}</p>
                    <p class="mb-0"><strong>Description:</strong> ${template.description!"No description."}</p>
                  </div>

                  <div>
                    <a href="/student/classes/${template.classTemplateId}" class="btn btn-primary">View Class</a>
                  </div>
                </div>
              </div>
            </#list>
            <#else>
              <div class="p-4 bg-body-tertiary border border-primary-subtle rounded-3 text-start">
                <p class="mb-0">No published classes are available right now.</p>
              </div>
          </#if>
        </div>
      </div>
    </div>
  </div>

  <script>
    const browseMessage = document.getElementById("browse-message");
    const browseResults = document.getElementById("browse-results");

    const searchInput = document.getElementById("search-input");
    const typeFilter = document.getElementById("type-filter");
    const intensityFilter = document.getElementById("intensity-filter");
    const priceFilter = document.getElementById("price-filter");
    const applyFiltersButton = document.getElementById("apply-filters-button");
    const clearFiltersButton = document.getElementById("clear-filters-button");

    const browseCards = document.querySelectorAll(".browse-card");

    applyFiltersButton.addEventListener("click", function () {
      applyFilters();
    });

    clearFiltersButton.addEventListener("click", function () {
      searchInput.value = "";
      typeFilter.value = "";
      intensityFilter.value = "";
      priceFilter.value = "";
      applyFilters();
    });

    function applyFilters() {
      const searchValue = searchInput.value.trim().toLowerCase();
      const selectedType = typeFilter.value;
      const selectedIntensity = intensityFilter.value;
      const maxPrice = priceFilter.value ? parseFloat(priceFilter.value) : null;

      let visibleCount = 0;

      browseCards.forEach(function (card) {
        const title = (card.dataset.title || "").toLowerCase();
        const description = (card.dataset.description || "").toLowerCase();
        const type = card.dataset.type || "";
        const intensity = card.dataset.intensity || "";
        const price = Number(card.dataset.price || 0);

        const matchesSearch =
          searchValue === "" ||
          title.includes(searchValue) ||
          description.includes(searchValue);

        const matchesType =
          selectedType === "" ||
          type === selectedType;

        const matchesIntensity =
          selectedIntensity === "" ||
          intensity === selectedIntensity;

        const matchesPrice =
          maxPrice === null ||
          price <= maxPrice;

        const shouldShow = matchesSearch && matchesType && matchesIntensity && matchesPrice;

        card.style.display = shouldShow ? "" : "none";

        if (shouldShow) {
          visibleCount += 1;
        }
      });

      if (browseCards.length > 0 && visibleCount === 0) {
        browseMessage.innerHTML = '<div class="alert alert-secondary">No matching published classes found.</div>';
      } else {
        browseMessage.innerHTML = "";
      }
    }
  </script>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
    crossorigin="anonymous"></script>
</body>

</html>

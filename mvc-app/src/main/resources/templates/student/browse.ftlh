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
            <a class="nav-link active" href="/student/home">Home</a>
          </li>
          <li class="nav-item">
            <a class="nav-link active" href="/student/my-classes">My Classes</a>
          </li>
          <li class="nav-item">
            <a class="nav-link active" href="/student/browse">Browse</a>
          </li>
        </ul>

        <ul class="navbar-nav ms-auto">
          <li class="nav-item">
            <a class="nav-link active" href="/student/profile">Profile</a>
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

    <div class="row mt-3">
      <div class="col-lg-10 mx-auto">
        <div class="p-3 bg-body-tertiary border border-primary-subtle rounded-3 text-start">
          <#if templates?? && templates?size gt 0>
            <#list templates as template>
              <div class="border rounded-3 p-3 mb-3">
                <div class="d-flex justify-content-between align-items-start flex-wrap gap-3">
                  <div>
                    <h3 class="mb-2">${template.title!"Untitled Class"}</h3>
                    <p class="mb-1">Instructor: ${template.instructor.name!""}</p>
                    <p class="mb-1">Type: ${template.classType!""}</p>
                    <p class="mb-1">Intensity: ${template.intensity!""}</p>
                    <p class="mb-1">Duration: ${template.duration!0} min</p>
                    <p class="mb-1">Price: $${template.price!0}</p>
                    <p class="mb-0">${template.description!"No description."}</p>
                  </div>

                  <div class="d-flex align-items-center">
                    <a href="/student/classes/${template.classTemplateId}" class="btn btn-primary">View Class</a>
                  </div>
                </div>
              </div>
            </#list>
            <#else>
              <div class="border rounded-3 p-3">
                <p class="mb-0">No published classes are available right now.</p>
              </div>
          </#if>
        </div>
      </div>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
    crossorigin="anonymous"></script>
</body>

</html>

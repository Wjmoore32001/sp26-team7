<!doctype html>
<html lang="en" data-bs-theme="dark">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Class Details</title>

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
        <h1 class="text-light">${template.title!"Class Details"}</h1>
      </div>
    </div>

    <div class="row mt-3">
      <div class="col-lg-10 mx-auto text-start">
        <div class="p-4 bg-body-tertiary border border-primary-subtle rounded-3 mb-3">
          <div class="row g-4 align-items-start">
            <div class="col-lg-8">
              <h3 class="mb-3">Class Information</h3>

              <div class="mb-3">
                <img src="${template.imageUrl!'/assets/images/placeholdergeneral.png'}" alt="Class image"
                  class="img-fluid rounded border border-primary-subtle" style="max-height: 280px;"
                  onerror="this.onerror=null;this.src='/assets/images/placeholdergeneral.png';">
              </div>

              <p class="mb-2"><strong>Type:</strong> ${template.classType!""}</p>
              <p class="mb-2"><strong>Intensity:</strong> ${template.intensity!""}</p>
              <p class="mb-2"><strong>Duration:</strong> ${template.duration!0} min</p>
              <p class="mb-2"><strong>Price:</strong> $${template.price!0}</p>
              <p class="mb-0"><strong>Description:</strong> ${template.description!"No description."}</p>
            </div>

            <div class="col-lg-4">
              <h3 class="mb-3">Instructor</h3>
              <div class="text-center p-3 border rounded-3">
                <img src="${template.instructor.profileImageUrl!'/assets/images/placeholdergeneral.png'}"
                  alt="Instructor image" class="img-fluid rounded border border-primary-subtle mb-3"
                  style="max-height: 140px;"
                  onerror="this.onerror=null;this.src='/assets/images/placeholdergeneral.png';">
                <p class="mb-0"><strong>${template.instructor.name!""}</strong></p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="row mt-2">
      <div class="col-lg-10 mx-auto text-start">
        <div class="p-4 bg-body-tertiary border border-primary-subtle rounded-3 mb-3">
          <h3 class="mb-3">Available Sessions</h3>

          <#if sessions?? && sessions?size gt 0>
            <#list sessions as session>
              <div class="border rounded-3 p-3 mb-3">
                <p class="mb-2"><strong>Scheduled At:</strong> ${session.scheduledAt!""}</p>

                <form method="post" action="/student/enroll" class="mb-0">
                  <input type="hidden" name="classSessionId" value="${session.classSessionId}">
                  <button type="submit" class="btn btn-primary">Join Session</button>
                </form>
              </div>
            </#list>
            <#else>
              <div class="border rounded-3 p-3">
                <p class="mb-0">No sessions are available for this class right now.</p>
              </div>
          </#if>
        </div>
      </div>
    </div>

    <div class="row mt-2 mb-4">
      <div class="col-lg-10 mx-auto text-start">
        <div class="p-4 bg-body-tertiary border border-primary-subtle rounded-3">
          <div class="d-flex justify-content-between align-items-center flex-wrap gap-2 mb-3">
            <h3 class="mb-0">Reviews</h3>
            <a href="/student/classes/${template.classTemplateId}/reviews/new" class="btn btn-outline-secondary">
              Leave Review
            </a>
          </div>

          <#if reviews?? && reviews?size gt 0>
            <#list reviews as review>
              <div class="border rounded-3 p-3 mb-3">
                <p class="mb-2"><strong>Student:</strong> ${review.student.name!""}</p>
                <p class="mb-2"><strong>Rating:</strong> ${review.rating!0}/5</p>
                <p class="mb-2"><strong>Comment:</strong> ${review.comment!""}</p>

                <#if review.replyText?? && review.replyText?has_content>
                  <div class="mt-3 p-3 rounded-3 border border-primary-subtle bg-primary-subtle">
                    <p class="mb-0"><strong>Instructor Reply:</strong> ${review.replyText}</p>
                  </div>
                </#if>
              </div>
            </#list>
            <#else>
              <div class="border rounded-3 p-3">
                <p class="mb-0">No reviews yet.</p>
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

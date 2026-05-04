<!doctype html>
<html lang="en" data-bs-theme="dark">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>GCH Home</title>

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
            <a class="nav-link active" href="/student/browse">Browse</a>
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

  <div class="container text-center mt-4">
    <div class="row">
      <div class="col-lg-10 mx-auto">
        <div class="p-4 bg-body-tertiary border border-primary-subtle rounded-3">
          <h1 class="display-4 mb-3">Gym Class Hub</h1>
          <p class="lead mb-4">
            Browse fitness classes, manage schedules, and explore instructor-led sessions.
          </p>

          <div class="d-flex justify-content-center gap-3 flex-wrap">
            <a href="/signin" class="btn btn-primary btn-lg">Login</a>
            <a href="/signup" class="btn btn-outline-secondary btn-lg">Create Account</a>
            <a href="/student/browse" class="btn btn-outline-info btn-lg">Browse Classes</a>
          </div>
        </div>
      </div>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
    crossorigin="anonymous"></script>
</body>

</html>

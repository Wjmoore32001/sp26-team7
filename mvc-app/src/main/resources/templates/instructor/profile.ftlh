<!doctype html>
<html lang="en" data-bs-theme="dark">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <link href="../assests/css/theme.css" rel="stylesheet">
</head>

<body>
    <div id="nav-placeholder"></div>
    <script defer src="../assests/js/include-nav.js" data-nav="instructor"></script>

    <main class="container text-center">

        <div class="row mt-3">
            <div class="col p-1 bg-body-tertiary border border-primary-subtle rounded-3">
                <h1>Instructor Profile</h1>
            </div>
        </div>

        <div class="row mt-3 justify-content-center">
            <div class="col-lg-8 text-start">
                <div class="p-4 bg-body-tertiary border border-primary-subtle rounded-3">

                    <div class="row g-4 align-items-center">

                        <div class="col-md-8">
                            <h3 class="mb-1">${instructor.name}</h3>
                            <div class="text-body-secondary mb-3">Certified Fitness Instructor</div>
                            <div class="text-body-secondary mb-3">${instructor.email}</div>

                            <p>
                                ${instructor.bio}
                            </p>
                        </div>
                    </div>

                    <div class="row mt-4 g-2">

                        <div class="col-md-6">
                            <a href="/instructor/profile/edit"
                                class="btn btn-outline-primary w-100">
                                Edit Profile
                            </a>
                        </div>

                        <div class="col-md-6">
                            <a href="/logout"
                                class="btn btn-outline-primary w-100">
                                Logout
                            </a>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </main>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
        crossorigin="anonymous"></script>
</body>

</html>
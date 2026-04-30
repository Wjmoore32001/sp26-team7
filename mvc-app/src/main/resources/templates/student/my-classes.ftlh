<!doctype html>
<html lang="en" data-bs-theme="dark">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>My Classes</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <link href="/assets/css/theme.css" rel="stylesheet">
</head>

<body>
    <div id="nav-placeholder"></div>
    <script defer src="/assets/js/include-nav.js" data-nav="student"></script>

    <div class="container">
        <div class="row mt-3">
            <div class="col p-3 bg-body-tertiary border border-primary-subtle rounded-3">
                <div class="row">
                    <div class="col">
                        <h1 class="h3 mb-1">My Classes</h1>
                        <div class="text-body-secondary">All scheduled sessions and your class history.</div>
                    </div>
                    <div class="col text-end">
                        <a class="btn btn-primary" href="/student/browse">Browse</a>
                    </div>
                </div>
            </div>
        </div>

        <div class="row mt-3">
            <div class="col p-3 bg-body-tertiary border border-primary-subtle rounded-3">
                <div class="row mb-2">
                    <div class="col">
                        <h2 class="h5 mb-0">Scheduled / Upcoming</h2>
                    </div>
                </div>

                <#list schedules as schedule>
                    <div class="row align-items-center border border-secondary-subtle rounded-3 p-2 mb-3">
                        <div class="col-md-7">
                            <a class="text-decoration-none" href="/student/class-details">
                                <h5 class="mb-1">${schedule.classSession.classTemplate.title}</h5>
                            </a>
                            <div class="small text-body-secondary mb-2">Instructor:
                                ${schedule.classSession.classTemplate.instructor.name}</div>
                            <div class="row small">
                                <div class="col-6 col-lg-4"><span class="text-body-secondary">Time:</span>
                                    ${schedule.classSession.scheduledAt}</div>
                                <div class="col-6 col-lg-4"><span class="text-body-secondary">Room:</span> Studio A
                                </div>
                                <div class="col-6 col-lg-4"><span class="text-body-secondary">Price:</span>
                                    $${schedule.classSession.classTemplate.price}</div>
                                <div class="col-6 col-lg-4"><span class="text-body-secondary">Type:</span>
                                    ${schedule.classSession.classTemplate.classType}</div>
                                <div class="col-6 col-lg-4"><span class="text-body-secondary">Intensity:</span>
                                    ${schedule.classSession.classTemplate.intensity}</div>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
        crossorigin="anonymous"></script>
</body>

</html>
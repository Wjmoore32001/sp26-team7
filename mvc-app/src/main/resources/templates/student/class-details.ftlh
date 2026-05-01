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
    <div id="nav-placeholder"></div>
    <script defer src="/assets/js/include-nav.js" data-nav="student"></script>

    <div class="container text-center">
        <div class="row mt-3">
            <div class="col p-1 bg-body-tertiary border border-primary-subtle rounded-3">
                <h1>Class Details</h1>
            </div>
        </div>
        <div class="row mt-3">
            <div class="col text-start">
                <div class="p-4 bg-body-tertiary border border-primary-subtle rounded-3">
                    <div class="row g-3 p-2 border border-secondary-subtle rounded-3 mb-3">
                        <div class="col-lg-8">
                            <h3 class="mb-1">${template.title}</h3>
                            <div class="text-body-secondary mb-2">${template.description}</div>
                            <div class="row small mb-2">
                                <div class="col-6 col-lg-4"><span class="text-body-secondary">Type:</span>
                                    ${template.classType}</div>
                                <div class="col-6 col-lg-4"><span class="text-body-secondary">Price:</span>
                                    $${template.price}</div>

                                <div class="col-6 col-lg-4"><span class="text-body-secondary">Intensity:</span>
                                    ${template.intensity}</div>
                                <div class="col-6 col-lg-4"><span class="text-body-secondary">Room:</span> Studio A
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <div class="p-2 border border-secondary-subtle rounded-3 h-100 text-center">
                                <div class="fw-semibold">${template.instructor.name}</div>
                                <div class="small text-body-secondary">Instructor</div>
                            </div>
                        </div>
                    </div>
                    <div class="row g-3 p-2 border border-secondary-subtle rounded-3">
                        <div class="d-flex align-items-center justify-content-between">
                            <h4 class="mb-0">Reviews</h4>
                            <a class="btn btn-outline-primary"
                                href="/student/classes/${template.classTemplateId}/reviews/new">Leave a Review</a>
                        </div>
                        <div class="col">
                            <#list reviews as review>
                                <div class="p-2 border border-secondary-subtle rounded-3 mb-3">
                                    <div class="d-flex justify-content-between">
                                        <div class="fw-semibold">${review.student.name}</div>
                                        <div class="small text-body-secondary">${review.rating}/5</div>
                                    </div>
                                    <div>${review.comment}</div>

                                    <#if review.replyText??>
                                        <div class="mt-2 p-2 border border-secondary-subtle rounded-3 bg-body-tertiary">
                                            <div class="small fw-semibold text-body-secondary mb-1">
                                                Instructor Reply
                                            </div>
                                            <div class="small">
                                                ${review.replyText}
                                            </div>
                                        </div>
                                    </#if>
                                </div>
                            </#list>
                        </div>

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
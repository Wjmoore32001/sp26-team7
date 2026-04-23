(async function () {
  const scriptTag = document.currentScript;
  const navType = scriptTag ? scriptTag.dataset.nav : null;
  const placeholder = document.getElementById("nav-placeholder");

  if (!placeholder) {
    return;
  }

  const filenameMap = {
    root: "nav-root.html",
    customer: "nav-customer.html",
    instructor: "nav-instructor.html"
  };

  const filename = filenameMap[navType] || "nav-root.html";
  const partialsDir = new URL("../partials/", scriptTag.src);
  const partialUrl = new URL(filename, partialsDir);

  fetch(partialUrl)
    .then(function (response) {
      if (!response.ok) {
        throw new Error("Could not load navbar.");
      }
      return response.text();
    })
    .then(function (html) {
      placeholder.innerHTML = html;
    })
    .catch(function (error) {
      console.log(error);
    });
})();

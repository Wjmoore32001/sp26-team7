(async function () {
  const scriptTag = document.currentScript;
  const navType = scriptTag?.dataset?.nav;

  const placeholder = document.getElementById("nav-placeholder");
  if (!placeholder) return;

  const filenameMap = {
    root: "nav-root.html",
    customer: "nav-customer.html",
    instructor: "nav-instructor.html",
  };

  const filename = filenameMap[navType] || filenameMap.root;

  // script is at: assets/js/include-nav.js
  // partials are at: assets/partials/*.html
  const partialUrl = '/assets/partials/${filename}';

  const response = await fetch(partialUrl);
  if (!response.ok) {
    console.error("Nav fetch failed:", response.status, partialUrl);
    return;
  }

  placeholder.innerHTML = await response.text();
})();
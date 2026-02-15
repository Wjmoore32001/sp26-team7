(async function () {
  const scriptTag = document.currentScript;
  const navType = scriptTag.dataset.nav;

  const placeholder = document.getElementById("nav-placeholder");

  const filenameMap = {
    root: "nav-root.html",
    customer: "nav-customer.html",
    instructor: "nav-instructor.html",
  };

  const filename = filenameMap[navType];

  const isRolePage =
    window.location.pathname.includes("/customer/") ||
    window.location.pathname.includes("/instructor/");

  const basePath = isRolePage ? "/high-fidelity-prototype/assets/partials/" : "/high-fidelity-prototype/assets/partials/";

  const response = await fetch(basePath + filename);
  const html = await response.text();

  placeholder.innerHTML = html;
})();

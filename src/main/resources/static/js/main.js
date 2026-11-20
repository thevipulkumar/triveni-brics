/* Triveni Bricks - small helpers. No libraries needed. */
(function () {
    "use strict";

    /* ---- mobile menu ---- */
    var menuBtn = document.getElementById("menuBtn");
    var mobileNav = document.getElementById("mobileNav");
    if (menuBtn && mobileNav) {
        menuBtn.addEventListener("click", function () {
            var open = mobileNav.classList.toggle("open");
            menuBtn.setAttribute("aria-expanded", open ? "true" : "false");
            menuBtn.innerHTML = open ? "&times;" : "&#9776;";
        });
    }

    /* ---- brick calculator ----
       A 9 inch wall needs about 13.5 bricks per square foot,
       a 4.5 inch wall about 6.75. Standard 9x4x3 inch brick with mortar. */
    var calcBtn = document.getElementById("calcBtn");
    if (calcBtn) {
        calcBtn.addEventListener("click", function () {
            var out = document.getElementById("calcOut");
            var len = parseFloat(document.getElementById("calcLen").value);
            var ht = parseFloat(document.getElementById("calcHt").value);
            var thick = parseFloat(document.getElementById("calcThick").value);

            out.hidden = false;

            if (!(len > 0) || !(ht > 0)) {
                out.classList.add("error");
                out.textContent = out.getAttribute("data-invalid");
                return;
            }

            var perSqFt = thick === 9 ? 13.5 : 6.75;
            var bricks = Math.ceil((len * ht * perSqFt) / 10) * 10;

            out.classList.remove("error");
            out.innerHTML =
                out.getAttribute("data-tpl").replace("{0}", bricks.toLocaleString("en-IN")) +
                '<span class="small">' + out.getAttribute("data-note") + "</span>";
        });
    }

    /* ---- ask before a delete ---- */
    document.querySelectorAll("form[data-confirm]").forEach(function (form) {
        form.addEventListener("submit", function (ev) {
            if (!window.confirm(form.getAttribute("data-confirm"))) {
                ev.preventDefault();
            }
        });
    });
})();

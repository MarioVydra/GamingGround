/** SHOP - SEARCHBAR **/

window.onscroll = function () {
    scroll();
};

function scroll() {
    let searchBar = document.querySelector(".shop-searchbar");
    let navbar = document.querySelector(".navbar");
    let header = document.querySelector(".page-header")
    let productSection = document.querySelector(".product-section")

    if (document.documentElement.scrollTop > (header.clientHeight - navbar.clientHeight)) {
        searchBar.style.position = "fixed";
        searchBar.style.top = navbar.clientHeight + "px";
        productSection.style.marginTop = searchBar.clientHeight + 50 + "px";
    } else {
        searchBar.style.position = "relative";
        searchBar.style.top = "0";
        productSection.style.margin = "0";
    }
}


/** SHOP PAGE - PRODUCT SORT **/

const bestButton = document.getElementById("best-button");
const cheapButton = document.getElementById("cheap-button");
const expensiveButton = document.getElementById("expensive-button");
const reviewsButton = document.getElementById("reviews-button");

if (bestButton) {
    bestButton.addEventListener("click", function () {
        deactivateButtons();
        bestButton.style.textDecoration = "underline";
    });
}

if (cheapButton) {
    cheapButton.addEventListener("click", function () {
        deactivateButtons();
        cheapButton.style.textDecoration = "underline";
    });
}

if (expensiveButton) {
    expensiveButton.addEventListener("click", function () {
        deactivateButtons();
        expensiveButton.style.textDecoration = "underline";
    });
}

if (reviewsButton) {
    reviewsButton.addEventListener("click", function () {
        deactivateButtons();
        reviewsButton.style.textDecoration = "underline";
    });
}

function deactivateButtons() {
    bestButton.style.textDecoration = "none";
    cheapButton.style.textDecoration = "none";
    expensiveButton.style.textDecoration = "none";
    reviewsButton.style.textDecoration = "none";
}


/** SHOP PAGE - SIDE BAR **/
const sidebarIcon = document.querySelector('.sidebar-icon');
const sidebar = document.querySelector('.sidebar');

if (sidebarIcon) {
    sidebarIcon.addEventListener('click', function () {
        if(sidebar.classList.contains('active')) {
            sidebar.classList.remove('active');
        } else {
            sidebar.classList.add('active');
        }
    });
}
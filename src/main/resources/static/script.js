/** NAVBAR **/
const hamburgerIcon = document.querySelector('.hamburger-image');
const menu = document.querySelector('.menu');
const closeIcon = document.querySelector('.close-image');
const body = document.body;

if (hamburgerIcon) {
    hamburgerIcon.addEventListener('click', function () {
        menu.classList.add('active');
    });
}

if (closeIcon) {
    closeIcon.addEventListener('click', function () {
        menu.classList.remove('active');
    });
}

body.addEventListener('click', function (e) {
    if (!menu.contains(e.target) && !hamburgerIcon.contains(e.target)) {
        menu.classList.remove('active');
    }
});


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


/** SLIDESHOW - HOMEPAGE **/
/** JS kód pre slideshow využitý zo stránky w3schools: https://www.w3schools.com/howto/howto_js_slideshow.asp **/

let slideIndex = 1;
let timeout;
showSlides(slideIndex);

function plusSlides(n) {
    showSlides(slideIndex += n);
}

function currentSlide(n) {
    showSlides(slideIndex = n);
}
function showSlides(n) {
    let i;
    let slides = document.getElementsByClassName("slides");
    let dots = document.getElementsByClassName("dot");
    if (n > slides.length) {
        slideIndex = 1
    }
    if (n < 1) {
        slideIndex = slides.length
    }
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    for (i = 0; i < dots.length; i++) {
        dots[i].className = dots[i].className.replace(" activeSlide", "");
    }
    slides[slideIndex-1].style.display = "block";
    dots[slideIndex-1].className += " activeSlide";

    if (timeout) {
        clearTimeout(timeout);
    }

    timeout = setTimeout(() => {
        plusSlides(1)
    }, 10000);
}

function logoutUser() {
    const request = new XMLHttpRequest();
    request.open("POST", "/api/user/logout", true);
    request.onreadystatechange = function() {
        if (this.readyState === XMLHttpRequest.DONE && this.status === 200)
        {
            alert("Log out was successful! Redirecting...");
            window.location.href = '/';
        }
    };

    request.send();
}


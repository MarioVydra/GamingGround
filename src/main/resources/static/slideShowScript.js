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
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

function loadProducts(page = 0, size = 5) {
    fetch(`/api/product/indexProducts?page=${page}&size=${size}`)
        .then(response => response.json())
        .then(pageData => {
            const productsContainer = document.querySelector(".products-container");
            productsContainer.innerHTML = "";

            pageData.content.forEach(product => {
                const productElement =
                    `<a href="/product-details/${product.id}">
                    <div class="product">
                        <img class="product-image" src="${product.imageUrl}" alt="${product.productTitle}">
                        <div class="product-description">
                            <h3>${product.productTitle}</h3>
                            <span>${product.description}</span>
                            <div class="product-price">
                                <h3>${product.price}€</h3>
                                <a id="cart-button" onclick="addToCart('${product.id}')"><button><img class="cart-image" src="/Images/cart.png" alt="Cart logo">  Add to cart</button></a>
                            </div>
                            <span>Quantity in stock: ${product.quantity}</span>
                            <h4>Average rating: ${product.averageRating}/5</h4>
                        </div>
                    </div>
                    </a>`;
                productsContainer.innerHTML += productElement;
            });
        })
        .catch(error => console.error("Error loading products:", error));
}

window.onload = () => loadProducts();

function subscribeNewsLetter() {
    const newsletterEmail = document.getElementById("newsletter-email");
    const newsletterEmailError = document.getElementById("newsletter-email-error");

    newsletterEmailError.innerText = "";
    newsletterEmail.style.borderColor = "black";

    if (newsletterEmail.value === "") {
        newsletterEmail.style.borderColor = "red";
        newsletterEmailError.innerText = "Email is required.";
        return false;
    }

    const emailRegex = /[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(newsletterEmail.value)) {
        newsletterEmailError.innerText = "Invalid email address.";
        newsletterEmail.style.borderColor = "red";
        return false;
    }

    const requestNewsletter = new XMLHttpRequest();
    requestNewsletter.open("POST", "/api/user/subscribeNewsletter/" + newsletterEmail.value, true);

    requestNewsletter.onreadystatechange = function () {
        if (requestNewsletter.readyState === 4) {
            if (requestNewsletter.status === 200) {
                console.log("Server response:", requestNewsletter.responseText);
                alert("You have successfully subscribed to the newsletter.");
                window.location.href = "/";
            } else if (requestNewsletter.status === 400) {
                console.log(requestNewsletter.responseText);
                if (requestNewsletter.responseText === "Invalid email address.") {
                    newsletterEmailError.innerText = " " + requestNewsletter.responseText;
                    newsletterEmail.style.borderColor = "red";
                }
            } else {
                console.error("Error:", requestNewsletter.status, requestNewsletter.statusText);
            }
        } else {
            console.error("Error:", requestNewsletter.status, requestNewsletter.statusText);
        }
    };
    requestNewsletter.send();
    return false;
}


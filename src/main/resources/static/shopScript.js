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

/** PRODUCT LOADING **/

let currentSort = "id";
let currentSearch = "";
let currentCategory = "";
let currentMinPrice = -1;
let currentMaxPrice = Infinity;

function loadProducts(page = 0, size = 10) {
    fetch(`/api/product/products?page=${page}&size=${size}
        &minPrice=${currentMinPrice}&maxPrice=${currentMaxPrice}&category=${currentCategory}
        &title=${currentSearch}&sortBy=${currentSort}`)
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
                                <a href="/cart"><button><img class="cart-image" src="/Images/cart.png" alt="Cart logo">  Add to cart</button></a>
                            </div>
                            <span>Quantity in stock: ${product.quantity}</span>
                        </div>
                    </div>
                    </a>`;
               productsContainer.innerHTML += productElement;
            });
            createPageButtons(pageData.totalPages, page);
        })
        .catch(error => console.error("Error loading products:", error));
}

function createPageButtons(totalPages, currentPage) {
    const pageContainer = document.querySelector(".pages");
    pageContainer.innerHTML = "";

    for (let i = 0; i < totalPages; i++) {
        const pageButton = document.createElement("button");
        pageButton.innerText = i + 1;
        pageButton.classList.add("page-button");
        if (i === currentPage) {
            pageButton.style.fontWeight = "bold";
            pageButton.style.textDecoration = "underline";
        } else {
            pageButton.style.fontWeight = "none";
            pageButton.style.textDecoration = "none";
        }
        pageButton.addEventListener("click", () => loadProducts(i));
        pageContainer.appendChild(pageButton);
    }

}

window.onload = () => loadProducts();

/** SHOP PAGE - PRODUCT SORT & FILTER **/

const cheapButton = document.getElementById("cheap-button");
const expensiveButton = document.getElementById("expensive-button");
const reviewsButton = document.getElementById("reviews-button");
const gamesButton = document.getElementById("games-button");
const gpuButton = document.getElementById("gpu-button");
const consoleButton = document.getElementById("console-button");

if (cheapButton) {
    cheapButton.addEventListener("click", function () {
        if (cheapButton.style.textDecoration === "underline") {
            deactivateSortButtons();
        } else {
            deactivateSortButtons();
            cheapButton.style.textDecoration = "underline";
            currentSort = "priceAsc";
        }
        loadProducts();
    });
}

if (expensiveButton) {
    expensiveButton.addEventListener("click", function () {
        if (expensiveButton.style.textDecoration === "underline") {
            deactivateSortButtons();
        } else {
            deactivateSortButtons();
            expensiveButton.style.textDecoration = "underline";
            currentSort = "priceDesc";
        }
        loadProducts();
    });
}

if (reviewsButton) {
    reviewsButton.addEventListener("click", function () {
        if (reviewsButton.style.textDecoration === "underline") {
            deactivateSortButtons();
        } else {
            deactivateSortButtons();
            reviewsButton.style.textDecoration = "underline";
            currentSort = "reviews";
        }
        loadProducts();
    });
}

function deactivateSortButtons() {
    cheapButton.style.textDecoration = "none";
    expensiveButton.style.textDecoration = "none";
    reviewsButton.style.textDecoration = "none";
    currentSort = "";
}

if (gamesButton) {
    gamesButton.addEventListener("click", function () {
        if (gamesButton.style.textDecoration === "underline") {
            deactivateCategoryButtons();
        } else {
            deactivateCategoryButtons();
            gamesButton.style.textDecoration = "underline";
            currentCategory = "game";
        }
        loadProducts();
    });
}

if (gpuButton) {
    gpuButton.addEventListener("click", function () {
        if (gpuButton.style.textDecoration === "underline") {
            deactivateCategoryButtons();
        } else {
            deactivateCategoryButtons();
            gpuButton.style.textDecoration = "underline";
            currentCategory = "gpu";
        }
        loadProducts();
    });
}

if (consoleButton) {
    consoleButton.addEventListener("click", function () {
        if (consoleButton.style.textDecoration === "underline") {
            deactivateCategoryButtons();
        } else {
            deactivateCategoryButtons();
            consoleButton.style.textDecoration = "underline";
            currentCategory = "console";
        }
        loadProducts();
    });
}

function deactivateCategoryButtons() {
    gamesButton.style.textDecoration = "none";
    gpuButton.style.textDecoration = "none";
    consoleButton.style.textDecoration = "none";
    currentCategory = "";
}

function updatePriceFilter() {
    const minPriceInput = document.getElementById("min-price");
    const maxPriceInput = document.getElementById("max-price");

    currentMinPrice = minPriceInput.value ? parseFloat(minPriceInput.value) : -1;
    currentMaxPrice = maxPriceInput.value ? parseFloat(maxPriceInput.value) : Infinity;

    loadProducts();
}

/**  PRODUCT SEARCH **/
document.getElementById("search-input").addEventListener("keyup", function (e) {
    currentSearch = e.target.value;
    loadProducts();
});

/** ADMIN FILE UPLOAD **/
document.getElementById("upload-form").addEventListener("submit", function(e) {
    e.preventDefault();
    const fileInput = document.getElementById("fileInput");
    const formData = new FormData();
    formData.append("file", fileInput.files[0]);

    fetch("/api/product/upload", {
        method: "POST",
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            console.log("Success", data);
        })
        .catch(error => console.error("Error uploading products:", error));
});


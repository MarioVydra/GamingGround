document.addEventListener("DOMContentLoaded", function () {
    loadCart();
});

function loadCart() {
    const itemList = document.querySelector(".itemList");
    itemList.innerHTML = "<h1>Item List</h1>";
    fetch("/api/cart/items")
        .then(response => response.json())
        .then(cartResponse => {
                cartResponse.products.forEach(product => {
                    const item = document.createElement("div");
                    item.className = "items";
                    item.innerHTML = `
                        <div>
                            <img class="item-picture" src="${product.imageUrl}" alt="Product picture">
                        </div>
                        <div class="item-info">
                            <a href="/product-details/${product.id}"><h2>${product.productTitle}</h2></a>
                        </div>
                        <div class="item-price">
                            <h2>${product.price}€</h2>
                        </div>
                        <div>
                            <img onclick="removeFromCart('${product.id}')" class="delete-item" src="/Images/close.png" alt="Delete logo">
                        </div>
                    `;
                    itemList.appendChild(item);
                });
                const finalPriceElement = document.createElement("h2");
                finalPriceElement.innerHTML = "Final price: " + cartResponse.finalPrice + "€";
                itemList.appendChild(finalPriceElement);
        })
        .catch(error => console.error("Error loading cart contents:", error));
}

function removeFromCart(productId) {
    fetch("/api/cart/remove/" + productId, {
        method: "DELETE"
    })
        .then(response => {
            if (response.ok) {
                alert("Item removed from the cart.");
                loadCart();
            } else {
                alert("Error while removing item from the cart.");
            }
        })
        .catch(error => console.error("Error removing item from cart:", error));
}

document.getElementById("profile-submit").onclick = function () {
    fetch("/api/cart/order", {
        method: "POST"
    })
        .then(response => {
            if (response.ok) {
                alert("Order processed successfully");
                loadCart();
            } else {
                alert("Error processing order.")
            }
        })
        .catch(error => console.error("Error processing order:", error));
}
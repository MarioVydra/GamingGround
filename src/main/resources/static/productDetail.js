document.addEventListener("DOMContentLoaded", function() {
   const productId= window.location.pathname.split("/")[2];
   fetch(`/api/product/${productId}`)
       .then(response => response.json())
       .then(product => {
           document.getElementById("product-title").innerText = product.productTitle;
           document.getElementById("product-description").innerText = product.description;
           document.getElementById("product-price").innerText = `${product.price}€`;
           document.getElementById("product-quantity").innerText = `Quantity in stock: ${product.quantity}`;
           document.querySelector(".product-img").setAttribute("src", product.imageUrl);
       })
       .catch(error => console.error("Error loading product:", error));
});
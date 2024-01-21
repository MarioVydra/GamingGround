/* GET PRODUCT INFO */
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
    loadReviews();
});

/* ADD REVIEW */
document.getElementById("reviewForm").addEventListener("submit", function (e) {
    e.preventDefault()

    const rating = document.getElementById("reviewRating");
    const content = document.getElementById("reviewContent");
    const productId = window.location.pathname.split("/")[2];

    const reviewData = {
        content: content.value,
        rating: parseInt(rating.value),
        productId: productId
    };

    fetch("/api/review/save", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(reviewData),
    })
    .then(response => response.text())
    .then(data => {
        console.log("Success:", data);
        alert(data);
        window.location.href = window.location.pathname;
    })
    .catch((error) => {
        console.error("Error", error);
        alert(error);
    })
});
document.addEventListener("DOMContentLoaded", function() {

});

function loadReviews() {
    const productId = window.location.pathname.split("/")[2];
    const reviewsContainer = document.querySelector(".reviews");
    reviewsContainer.innerHTML = "<h1>Reviews</h1>";

    fetch(`/api/review/reviews/${productId}`)
        .then(response => response.json())
        .then(reviews => {
            reviews.forEach(review => {
                const reviewDiv = document.createElement("div");
                reviewDiv.className = "reviewContainer";

                reviewDiv.innerHTML = `
                    <div class="reviewHeader">
                        <h4>Rating: ${review.rating}/5</h4>
                        <h4>${review.nameAndSurname}</h4>
                        <h4>${new Date(review.date).toLocaleString("sk-SK")}</h4>
                        ${review.canEdit ? '<div><img class="edit-review" src="/Images/edit-review.png" alt="Edit logo" onclick="editReview('+ review.id +')"><img class="delete-review" src="/Images/close.png" alt="Delete logo" onclick="deleteReview('+ review.id +')"></div>' : ''}  
                    </div>
                    <textarea id="review_${review.id}" name="review" rows="4" cols="50" readonly>${review.content}</textarea>
                `;
                console.log(review.content)
                reviewsContainer.appendChild(reviewDiv);
            });
        })
        .catch(error => console.error("Error loading product:", error));
}
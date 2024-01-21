/* GET PRODUCT INFO */
document.addEventListener("DOMContentLoaded", function() {

   const productId= window.location.pathname.split("/")[2];
   fetch("/api/product/" + productId)
       .then(response => response.json())
       .then(product => {
           document.getElementById("cart-button").onclick = function () {
               addToCart(product.id);
           };
           document.getElementById("product-title").innerText = product.productTitle;
           document.getElementById("product-description").innerText = product.description;
           document.getElementById("product-price").innerText = product.price + "€";
           document.getElementById("product-quantity").innerText = "Quantity in stock: " + product.quantity;
           document.querySelector(".product-img").setAttribute("src", product.imageUrl);
           document.getElementById("product-rating").innerText = "Average rating: " + product.averageRating + "/5";
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
        location.reload();
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

    fetch("/api/review/reviews/" + productId)
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
                        ${review.canEdit ? `<div><img class="edit-review" src="/Images/edit-review.png" alt="Edit logo" onclick="editReview('${review.id}')"><img class="delete-review" src="/Images/close.png" alt="Delete logo" onclick="deleteReview('${review.id}')"></div>` : ""}  
                    </div>
                    <textarea id="review_${review.id}" name="review" rows="4" cols="50" readonly>${review.content}</textarea>
                `;
                console.log(review.content)
                reviewsContainer.appendChild(reviewDiv);
            });
        })
        .catch(error => console.error("Error loading product:", error));
}

function deleteReview(reviewId) {
    if (confirm("Are you sure you want to delete this review?")) {
        fetch("/api/review/delete/" + reviewId, { method: "DELETE" })
        .then(response => {
            if (response.ok) {
                alert("Review deleted successfully.");
                location.reload();
            } else {
                alert("Error deleting review.")
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Error deleting review: " + error);
        });
    }
}

function editReview(reviewId) {
    const reviewTextArea = document.getElementById("review_" + reviewId);
    reviewTextArea.removeAttribute("readonly");
    reviewTextArea.style.border = "2px solid black";

    const saveButton = document.createElement("button");
    saveButton.innerText = "Save";
    reviewTextArea.parentNode.insertBefore(saveButton, reviewTextArea);
    saveButton.onclick = function() {
        fetch("/api/review/update/" + reviewId, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ content: reviewTextArea.value})
        })
            .then(response => {
                if (response.ok) {
                    alert("Review updated successfully");
                    reviewTextArea.setAttribute("readonly", "readonly");
                    reviewTextArea.style.border = "none";
                    saveButton.remove();
                } else {
                    alert("Error updating review.");
                }
            })
            .catch(error => console.error("Error", error));
    };

}


/* ADD TO CART */
function addToCart(productId) {
    fetch("/api/cart/add/" + productId, {
        method: "POST",
    })
    .then(response => {
        if (response.ok) {
            alert("Item successfully added to the cart.");
        } else {
            alert("Error while adding item to the cart.");
        }
    })
    .catch(error => console.error("Error", error));
}
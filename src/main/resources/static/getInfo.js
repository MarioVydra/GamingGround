document.addEventListener("DOMContentLoaded", function () {
    const username = document.getElementById("accountEmail").textContent;
    const emailInput = document.getElementById("email");
    const roleInput = document.getElementById("role");
    const nameInput = document.getElementById("name");
    const surnameInput = document.getElementById("surname");
    const phoneNumberInput = document.getElementById("phoneNumber");
    const dateOfBirthInput = document.getElementById("dateOfBirth");
    const cityInput = document.getElementById("city");
    const streetInput = document.getElementById("street");
    const numberInput = document.getElementById("number");
    const zipcodeInput = document.getElementById("zipcode");
    const countryInput = document.getElementById("country");

    fetch("/api/user/" + username)
        .then (function (response) {
            if (!response.ok) {
                throw new Error("Network response was not ok: " + response);
            }
            return response.json();
        })
        .then(function (user) {
            emailInput.value = user.email || "";
            if (roleInput) {
                roleInput.value = user.roles || "";
            }
            nameInput.value = user.name || "";
            surnameInput.value = user.surname || "";
            phoneNumberInput.value = user.phoneNumber || "";
            dateOfBirthInput.value = formatDateString(user.dateOfBirth) || "";
        })
        .catch(function (error) {
           console.error("There was a problem with the fetch operation: ", error);
        });

    fetch("/api/address/" + username)
        .then (function (response) {
            if (!response.ok) {
                throw new Error("Network response was not ok: " + response);
            }
            return response.json();
        })
        .then(function (address) {
            cityInput.value = address.city || "";
            streetInput.value = address.street || "";
            numberInput.value = address.number || "";
            zipcodeInput.value = address.zipcode || "";
            countryInput.value = address.country || "";
        })
        .catch(function (error) {
            console.error("There was a problem with the fetch operation: ", error);
        });
});

function formatDateString(dateString) {
    let parts = dateString.split("-");
    return parts[2] + "." + parts[1] + "." + parts[0];
}
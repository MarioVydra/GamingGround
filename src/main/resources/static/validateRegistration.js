function validateRegistration() {
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var passwordRepeat = document.getElementById("password-repeat").value;
    var name = document.getElementById("name").value;
    var surname = document.getElementById("surname").value;
    var telephone = document.getElementById("telephone").value;
    var street = document.getElementById("street").value;
    var number = document.getElementById("number").value;
    var code = document.getElementById("code").value;
    var country = document.getElementById("country").value;

    if (email === "") {
        alert("Email is required.");
        return;
    }
    if (password === "") {
        alert("Password is required.");
        return;
    }
    if (passwordRepeat === "") {
        alert("Repeat password is required.");
        return;
    }
    if (name === "") {
        alert("Name is required.");
        return;
    }
    if (surname === '') {
        alert("Surname is required.");
        return;
    }
    if (telephone === "") {
        alert("Telephone number is required.");
        return;
    }
    if (street === "") {
        alert("Street is required.");
        return;
    }
    if (number === "") {
        alert("Number of house is required.");
        return;
    }
    if (code === "") {
        alert("ZIP code is required.");
        return;
    }
    if (country === "") {
        alert("Country is required.");
        return;
    }

    if (password !== passwordRepeat) {
        alert("Password and repeat password must be the same.");
        return;
    }

    var emailPattern = /[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(email)) {
        alert("Invalid email address.");
        return;
    }

    if (password.length < 10 && passwordRepeat.length < 10) {
        alert("Password must have atleast 10 characters.");
        return;
    }

    var phonePattern = /^\+[0-9]{1,12}$/;
    if (!phonePattern.test(telephone)) {
        alert("Telephone number is in the wrong format.");
        return;
    }

    var zipCodePattern = /^\d{3} \d{2}$/;
    if (!zipCodePattern.test(code)) {
        alert("ZIP Code is in the wrong format.");
        return;
    }

    var registrationData = {
        email: email,
        password: password,
        name: name,
        surname: surname,
        phoneNumber: telephone,
        street: street,
        number: number,
        zipcode: code,
        country: country
    };

    fetch('/api/user/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(registrationData),
    })
        .then(response => response.text())
        .then(data => {
            console.log(data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

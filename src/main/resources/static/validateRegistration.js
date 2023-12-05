function validateRegistration() {
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var passwordRepeat = document.getElementById("password-repeat").value;
    var name = document.getElementById("name").value;
    var surname = document.getElementById("surname").value;
    var telephone = document.getElementById("telephone").value;
    var dateOfBirth = document.getElementById("dateOfBirth").value;
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
    if (dateOfBirth === "") {
        alert("Date of birth is required.");
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

    var emailRegex = /[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        alert("Invalid email address.");
        return;
    }

    if (password.length < 10 && passwordRepeat.length < 10) {
        alert("Password must have atleast 10 characters.");
        return;
    }

    var dateOfBirthRegex = /^(0[1-9]|[12][0-9]|3[01])\.(0[1-9]|1[0-2])\.\d{4}$/;
    if (!dateOfBirthRegex.test(dateOfBirth)) {
        alert("Date of birth is in the wrong format.");
        return;
    }

    var parts = dateOfBirth.split(".");
    var day = parseInt(parts[0], 10);
    var month = parseInt(parts[1], 10);
    var year = parseInt(parts[2], 10);

    var date = new Date(year, month - 1, day);
    if (date.getDate() !== day || date.getMonth() + 1 !== month || date.getFullYear() !== year) {
        alert("The specified date does not exist.");
        return;
    }

    var currentDate = new Date();

    var timeDiff = currentDate - date; // in millis
    var age = Math.floor(timeDiff / (365.25 * 24 * 60 * 60 * 1000));

    if (age < 15) {
        alert("User is not older than 15 years.");
        return;
    }

    var phoneRegex = /^\+[0-9]{1,12}$/;
    if (!phoneRegex.test(telephone)) {
        alert("Telephone number is in the wrong format.");
        return;
    }

    var zipCodeRegex = /^\d{3} \d{2}$/;
    if (!zipCodeRegex.test(code)) {
        alert("ZIP Code is in the wrong format.");
        return;
    }

    var registrationData = {
        email: email,
        password: password,
        name: name,
        surname: surname,
        phoneNumber: telephone,
        dateOfBirth: dateOfBirth,
        street: street,
        number: number,
        zipcode: code,
        country: country
    };

    fetch('/api/user/save', {
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

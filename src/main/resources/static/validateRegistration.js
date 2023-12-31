function validateRegistration() {

    const inputs = [
        { id: "email", label: "Email", regex: /[^\s@]+@[^\s@]+\.[^\s@]+$/ },
        { id: "password", label: "Password", minLength: 10 },
        { id: "repeatPassword", label: "Repeat password", minLength: 10 },
        { id: "name", label: "Name" },
        { id: "surname", label: "Surname" },
        { id: "phoneNumber", label: "Telephone", regex: /^\+[0-9]{1,12}$/ },
        { id: "dateOfBirth", label: "Date of birth", formatRegex: /^(0[1-9]|[12][0-9]|3[01])\.(0[1-9]|1[0-2])\.\d{4}$/ },
        { id: "street", label: "Street" },
        { id: "number", label: "Number" },
        { id: "zipcode", label: "ZIP Code", regex: /^\d{3} \d{2}$/ },
        { id: "country", label: "Country" },
        { id: "privacyPolicy", label: "Privacy policy and Terms & conditions" }
    ];

    let invalidInput = false;

    let elements = [];

    for (let input of inputs) {
        let element = document.getElementById(input.id);
        let elementError = document.getElementById(input.id + "-error");
        if (elementError) {
            elementError.innerText = "";
        }
        if (element) {
            element.style.borderColor = "black";
        }
        if (input.id !== "repeatPassword" || input.id !== "privacyPolicy") {
            elements.push(element);
        }

        if (element.value === "") {
            element.style.borderColor = "red";
            elementError.innerText = input.label + " is required.";
            invalidInput = true;
        }

        if (input.regex && !input.regex.test(element.value)) {
            elementError.innerText = input.label + " is in the wrong format.";
            element.style.borderColor = "red";
            invalidInput = true;
        }

        if (input.minLength && element.value.length < input.minLength) {
            elementError.innerText = input.label + " must have at least " + input.minLength + " characters.";
            element.style.borderColor = "red";
            invalidInput = true;
        }

        if (input.id === "repeatPassword" && element.value !== document.getElementById("password").value) {
            elementError.innerText = "Passwords must be the same.";
            element.style.borderColor = "red";
            invalidInput = true;
        }

        if (input.id === "dateOfBirth") {
            let parts = element.value.split(".");
            let day = parseInt(parts[0], 10);
            let month = parseInt(parts[1], 10);
            let year = parseInt(parts[2], 10);

            let date = new Date(year, month - 1, day);
            if (date.getDate() !== day || date.getMonth() + 1 !== month || date.getFullYear() !== year) {
                elementError.innerText = "The specified date does not exist.";
                element.style.borderColor = "red";
                invalidInput = true;
            }

            let currentDate = new Date();
            let timeDiff = currentDate - date; // in millis
            let age = Math.floor(timeDiff / (365.25 * 24 * 60 * 60 * 1000));

            if (age < 15) {
                elementError.innerText = "You are not older than 15 years.";
                element.style.borderColor = "red";
                invalidInput = true;
            }

            if (timeDiff < 0) {
                elementError.innerText = "You are not from the future.";
                element.style.borderColor = "red";
                invalidInput = true;
            }
        }

        if (input.id === "privacyPolicy") {
            if (!element.checked) {
                elementError.innerText = "You must agree with this.";
                invalidInput = true;
            }
        }
    }
    /*
    const email = document.getElementById("email");
    const password = document.getElementById("password");
    const passwordRepeat = document.getElementById("repeatPassword");
    const name = document.getElementById("name");
    const surname = document.getElementById("surname");
    const telephone = document.getElementById("phoneNumber");
    const dateOfBirth = document.getElementById("dateOfBirth");
    const street = document.getElementById("street");
    const number = document.getElementById("number");
    const code = document.getElementById("zipcode");
    const country = document.getElementById("country");

    const emailError = document.getElementById("email-error");
    const passwordError = document.getElementById("password-error");
    const passwordRepeatError = document.getElementById("repeatPassword-error");
    const telephoneError = document.getElementById("phoneNumber-error");
    const dateOfBirthError = document.getElementById("dateOfBirth-error");
    const codeError = document.getElementById("zipcode-error");


    const emailRegex = /[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email.value)) {
        emailError.innerText = "Invalid email address.";
        email.style.borderColor = "red";
        invalidInput = true;
    }

    if (password.value.length < 10) {
        passwordError.innerText = "Password must have at least 10 characters.";
        password.style.borderColor = "red";
        invalidInput = true;
    }

    if (password.value !== passwordRepeat.value) {
        passwordError.innerText = "Passwords must be the same.";
        passwordRepeatError.innerText = "Passwords must be the same.";
        password.style.borderColor = "red";
        passwordRepeat.style.borderColor = "red";
        invalidInput = true;
    }


    const phoneRegex = /^\+[0-9]{1,12}$/;
    if (!phoneRegex.test(telephone.value)) {
        telephoneError.innerText = "Telephone number is in the wrong format.";
        telephone.style.borderColor = "red";
        invalidInput = true;
    }

    const dateOfBirthRegex = /^(0[1-9]|[12][0-9]|3[01])\.(0[1-9]|1[0-2])\.\d{4}$/;
    if (!dateOfBirthRegex.test(dateOfBirth.value)) {
        dateOfBirthError.innerText = "Date of birth is in the wrong format.";
        dateOfBirth.style.borderColor = "red";
        invalidInput = true;
    }

    let parts = dateOfBirth.value.split(".");
    let day = parseInt(parts[0], 10);
    let month = parseInt(parts[1], 10);
    let year = parseInt(parts[2], 10);

    let date = new Date(year, month - 1, day);
    if (date.getDate() !== day || date.getMonth() + 1 !== month || date.getFullYear() !== year) {
        dateOfBirthError.innerText = "The specified date does not exist.";
        dateOfBirth.style.borderColor = "red";
        invalidInput = true;
    }

    let currentDate = new Date();
    let timeDiff = currentDate - date; // in millis
    let age = Math.floor(timeDiff / (365.25 * 24 * 60 * 60 * 1000));

    if (age < 15) {
        dateOfBirthError.value.innerText = "User is not older than 15 years.";
        dateOfBirth.style.borderColor = "red";
        invalidInput = true;
    }

    const zipCodeRegex = /^\d{3} \d{2}$/;
    if (!zipCodeRegex.test(code.value)) {
        codeError.innerText = "ZIP Code is in the wrong format.";
        code.style.borderColor = "red";
        invalidInput = true;
    }*/

    if (invalidInput) {
        return false;
    }

    const registrationData = {
        email: elements[0].value,
        password: elements[1].value,
        name: elements[2].value,
        surname: elements[3].value,
        phoneNumber: elements[4].value,
        dateOfBirth: elements[5].value,
        street: elements[6].value,
        number: elements[7].value,
        zipcode: elements[8].value,
        country: elements[9].value
    };

    const request = new XMLHttpRequest();
    request.open("POST", "/api/user/save", true);
    request.setRequestHeader("Content-Type", "application/json");

    request.onreadystatechange = function () {
        if (request.readyState === 4) {
            if (request.status === 200) {
                console.log("Server response:", request.responseText);
                alert("Registration was successful!");
                window.location.href = "/";
            } else if (request.status === 400) {
                console.log(request.responseText);
                if (isJSON(request.responseText)) {
                    let errors = JSON.parse(request.responseText);
                    if (errors && errors.errors) {
                       handleRegistrationErrors(errors);
                    }
                } else {
                    console.error("Error:", request.status, request.statusText);
                    if (request.responseText === "User with this email is already registered.") {
                        document.getElementById("email-error").innerText = "User with this email is already registered.";
                        elements[0].style.borderColor = "red";
                    } else {
                        alert("Registration failed. Status code: " + request.status + ". Error message: " + request.responseText + ".");
                    }
                }
            } else {
                console.error("Error:", request.status, request.statusText);
                alert("Registration failed. Status code: " + request.status + ". Error message: " + request.responseText + ".");
            }
        }
    };


    request.send(JSON.stringify(registrationData));
    return false;
}

function handleRegistrationErrors(errors) {
    let errorList = errors.errors;
    for (let i = 0; i < errorList.length; i++) {
        let error = errorList[i];
        let fieldName = error.split(":")[0].trim();
        let errorMessage = error.split(":")[1].trim();
        let element = document.getElementById(fieldName + "-error");
        if (element) {
                element.innerText = errorMessage;
            }
        }
}

function isJSON(responseText) {
    try {
        JSON.parse(responseText);
        return true;
    } catch (e) {
        console.error("Error parsing JSON:", e);
        return false;
    }
}

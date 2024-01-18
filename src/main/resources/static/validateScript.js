function validateRegistration() {

    const inputs = [
        { id: "email", label: "Email", regex: /[^\s@]+@[^\s@]+\.[^\s@]+$/ },
        { id: "password", label: "Password", minLength: 10 },
        { id: "repeatPassword", label: "Repeat password", minLength: 10 },
        { id: "name", label: "Name" },
        { id: "surname", label: "Surname" },
        { id: "phoneNumber", label: "Telephone", regex: /^\+[0-9]{1,12}$/ },
        { id: "dateOfBirth", label: "Date of birth", formatRegex: /^(0[1-9]|[12][0-9]|3[01])\.(0[1-9]|1[0-2])\.\d{4}$/ },
        { id: "city", label: "City"},
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
        if (input.id !== "repeatPassword" && input.id !== "privacyPolicy") {
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
        city: elements[6].value,
        street: elements[7].value,
        number: elements[8].value,
        zipcode: elements[9].value,
        country: elements[10].value
    };

    const requestRegister = new XMLHttpRequest();
    requestRegister.open("POST", "/api/user/save", true);
    requestRegister.setRequestHeader("Content-Type", "application/json");

    requestRegister.onreadystatechange = function () {
        if (requestRegister.readyState === 4) {
            if (requestRegister.status === 200) {
                console.log("Server response:", requestRegister.responseText);
                alert("Registration was successful!");
                window.location.href = "/login";
            } else if (requestRegister.status === 400) {
                console.log(requestRegister.responseText);
                if (isJSON(requestRegister.responseText)) {
                    let errors = JSON.parse(requestRegister.responseText);
                    if (errors && errors.errors) {
                        handleErrors(errors);
                    }
                } else {
                    console.error("Error:", requestRegister.status, requestRegister.statusText);
                    if (requestRegister.responseText === "User with this email is already registered.") {
                        document.getElementById("email-error").innerText = " " + requestRegister.responseText;
                        elements[0].style.borderColor = "red";
                    } else if (requestRegister.responseText === "Invalid date format. Expected format: dd.MM.yyyy") {
                        document.getElementById("email-error").innerText = " " + requestRegister.responseText;
                        elements[5].style.borderColor = "red";
                    } else {
                        alert("Registration failed. Status code: " + requestRegister.status + ". Error message: " + requestRegister.responseText + ".");
                    }
                }
            } else {
                console.error("Error:", requestRegister.status, requestRegister.statusText);
                alert("Registration failed. Status code: " + requestRegister.status + ". Error message: " + requestRegister.responseText + ".");
            }
        }
    };

    requestRegister.send(JSON.stringify(registrationData));
    return false;
}

function loginUser() {
    const email = document.getElementById("email");
    const password = document.getElementById("password");

    const emailError = document.getElementById("email-error");
    const passwordError = document.getElementById("password-error");

    emailError.innerText = "";
    passwordError.innerText = "";
    email.style.borderColor = "black";
    password.style.borderColor = "black";

    if (email.value === "") {
        email.style.borderColor = "red";
        emailError.innerText = "Email is required.";
        return false;
    }

    const emailRegex = /[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email.value)) {
        emailError.innerText = " Invalid email address.";
        email.style.borderColor = "red";
        return false;
    }

    if (password.value === "") {
        password.style.borderColor = "red";
        passwordError.innerText = "Password is required.";
        return false;
    }

    if (password.value.length < 10) {
        passwordError.innerText = " Password must have at least 10 characters.";
        password.style.borderColor = "red";
        return false;
    }

    const loginData = {
        email: email.value,
        password: password.value
    };

    const requestLogin = new XMLHttpRequest();
    requestLogin.open("POST", "/api/user/login", true);
    requestLogin.setRequestHeader("Content-Type", "application/json");

    requestLogin.onreadystatechange = function () {
        if (requestLogin.readyState === 4) {
            if (requestLogin.status === 200) {
                console.log("Server response:", requestLogin.responseText);
                alert("Login was successful!");
                window.location.href = "/";
            } else if (requestLogin.status === 400) {
                console.log(requestLogin.responseText);
                if (isJSON(requestLogin.responseText)) {
                    let errors = JSON.parse(requestLogin.responseText);
                    if (errors && errors.errors) {
                        handleErrors(errors);
                    }
                } else {
                    console.error("Error:", requestLogin.status, requestLogin.statusText);
                    if (requestLogin.responseText === "Invalid password.") {
                        document.getElementById("password-error").innerText = " " + requestLogin.responseText;
                        password.style.borderColor = "red";
                    } else if (requestLogin.responseText === "The user with the given email does not exist.") {
                        document.getElementById("email-error").innerText = " " + requestLogin.responseText;
                        email.style.borderColor = "red";
                    } else {
                        alert("Login failed. Status code: " + requestLogin.status + ". Error message: " + requestLogin.responseText + ".");
                    }
                }
            } else {
                console.error("Error:", requestLogin.status, requestLogin.statusText);
                alert("Login failed. Status code: " + requestLogin.status + ". Error message: " + requestLogin.responseText + ".");
            }
        }
    };

    requestLogin.send(JSON.stringify(loginData));
    return false;
}

function changePassword() {
    const username = document.getElementById("accountEmail").textContent;

    const oldPassword = document.getElementById("oldPassword");
    const oldPasswordError = document.getElementById("oldPassword-error");

    const password = document.getElementById("password");
    const passwordError = document.getElementById("password-error");

    const repeatPassword = document.getElementById("repeatPassword");
    const repeatPasswordError = document.getElementById("repeatPassword-error");

    oldPasswordError.innerText = "";
    passwordError.innerText = "";
    repeatPasswordError.innerText = "";
    oldPassword.style.borderColor = "black";
    password.style.borderColor = "black";
    repeatPassword.style.borderColor = "black";

    if (oldPassword.value === "") {
        oldPassword.style.borderColor = "red";
        oldPasswordError.innerText = "Old password is required.";
        return false;
    }

    if (oldPassword.value.length < 10) {
        oldPasswordError.innerText = "Password must have at least 10 characters.";
        oldPassword.style.borderColor = "red";
        return false;
    }

    if (password.value === "") {
        password.style.borderColor = "red";
        passwordError.innerText = "Password is required.";
        return false;
    }

    if (password.value.length < 10) {
        passwordError.innerText = "Password must have at least 10 characters.";
        password.style.borderColor = "red";
        return false;
    }

    if (repeatPassword.value === "") {
        repeatPassword.style.borderColor = "red";
        repeatPasswordError.innerText = "Password is required.";
        return false;
    }

    if (repeatPassword.value.length < 10) {
        repeatPasswordError.innerText = "Password must have at least 10 characters.";
        repeatPassword.style.borderColor = "red";
        return false;
    }

    if (password.value !== repeatPassword.value) {
        passwordError.innerText = "Passwords must be the same.";
        password.style.borderColor = "red";
        repeatPasswordError.innerText = "Passwords must be the same.";
        repeatPassword.style.borderColor = "red";
        return false;
    }

    const changePasswordData = {
        email: username,
        oldPassword: oldPassword.value,
        password: password.value
    };

    const request = new XMLHttpRequest();
    request.open("PUT", "/api/user/changePassword", true);
    request.setRequestHeader("Content-Type", "application/json");

    request.onreadystatechange = function () {
        if (request.readyState === 4) {
            if (request.status === 200) {
                console.log("Server response:", request.responseText);
                alert("Change of password was successful!");
                window.location.href = "/login";
            } else if (request.status === 400) {
                console.log(request.responseText);
                if (isJSON(request.responseText)) {
                    let errors = JSON.parse(request.responseText);
                    if (errors && errors.errors) {
                        handleErrors(errors);
                    }
                } else {
                    console.error("Error:", request.status, request.statusText);
                    if (request.responseText === "Invalid old password.") {
                        oldPassword.style.borderColor = "red";
                        oldPasswordError.innerText = request.responseText;
                    } else {
                        alert("Login failed. Status code: " + request.status + ". Error message: " + request.responseText + ".");
                    }
                }
            } else {
                console.error("Error:", request.status, request.statusText);
                alert("Login failed. Status code: " + request.status + ". Error message: " + request.responseText + ".");
            }
        }
    };

    request.send(JSON.stringify(changePasswordData));
    return false;
}

function deleteAccount() {
    const username = document.getElementById("accountEmail").textContent;

    const password2 = document.getElementById("password2");
    const passwordError2 = document.getElementById("password-error2");

    const repeatPassword2 = document.getElementById("repeatPassword2");
    const repeatPasswordError2 = document.getElementById("repeatPassword-error2");

    passwordError2.innerText = "";
    repeatPasswordError2.innerText = "";
    password2.style.borderColor = "black";
    repeatPassword2.style.borderColor = "black";

    if (password2.value === "") {
        password2.style.borderColor = "red";
        passwordError2.innerText = "Password is required.";
        return false;
    }

    if (password2.value.length < 10) {
        passwordError2.innerText = "Password is invalid.";
        password2.style.borderColor = "red";
        return false;
    }

    if (repeatPassword2.value === "") {
        repeatPassword2.style.borderColor = "red";
        repeatPasswordError2.innerText = "Repeat password is required.";
        return false;
    }

    if (repeatPassword2.value.length < 10) {
        repeatPasswordError2.innerText = "Repeat password is invalid.";
        repeatPassword2.style.borderColor = "red";
        return false;
    }

    if (password2.value !== repeatPassword2.value) {
        passwordError2.innerText = "Passwords must be the same.";
        password2.style.borderColor = "red";
        repeatPasswordError2.innerText = "Passwords must be the same.";
        repeatPassword2.style.borderColor = "red";
        return false;
    }

    const deleteData = {
        email: username,
        password2: password2.value
    };

    const request = new XMLHttpRequest();
    request.open("DELETE", "/api/user/delete", true);
    request.setRequestHeader("Content-Type", "application/json");

    request.onreadystatechange = function () {
        if (request.readyState === 4) {
            if (request.status === 200) {
                console.log("Server response:", request.responseText);
                alert("Account was successfully deleted!");
                window.location.href = "/";
            } else if (request.status === 400) {
                console.log(request.responseText);
                if (isJSON(request.responseText)) {
                    let errors = JSON.parse(request.responseText);
                    if (errors && errors.errors) {
                        handleErrors(errors);
                    }
                } else {
                    console.error("Error:", request.status, request.statusText);
                    if (request.responseText === "Invalid password.") {
                        password2.style.borderColor = "red";
                        passwordError2.innerText = request.responseText;
                    } else {
                        alert("Login failed. Status code: " + request.status + ". Error message: " + request.responseText + ".");
                    }
                }
            } else {
                console.error("Error:", request.status, request.statusText);
                alert("Login failed. Status code: " + request.status + ". Error message: " + request.responseText + ".");
            }
        }
    };
    request.send(JSON.stringify(deleteData));
    return false;
}

function updateInfo() {
    const username = document.getElementById("accountEmail").textContent;

    const inputs = [
        { id: "name", label: "Name" },
        { id: "surname", label: "Surname" },
        { id: "phoneNumber", label: "Telephone", regex: /^\+[0-9]{1,12}$/ },
        { id: "dateOfBirth", label: "Date of birth", formatRegex: /^(0[1-9]|[12][0-9]|3[01])\.(0[1-9]|1[0-2])\.\d{4}$/ },
        { id: "city", label: "City"},
        { id: "street", label: "Street" },
        { id: "number", label: "Number" },
        { id: "zipcode", label: "ZIP Code", regex: /^\d{3} \d{2}$/ },
        { id: "country", label: "Country" },
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
        elements.push(element);

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
    }

    if (invalidInput) {
        return false;
    }

    const updateData = {
        email: username,
        name: elements[0].value,
        surname: elements[1].value,
        phoneNumber: elements[2].value,
        dateOfBirth: elements[3].value,
        city: elements[4].value,
        street: elements[5].value,
        number: elements[6].value,
        zipcode: elements[7].value,
        country: elements[8].value
    };

    const updateRequest = new XMLHttpRequest();
    updateRequest.open("PUT", "/api/user/update", true);
    updateRequest.setRequestHeader("Content-Type", "application/json");

    updateRequest.onreadystatechange = function () {
        if (updateRequest.readyState === 4) {
            if (updateRequest.status === 200) {
                console.log("Server response:", updateRequest.responseText);
                alert("Profile information successfully updated.");
            } else if (updateRequest.status === 400) {
                console.log(updateRequest.responseText);
                if (isJSON(updateRequest.responseText)) {
                    let errors = JSON.parse(updateRequest.responseText);
                    if (errors && errors.errors) {
                        handleErrors(errors);
                    }
                } else {
                    console.error("Error:", updateRequest.status, updateRequest.statusText);
                    if (updateRequest.responseText === "Invalid date format. Expected format: dd.MM.yyyy") {
                        document.getElementById("email-error").innerText = " " + updateRequest.responseText;
                        elements[5].style.borderColor = "red";
                    } else {
                        alert("Registration failed. Status code: " + updateRequest.status + ". Error message: " + updateRequest.responseText + ".");
                    }
                }
            } else {
                console.error("Error:", updateRequest.status, updateRequest.statusText);
                alert("Registration failed. Status code: " + updateRequest.status + ". Error message: " + updateRequest.responseText + ".");
            }
        }
    };

    updateRequest.send(JSON.stringify(updateData));
    return false;
}

function handleErrors(errors) {
    let errorList = errors.errors;
    for (let i = 0; i < errorList.length; i++) {
        let error = errorList[i];
        let fieldName = error.split(":")[0].trim();
        let errorMessage = error.split(":")[1].trim();
        let element = document.getElementById(fieldName + "-error");
        if (element) {
                element.innerText = " " + errorMessage;
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

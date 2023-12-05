function getUser() {
    var id = document.getElementById("email").value;

    var request = new XMLHttpRequest();
    request.open("GET", "/api/user/" + id, true);

    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200)
        {
            var user = JSON.parse(request.responseText);
            fillTable(user);
        }
    };
    request.send();

    return false;
}

function fillTable(user) {
    var tablebody = document.getElementById("userData");
    tablebody.innerHTML = "";

    var row = tablebody.insertRow();
    row.insertCell().textContent = user.name;
    row.insertCell().textContent = user.surname;
    row.insertCell().textContent = user.email;
    row.insertCell().textContent = user.password;
    row.insertCell().textContent = user.phoneNumber;
    row.insertCell().textContent = user.dateOfBirth;

}
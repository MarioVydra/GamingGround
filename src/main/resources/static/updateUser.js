function updateUser() {
    var email = document.getElementById("email3").value;
    var attribute = document.getElementById("attribute").value;
    var value = document.getElementById("value").value;

    var request = new XMLHttpRequest();

    var data = {
        email: email,
        attribute: attribute,
        value: value
    }

    request.open("PUT", "api/user/update/" + email, true);
    request.setRequestHeader("Content-Type", "application/json");

    request.onreadystatechange = function () {
        if (request.readyState === 4) {
            if (request.status === 200) {
                alert("User updated successfully!");
            } else {
                alert("Error updating user. Status code: " + request.status);
            }
        }
    };

    request.send(JSON.stringify(data));

    return false;
}
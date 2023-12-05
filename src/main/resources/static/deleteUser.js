function deleteUser()
{
    var email = document.getElementById("email2").value;

    var request = new XMLHttpRequest();

    request.open("DELETE", "api/user/delete/" + email, true);

    request.onreadystatechange = function () {
        if (request.readyState === 4) {
            if (request.status === 200) {
                alert("User deleted successfully!");
            } else {
                alert("Error deleting user. Status code: " + request.status);
            }
        }
    };

    request.send();

    return false;
}
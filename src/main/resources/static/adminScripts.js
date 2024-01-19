document.addEventListener("DOMContentLoaded", function() {
    fetch("api/admin/users")
        .then(response => response.json())
        .then(users => {
            users.forEach(user => {
                const table = document.getElementById("usersTable")
                let row = table.insertRow();
                row.insertCell(0).innerHTML = user.email;
                row.insertCell(1).innerHTML = user.name;
                row.insertCell(2).innerHTML = user.surname;
                let checkboxCell = row.insertCell(3);
                let checkboxElement = document.createElement("input");
                checkboxElement.type = "checkbox";
                checkboxElement.name = "userEmail";
                checkboxElement.value = user.email;
                checkboxCell.appendChild(checkboxElement);
            });
        })
        .catch(error => console.error("Error", error));
});

function deleteSelectedUsers() {
    let selectedEmails = Array.from(document.querySelectorAll('input[name="userEmail"]:checked'))
        .map(checkbox => checkbox.value);
    console.log(JSON.stringify({ userEmails: selectedEmails }));

    fetch("api/admin/delete", {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ userEmails: selectedEmails })
    })
        .then(response => {
            if (response.ok) {
                location.reload();
            } else {
                alert("Error: Could not delete users.");
            }
        })
        .catch(error => console.error("Error", error));
}
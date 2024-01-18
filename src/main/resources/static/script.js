/** NAVBAR **/
const hamburgerIcon = document.querySelector('.hamburger-image');
const menu = document.querySelector('.menu');
const closeIcon = document.querySelector('.close-image');
const body = document.body;

if (hamburgerIcon) {
    hamburgerIcon.addEventListener('click', function () {
        menu.classList.add('active');
    });
}

if (closeIcon) {
    closeIcon.addEventListener('click', function () {
        menu.classList.remove('active');
    });
}

body.addEventListener('click', function (e) {
    if (!menu.contains(e.target) && !hamburgerIcon.contains(e.target)) {
        menu.classList.remove('active');
    }
});


/** LOG OUT **/
function logoutUser() {
    const request = new XMLHttpRequest();
    request.open("POST", "/api/user/logout", true);
    request.onreadystatechange = function() {
        if (this.readyState === XMLHttpRequest.DONE && this.status === 200)
        {
            alert("Log out was successful! Redirecting...");
            window.location.href = '/';
        }
    };
    request.send();
}


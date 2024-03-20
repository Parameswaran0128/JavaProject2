var users = {
    "admin": {"password": "1234", "type": "admin"},
    "employee": {"password": "employeepassword", "type": "employee"}
};

function loginFunc(){
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    var selectedUserType = document.querySelector('input[name="userType"]:checked').value;


    if (username.trim() === "" || password.trim() === "") {
        document.getElementById("errorMessage").innerHTML = "Please enter both username and password.";
        return;
    }
    var found = false;
            for (var user in users) {
                if (users.hasOwnProperty(user)) {
                    if (user === username && users[user].password === password && users[user].type === selectedUserType) {
                        if (selectedUserType === "admin") {
                            window.location.href = "admin.html";
                        } else if (selectedUserType === "employee") {
                            window.location.href = "employee.html";
                        }
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                document.getElementById("errorMessage").innerHTML = "Invalid username or password. Please try again.";
            }
}

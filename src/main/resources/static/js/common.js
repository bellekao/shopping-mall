document.addEventListener("DOMContentLoaded", () => {
    const logoutBtn = document.getElementById("logoutBtn");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", logout);
    }
});

function logout() {
    logoutBtn.addEventListener("click", () => {
        fetch("/api/users/logout", {
            method: "POST",
            credentials: "include",
        })
            .then(res => res.json())
            .then(data => {
                if (data.status === "success") {
                    alert("登出成功！");
                    window.location.href = "/users/login";
                }
            })
            .catch(error => {
                console.error("登出錯誤：", error);
                alert("登出失敗！");
            });
    });
}

function handleLogin(event) {
    event.preventDefault();  // 阻止表單的默認提交

    var username = document.getElementById("email").value;
    var password = document.getElementById("password").value;

    var loginData = {
        "email": username,
        "password": password
    };

    // 使用 Fetch API 來發送 POST 請求
    fetch("/api/users/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        credentials: 'include',
        body: JSON.stringify(loginData)
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => {
                    throw new Error(err.message || "登入失敗，請檢查帳號或密碼")
                })
            }
            return response.json(); // 提取錯誤訊息
        })
        .then(user => {
            if (user.role === 1) {
                window.location.href = "/managers/book_manager"
            } else {
                window.location.href = "/index";
            }
        })
        .catch(error => {
            console.error("錯誤:", error);
            alert(error.message);
        });
}

function handleRegister(event) {
    event.preventDefault();

    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    var email = document.getElementById("email").value;

    var registerDate = {
        "username": username,
        "password": password,
        "email": email
    };

    fetch("/api/users/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(registerDate)
    })
        .then(response => {
            if (!response.ok) {
            return response.json().then(err => {
                throw new Error(err.message || "註冊失敗");
            });
        }
            return response.json();
        })
            .then(data => {
            window.location.href = "/index";
        })
            .catch(error => {
            alert(error.message);
        })
}
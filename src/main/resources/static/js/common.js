document.addEventListener("DOMContentLoaded", () => {
    // 取得登入使用者資訊
    fetch("/api/users/me", { credentials: "include" })
        .then(res => {
            if (!res.ok) throw new Error("尚未登入");
            return res.json();
        })
        .then(result => {
            const user = result.data;
            document.getElementById("userInfo").textContent = `歡迎，${user.userName}`;

            // 隱藏登入與註冊連結
            document.getElementById("loginLink").classList.add("d-none");
            document.getElementById("registerLink").classList.add("d-none");

            // 顯示登出按鈕
            document.getElementById("logoutWrapper").classList.remove("d-none");

            // 顯示一般使用者功能
            if (user.role === 0) {
                document.getElementById("cartLink").classList.remove("d-none");
                document.getElementById("orderLink").classList.remove("d-none");
            }

            // 顯示管理員功能
            if (user.role === 1) {
                document.getElementById("adminLink").classList.remove("d-none");
            }
        })
        .catch(() => {
            // 尚未登入時：顯示登入與註冊，隱藏登出按鈕
            document.getElementById("loginLink").classList.remove("d-none");
            document.getElementById("registerLink").classList.remove("d-none");
            document.getElementById("logoutWrapper").classList.add("d-none");
        });

    // 登出按鈕事件
    const logoutBtn = document.getElementById("logoutBtn");
    if (logoutBtn) {
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
});


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
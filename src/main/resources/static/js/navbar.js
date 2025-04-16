document.addEventListener("DOMContentLoaded", () => {
    const logoutForm = document.getElementById("logoutForm");
    if (logoutForm) {
        logoutForm.addEventListener("submit", function (event) {
            event.preventDefault(); // 防止預設送出
            fetch(this.action, {
                method: "POST",
                credentials: "include"
            })
                .then(res => res.json())
                .then(data => {
                    if (data.status === "success") {
                        alert("登出成功！");
                        window.location.href = "/users/login";
                    } else {
                        alert("登出失敗！");
                    }
                })
                .catch(err => {
                    console.error("登出錯誤：", err);
                    alert("登出失敗！");
                });
        });
    }
});

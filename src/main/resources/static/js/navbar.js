document.addEventListener("DOMContentLoaded", () => {
    const logoutBtn = document.getElementById("logoutBtn");

    if (logoutBtn) {
        logoutBtn.addEventListener("click", () => {
            fetch("/api/users/logout", {
                method: "POST",
                credentials: "include",
            })
                .then(res => {
                    if (!res.ok) throw new Error("登出失敗");
                    return res.json();
                })
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

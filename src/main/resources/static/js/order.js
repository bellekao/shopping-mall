console.log("order.js loaded");

document.addEventListener("DOMContentLoaded", () => {

    fetch("/api/orders")
        .then((res) => res.json())
        .then((result) => {
            const orders = result.data;
            const tbody = document.getElementById("order-details-body");

            orders.forEach((order) => {
                const tr = document.createElement("tr");

                tr.innerHTML = `
                    <td>${order.orderId}</td>
                    <td>${order.createdDate}</td>
                    <td>$${order.totalAmount}</td>
                    <td>
                        <button class="btn btn-sm btn-primary detail-btn" data-id="${order.orderId}">訂單資訊</button>
                    </td>
                `;

                tbody.appendChild(tr);
            })

            tbody.addEventListener("click", (e) => {
                if (e.target.classList.contains("detail-btn")) {
                    const orderId = e.target.dataset.id;
                    console.log("orderId:", orderId);

                    window.location.href = "/orders/" + orderId;
                }
            })
        })
        .catch((err) => {
            console.error("取得訂單資料失敗", err);
        });
});
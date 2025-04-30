document.addEventListener("DOMContentLoaded", () => {
    const cartDetailsBody = document.getElementById("cart-details-body");
    const checkoutBtn = document.getElementById("checkout-btn");
    const clearCartBtn = document.getElementById("clear-cart-btn");

    loadCart(); // 初始化載入

    function loadCart() {
        fetch('/api/carts', {
            method: 'GET',
            credentials: 'include'
        })
            .then(response => {
                if (!response.ok) throw new Error("無法載入購物車");
                return response.json();
            })
            .then(data => {
                const cartItems = data?.data || [];
                if (cartItems.length > 0) {
                    renderCartItems(cartItems);
                } else {
                    cartDetailsBody.innerHTML = '<tr><td colspan="7">您的購物車是空的</td></tr>';
                    updateCartTotal(); // 清空時也要更新總金額
                }
            })
            .catch(err => {
                console.error('載入購物車錯誤:', err);
                alert('無法載入購物車資料');
            });
    }

    function renderCartItems(cartItems) {
        cartDetailsBody.innerHTML = "";

        cartItems.forEach(item => {
            const { cartItemId, book, quantity } = item;
            const row = document.createElement('tr');
            row.innerHTML = `
                <td><img src="${book.imageUrl}" alt="商品圖示" class="img-fluid" style="width: 60px;"></td>
                <td>${book.title}</td>
                <td>${book.author}</td>
                <td>$${book.price}</td>
                <td class="text-center">
                    <input type="number" class="form-control form-control-sm quantity-input mx-auto d-block"
                           style="width: 70px; text-align: center;"
                           value="${quantity}" min="1" max="${book.stock}"
                           data-item-id="${cartItemId}" data-max="${book.stock}">
                </td>
                <td>$${(book.price * quantity).toFixed(2)}</td>
                <td>
                    <button class="btn btn-danger btn-sm delete-btn" data-item-id="${cartItemId}">刪除</button>
                </td>
            `;
            cartDetailsBody.appendChild(row);
        });

        bindQuantityInputEvents();
        bindDeleteButtons();
        updateCartTotal(); // 渲染完畢就更新總金額
    }

    function bindQuantityInputEvents() {
        const inputs = cartDetailsBody.querySelectorAll('.quantity-input');

        inputs.forEach(input => {
            let oldValue = parseInt(input.value); // 記錄原始值

            input.addEventListener('focus', () => {
                oldValue = parseInt(input.value); // 每次 focus 時重設舊值
            });

            input.addEventListener('change', () => {
                const itemId = input.dataset.itemId;
                let newQuantity = parseInt(input.value);
                const maxStock = parseInt(input.dataset.max);
                const price = parseFloat(input.closest('tr').querySelector('td:nth-child(4)').innerText.replace('$', ''));

                if (isNaN(newQuantity) || newQuantity < 1) {
                    newQuantity = 1;
                    input.value = newQuantity;
                }

                if (newQuantity > maxStock) {
                    newQuantity = maxStock;
                    input.value = newQuantity;
                }

                // 更新畫面上的金額
                const totalCell = input.closest('tr').querySelector('td:nth-child(6)');
                totalCell.innerText = `$${(price * newQuantity).toFixed(2)}`;

                // 傳送更新
                fetch(`/api/carts/${itemId}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    credentials: 'include',
                    body: JSON.stringify({ quantity: newQuantity })
                })
                    .then(response => {
                        if (!response.ok) {
                            return response.json().then(data => {
                                throw new Error(data.message || '更新失敗');
                            });
                        }
                        return response.json();
                    })
                    .then(data => {
                        console.log("更新成功", data);
                        updateCartTotal();
                    })
                    .catch(err => {
                        // 回復原本的數量與金額
                        alert("更新失敗：" + err.message);
                        input.value = oldValue;
                        totalCell.innerText = `$${(price * oldValue).toFixed(2)}`;
                        updateCartTotal();
                    });
            });
        });
    }

    function bindDeleteButtons() {
        const deleteBtns = cartDetailsBody.querySelectorAll('.delete-btn');
        deleteBtns.forEach(btn => {
            btn.addEventListener('click', () => {
                const itemId = btn.dataset.itemId;
                removeCartItem(itemId);
            });
        });
    }

    function updateCartItemQuantity(itemId, quantity) {
        fetch(`/api/carts/${itemId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include',
            body: JSON.stringify({ quantity })
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(data => {
                        throw new Error(data.message || '更新失敗');
                    });
                }
                return response.json();
            })
            .then(data => {
                console.log("更新成功", data);
            })
            .catch(err => {
                console.error('更新數量失敗:', err);
                alert('更新數量失敗：' + err.message);
            });
    }

    function updateCartTotal() {
        const totalCell = document.getElementById("cart-total");
        let total = 0;

        const rows = cartDetailsBody.querySelectorAll("tr");
        rows.forEach(row => {
            const totalText = row.querySelector("td:nth-child(6)")?.innerText;
            if (totalText) {
                const value = parseFloat(totalText.replace('$', ''));
                if (!isNaN(value)) total += value;
            }
        });

        totalCell.innerText = `$${total.toFixed(2)}`;
    }

    function removeCartItem(itemId) {
        if (!confirm("確定要刪除此商品嗎？")) return;

        fetch(`/api/carts/${itemId}`, {
            method: 'DELETE',
            credentials: 'include'
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(data => {
                        throw new Error(data.message || '刪除失敗');
                    });
                }
                loadCart(); // 自動觸發 updateCartTotal
            })
            .catch(err => {
                console.error('刪除商品失敗:', err);
                alert('刪除商品失敗：' + err.message);
            });
    }

    clearCartBtn.addEventListener('click', () => {
        fetch('/api/carts', {
            method: 'DELETE',
            credentials: 'include'
        })
            .then(response => {
                if (response.ok) {
                    alert("購物車已清空！");
                    loadCart(); // 自動更新總金額
                } else {
                    return response.json().then(data => {
                        alert(`清空失敗：${data.message}`);
                    });
                }
            })
            .catch(err => {
                console.error('清空購物車失敗:', err);
                alert('清空購物車失敗：' + err.message);
            });
    });

    checkoutBtn.addEventListener('click', () => {
        alert("結帳功能尚未實現");
    });
});

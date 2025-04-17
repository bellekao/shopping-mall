console.log("admin_books.js loaded");

document.addEventListener("DOMContentLoaded", () => {

    console.log("admin_books.js loaded");

    // 綁定新增書籍按鈕事件
    const addBtn = document.getElementById("add-book-btn");
    if (addBtn) {
        addBtn.addEventListener("click", () => {
            // alert("新增書籍按鈕被點擊了！");
            window.location.href = "/managers/book_edit";
        });
    }

    fetch("/api/books")
        .then((res) => res.json())
        .then((result) => {
            const books = result.data;
            const tbody = document.getElementById("book-details-body");

            books.forEach((book) => {
                const tr = document.createElement("tr");

                tr.innerHTML = `
                    <td>${book.bookId}</td>
                    <td><img src="${book.imageUrl}" alt="封面" width="60"></td>
                    <td>${book.title}</td>
                    <td>${book.author}</td>
                    <td>${book.category}</td>
                    <td>${book.price}</td>
                    <td>${book.stock}</td>
                    <td>${book.sale}</td>
                    <td>
                        <button class="btn btn-sm btn-primary me-1 edit-btn" data-id="${book.bookId}">修改</button>
                         <button class="btn btn-sm btn-danger delete-btn" data-id="${book.bookId}">刪除</button>
                    </td>
                `;

                tbody.appendChild(tr);
            });

            tbody.addEventListener("click", (e) => {
                if (e.target.classList.contains("edit-btn")) {
                    const bookId = e.target.dataset.id;
                    console.log("要修改的書籍 ID:", bookId);
                    // 開啟編輯 modal 或導向編輯頁
                    window.location.href = `/managers/book_edit?bookId=${bookId}`;
                }

                if (e.target.classList.contains("delete-btn")) {
                    const bookId = e.target.dataset.id;
                    console.log("要刪除的書籍 ID:", bookId);

                    // 觸發刪除確認與 API 呼叫
                    if (confirm("確定要刪除這本書籍嗎？")) {
                        // 呼叫刪除 API
                        fetch(`/api/books/${bookId}`, {
                            method: "DELETE",
                            credentials: "include",  // 登錄狀態
                        })
                            .then((res) => {
                                if (!res.ok) throw new Error("刪除失敗");
                                alert("書籍已成功刪除！");
                                // 刪除後，重新載入書籍列表
                                location.reload(); // 重新載入頁面
                            })
                            .catch((err) => {
                                console.error("刪除錯誤：", err);
                                alert("刪除失敗！");
                            });
                    }
                }
            });

        })
        .catch((err) => {
            console.error("取得書籍資料失敗", err);
        });
});

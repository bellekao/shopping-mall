document.addEventListener("DOMContentLoaded", () => {
    const imageUrlInput = document.getElementById("imageUrl");
    const imagePreview = document.getElementById("image-preview");
    const form = document.getElementById("book-form");

    // 圖片網址變動時更新預覽
    if (imageUrlInput && imagePreview) {
        imageUrlInput.addEventListener("input", () => {
            const url = imageUrlInput.value.trim();
            imagePreview.src = url || "/images/default.jpg"; // 預設圖片
        });
    }

    // 表單送出驗證
    if (form) {
        form.addEventListener("submit", (e) => {
            let hasError = false;

            // 欄位清單
            const fields = ["title", "author", "category", "imageUrl", "price", "stock"];

            fields.forEach((field) => {
                const input = document.getElementById(field);
                const errorSpan = document.getElementById(`${field}-error`);

                if (input && errorSpan) {
                    if (!input.value.trim()) {
                        errorSpan.textContent = "此欄位不可為空";
                        hasError = true;
                    } else {
                        errorSpan.textContent = "";
                    }
                }
            });

            if (hasError) {
                e.preventDefault(); // 阻止送出
            }
        });
    }
});

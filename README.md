# Book Mall

## 頁面呈現

### 首頁
![index](https://github.com/user-attachments/assets/48fc0eac-5e06-4780-9f80-4707901227c7)

### 登入
![login](https://github.com/user-attachments/assets/471c4efd-70e0-44f3-bb91-f74a08057cbb)

### 註冊
![register](https://github.com/user-attachments/assets/a05cc0ea-b648-4579-a09d-46443e18c665)

### 會員購物車頁面
![cart](https://github.com/user-attachments/assets/d6193aa2-4ee2-47b7-867a-df3556a3fc7d)

### 後台管理
![manager](https://github.com/user-attachments/assets/5985a9fd-7137-4d89-a7b2-072a04091a06)

### 訂單頁面
![orders](https://github.com/user-attachments/assets/77abfcf4-0ff2-40ac-94aa-5bfccfd2e064)

### 訂單資訊頁面
![orderDetail](https://github.com/user-attachments/assets/12d2de4a-01c3-49df-9002-dc6854d6118d)


## 需求分析
1. 買家在輸入用戶名、電子郵件、密碼後可註冊會員，電子郵件不可重複。
2. 註冊成功後可輸入電子郵件、密碼登入會員。
3. 每件商品(書籍)都有自己的編號、名稱、作者、分類、圖片、價格、庫存、銷量。
4. 購物車內有自己的編號、商品編號、總金額，並記錄商品的書名、作者、圖片、價格。
5. 購物車內可刪除特定商品、調整購買數量。
6. 訂單內會有訂單編號、訂單成立時間、消費金額，以及該筆訂單對應的訂單明細。
7. 買家在登入後可將商品加入購物車內，在購物車頁面內點選結帳後成為訂單。

### 系統功能分析
顧客可以在網站首頁上瀏覽所有的商品，點選商品下的查看詳情可以看到該商品的詳細資訊，並且若在登入的情況下可以將該商品加入購物車內，若未登入則會跳轉至登入頁面。
顧客登入後可以在購物車內調整要購買的商品數量、刪除特定商品、刪除全部商品以及結帳。在購物車內點選結帳後，這些商品會被列入訂單中。

根據上述說明，該專案會有以下功能模組：

| 功能模組 | 說明 |
| -------- | -------- |
| 商品功能  | 管理員可新增、編輯、刪除，一般使用者可瀏覽商品列表 |
| 帳號功能  | 使用者可註冊帳號並登入，登入後可使用購物車與訂單功能 |
| 購物車功能| 登入後的使用者可將商品加入購物車、修改數量、刪除項目或清空購物車 |
| 訂單功能  | 使用者可根據購物車內容創建訂單，並可查詢歷史訂單列表 |

使用到的工具如下：

| 功能需求 | 使用工具 |
| -------- | -------- |
| 網頁前端 | HTML, CSS, JavaScript, Bootstrap |
| 網頁後端 | Spring Boot, Spring MVC, Spring JDBC, RESTful API |
| 資料庫 | MySQL     |
| 版本控制 | GitHub |



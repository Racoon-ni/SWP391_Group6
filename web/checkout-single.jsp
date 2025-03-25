<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entity.Book, entity.Account" %>
<%
    Book selectedBook = (Book) session.getAttribute("selectedBook");
    String selectedFormat = (String) session.getAttribute("selectedFormat");
    Integer selectedQuantity = (Integer) session.getAttribute("selectedQuantity");
    Account user = (Account) session.getAttribute("account");

    if (user == null || selectedBook == null || selectedFormat == null || selectedQuantity == null) {
        response.sendRedirect("home.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Xác nhận thanh toán</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/button.css">
    <style>
        body {
            background-color: #f8f9fa;
            font-family: 'Segoe UI', sans-serif;
        }
        .card {
            border: none;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
        .img-preview {
            max-height: 250px;
            object-fit: contain;
        }
        .btn-submit {
            background-color: #28a745;
            color: white;
        }
        .btn-submit:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
<jsp:include page="Menu.jsp"/>

<div class="container my-5">
    <h2 class="text-center mb-4 fw-bold">🧾 Xác Nhận Đơn Hàng</h2>

    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card p-4">
                <div class="row g-3 align-items-center">
                    <div class="col-md-4 text-center">
                        <img src="<%= selectedBook.getCoverImage() %>" class="img-fluid img-preview rounded" alt="Book Cover">
                    </div>
                    <div class="col-md-8">
                        <h4 class="fw-bold"><%= selectedBook.getTitle() %></h4>
                        <p><strong>Tác giả:</strong> <%= selectedBook.getAuthorName() %></p>
                        <p><strong>Hình thức:</strong> <%= selectedFormat %></p>
                        <p><strong>Số lượng:</strong> <%= selectedQuantity %></p>
                        <p><strong>Đơn giá:</strong> $<%= selectedBook.getPrice() %></p>
                        <h5 class="text-success fw-bold">Tổng tiền: $<%= selectedBook.getPrice() * selectedQuantity %></h5>
                    </div>
                </div>

                <hr class="my-4">

                <form action="place-order" method="POST">
                    <input type="hidden" name="book_id" value="<%= selectedBook.getBook_id() %>">
                    <input type="hidden" name="quantity" value="<%= selectedQuantity %>">
                    <input type="hidden" name="book_format" value="<%= selectedFormat %>">

                    <div class="mb-3">
                        <label for="shipping_address" class="form-label">📦 Địa chỉ giao hàng:</label>
                        <input type="text" class="form-control" name="shipping_address" id="shipping_address" required>
                    </div>

                    <div class="mb-3">
                        <label for="payment_method" class="form-label">💳 Phương thức thanh toán:</label>
                        <select name="payment_method" id="payment_method" class="form-select" required>
                            <option value="">-- Chọn phương thức --</option>
                            <option value="Cash on Delivery">Tiền mặt khi nhận hàng</option>
                            <option value="Credit Card">Thẻ tín dụng</option>
                            <option value="Bank Transfer">Chuyển khoản ngân hàng</option>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-submit w-100 btn-lg">
                        ✅ Xác nhận thanh toán
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="Footer.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

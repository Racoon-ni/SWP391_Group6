<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entity.Order, entity.OrderItem" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết đơn hàng</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f4f6f9;
            padding: 40px;
        }

        .container {
            background: #fff;
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        h2 {
            color: #333;
            font-weight: bold;
            margin-bottom: 30px;
        }

        .table th {
            background-color: #343a40;
            color: #fff;
        }

        .btn-back {
            margin-top: 20px;
            background-color: #6c757d;
            border: none;
        }

        .btn-back:hover {
            background-color: #5a6268;
        }

        .info-box {
            margin-bottom: 20px;
        }

        .info-box strong {
            color: #555;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>📖 Chi tiết đơn hàng</h2>

    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>Mã sách</th>
            <th>Hình ảnh</th>
            <th>Tên sách</th>
            <th>Định dạng</th>
            <th>Giá</th>
            <th>Số lượng</th>
            <th>Tổng</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${orderItems}">
            <tr>
                <td>${item.bookId}</td>
                <td><img src="${item.coverImage}" alt="cover" width="50" height="70" style="object-fit:cover;"></td>
                <td>${item.bookTitle}</td>
                <td>${item.bookFormat}</td>
                <td>$${item.price}</td>
                <td>${item.quantity}</td>
                <td>$${item.price * item.quantity}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="text-end mt-3">
        <a href="orders" class="btn btn-back">
            ⬅️ Quay lại danh sách đơn hàng
        </a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

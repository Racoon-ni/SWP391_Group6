<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.OrderItem" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết đơn hàng</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f9f9f9;
            padding: 30px;
        }

        h2 {
            text-align: center;
            color: #333;
        }

        table {
            width: 90%;
            margin: auto;
            border-collapse: collapse;
            background-color: white;
            box-shadow: 0 0 10px #ccc;
        }

        th, td {
            padding: 12px 15px;
            text-align: center;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #f3f3f3;
        }

        img {
            width: 80px;
            height: auto;
        }

        .btn {
            display: block;
            width: fit-content;
            margin: 30px auto 0;
            background-color: #444;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 4px;
            text-align: center;
        }

        .btn:hover {
            background-color: #333;
        }
    </style>
</head>
<body>

<h2>📖 Chi tiết đơn hàng</h2>

<%
    List<OrderItem> orderItems = (List<OrderItem>) request.getAttribute("orderItems");
    double total = 0;
%>

<table>
    <thead>
        <tr>
            <th>Ảnh bìa</th>
            <th>Tên sách</th>
            <th>Định dạng</th>
            <th>Giá</th>
            <th>Số lượng</th>
            <th>Tổng</th>
        </tr>
    </thead>
    <tbody>
    <%
        for (OrderItem item : orderItems) {
            double itemTotal = item.getPrice() * item.getQuantity();
            total += itemTotal;
    %>
        <tr>
            <td><img src="<%= item.getCoverImage() %>" alt="Ảnh bìa"></td>
            <td><%= item.getBookTitle() %></td>
            <td><%= item.getBookFormat() %></td>
            <td><%= item.getPrice() %> $</td>
            <td><%= item.getQuantity() %></td>
            <td><%= itemTotal %> $</td>
        </tr>
    <%
        }
    %>
        <tr>
            <td colspan="5" style="text-align:right; font-weight: bold;">Tổng cộng:</td>
            <td style="font-weight: bold;"><%= total %> $</td>
        </tr>
    </tbody>
</table>

<a href="orders" class="btn">← Quay lại danh sách đơn hàng</a>

</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="entity.CartItem" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Giỏ hàng - Book Store</title>

        <!-- Link CSS -->
        <link rel="stylesheet" href="styles.css">
        <style>
            body {
                font-family: 'Times New Roman', serif;
                background-color: #f8f4e5;
                color: #5a4633;
                margin: 0;
                padding: 0;
            }
            .container {
                width: 80%;
                margin: auto;
                text-align: center;
            }
            h2 {
                font-size: 30px;
                margin: 20px 0;
                color: #2c2c2c;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                background: #fff;
                border-radius: 10px;
                overflow: hidden;
            }
            th, td {
                padding: 15px;
                border-bottom: 1px solid #ddd;
                text-align: center;
            }
            th {
                background: #dfd3c3;
                font-size: 18px;
            }
            img {
                width: 80px;
                height: 100px;
                border-radius: 5px;
            }
            .btn {
                display: inline-block;
                padding: 8px 15px;
                border-radius: 5px;
                text-decoration: none;
                font-size: 14px;
                cursor: pointer;
            }
            .btn-update {
                background: #af8260;
                color: white;
                border: none;
            }
            .btn-delete {
                background: #c0392b;
                color: white;
                border: none;
            }
            .btn-clear {
                background: #4a4a4a;
                color: white;
                border: none;
            }
            .btn:hover {
                opacity: 0.8;
            }
        </style>
    </head>
    <body>

        <div class="container">
            <h2>🛒 Giỏ hàng của bạn</h2>

            <table>
                <tr>
                    <th></th>
                    <th>Tên sách</th>
                    <th>Định dạng</th>
                    <th>Giá</th>
                    <th>Số lượng</th>
                    <th>Tổng tiền</th>
                    <th></th>
                </tr>

                <%
                    List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems");
                    if (cartItems == null || cartItems.isEmpty()) {
                %>
                <tr><td colspan="7">📭 Giỏ hàng của bạn đang trống!</td></tr>
                <% } else {
                    double totalPrice = 0;
                    for (CartItem item : cartItems) {
                        double itemTotal = item.getQuantity() * item.getPrice();
                        totalPrice += itemTotal;
                %>
                <tr>
                    <td><img src="<%= item.getCover_image() %>" alt="Book Cover"></td>
                    <td><%= item.getBookTitle() %></td>
                    <td><%= item.getBook_format() %></td>
                    <td><%= item.getPrice() %> $</td>
                    <td>
                        <!-- Form cập nhật số lượng -->
                        <form action="cart" method="post">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="book_id" value="<%= item.getBook_id() %>">
                            <input type="number" name="quantity" value="<%= item.getQuantity() %>" min="1">
                            <input type="submit" class="btn btn-update" value="✔ Cập nhật">
                        </form>
                    </td>
                    <td><%= itemTotal %> $</td>
                    <td>
                        <!-- Form xóa sản phẩm -->
                        <form action="cart" method="post">
                            <input type="hidden" name="action" value="remove">
                            <input type="hidden" name="book_id" value="<%= item.getBook_id() %>">
                            <input type="submit" class="btn btn-delete" value="❌ Xóa">
                        </form>
                    </td>
                </tr>
                <% } %>
                <tr>
                    <td colspan="5"><strong>Tổng cộng:</strong></td>
                    <td><strong><%= totalPrice %> $</strong></td>
                    <td></td>
                </tr>
                <% } %>
            </table>

            <br>
            <!-- Form xóa toàn bộ giỏ hàng -->
            <form action="cart" method="post">
                <input type="hidden" name="action" value="clear">
                <input type="submit" class="btn btn-clear" value="🗑 Xóa toàn bộ giỏ hàng">
            </form>

            <br>
            <a href="home" class="btn btn-update">🏠 Tiếp tục mua sắm</a>
            <form action="cart" method="POST">
                <input type="hidden" name="action" value="checkout">
                <button type="submit" class="btn btn-update" style="margin-top: 10px; margin-bottom: 10px">💳 Thanh toán</button>
            </form>




        </div>

    </body>
</html>

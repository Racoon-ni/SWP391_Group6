<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="entity.CartItem" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Xác nhận thanh toán</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f7f7f7;
                padding: 30px;
            }

            .container {
                width: 80%;
                margin: auto;
                background: white;
                padding: 25px;
                border-radius: 8px;
                box-shadow: 0 0 10px #ccc;
            }

            h2 {
                text-align: center;
                color: #333;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 25px;
            }

            th, td {
                padding: 12px;
                text-align: center;
                border-bottom: 1px solid #ddd;
            }

            th {
                background-color: #efefef;
            }

            .form-section {
                margin-top: 25px;
            }

            .form-group {
                margin-bottom: 15px;
            }

            label {
                font-weight: bold;
            }

            input[type="text"], select {
                width: 100%;
                padding: 8px;
                margin-top: 5px;
                box-sizing: border-box;
            }

            .btn {
                background-color: #28a745;
                color: white;
                padding: 10px 20px;
                font-size: 16px;
                border: none;
                border-radius: 4px;
                margin-top: 20px;
                cursor: pointer;
            }

            .btn:hover {
                background-color: #218838;
            }

        </style>
    </head>
    <body>

        <div class="container">
            <h2>💳 Xác nhận thanh toán</h2>

            <form method="post" action="place-order">
                <table>
                    <thead>
                        <tr>
                            <th>Tên sách</th>
                            <th>Định dạng</th>
                            <th>Giá</th>
                            <th>Số lượng</th>
                            <th>Tổng</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
                            double totalPrice = 0;

                            for (CartItem item : cartItems) {
                                double itemTotal = item.getPrice() * item.getQuantity();
                                totalPrice += itemTotal;
                        %>
                        <tr>
                            <td><%= item.getBookTitle() %></td>
                            <td><%= item.getBook_format() %></td>
                            <td><%= item.getPrice() %> $</td>
                            <td><%= item.getQuantity() %></td>
                            <td><%= itemTotal %> $</td>
                        </tr>
                        <% } %>
                        <tr>
                            <td colspan="4" style="text-align:right"><strong>Tổng cộng:</strong></td>
                            <td><strong><%= totalPrice %> $</strong></td>
                        </tr>
                    </tbody>
                </table>

                <div class="form-section">
                    <div class="form-group">
                        <label for="shipping_address">📦 Địa chỉ giao hàng:</label>
                        <input type="text" name="shipping_address" id="shipping_address" required>
                    </div>

                    <div class="form-group">
                        <label for="payment_method">💰 Phương thức thanh toán:</label>
                        <select name="payment_method" id="payment_method" class="form-select" required>
                            <option value="">-- Chọn phương thức --</option>
                            <option value="Cash on Delivery">Thanh toán khi nhận hàng</option>
                            <option value="Credit Card">Thẻ tín dụng</option>
                            <option value="Bank Transfer">Chuyển khoản ngân hàng</option>
                            <option value="PayPal">PayPal</option>
                        </select>
                    </div>


                    <button type="submit" class="btn">✅ Xác nhận đặt hàng</button>
                </div>
            </form>
        </div>

    </body>
</html>

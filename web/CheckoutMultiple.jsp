<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="entity.CartItem" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Xác nhận thanh toán</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body style="background-color: #f8f9fa;">


<div class="container my-5">
    <div class="card shadow-lg border-0">
        <div class="card-body px-5 py-4">
            <h2 class="text-center fw-bold mb-4">💳 Xác nhận thanh toán</h2>

            <form method="post" action="place-order">
                <div class="table-responsive mb-4">
                    <table class="table table-bordered text-center align-middle bg-white">
                        <thead class="table-light">
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
                            <td>$<%= item.getPrice() %></td>
                            <td><%= item.getQuantity() %></td>
                            <td class="text-success fw-bold">$<%= itemTotal %></td>
                        </tr>
                        <% } %>
                        <tr class="fw-bold bg-light">
                            <td colspan="4" class="text-end">Tổng cộng:</td>
                            <td class="text-success">$<%= totalPrice %></td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <!-- Thông tin đặt hàng -->
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="shipping_address" class="form-label">📦 Địa chỉ giao hàng:</label>
                        <input type="text" class="form-control" name="shipping_address" id="shipping_address" required>
                    </div>

                    <div class="col-md-6 mb-3">
                        <label for="payment_method" class="form-label">💰 Phương thức thanh toán:</label>
                        <select name="payment_method" id="payment_method" class="form-select" required>
                            <option value="">-- Chọn phương thức --</option>
                            <option value="Cash on Delivery">Thanh toán khi nhận hàng</option>
                            <option value="Credit Card">Thẻ tín dụng</option>
                            <option value="Bank Transfer">Chuyển khoản ngân hàng</option>
                            <option value="PayPal">PayPal</option>
                        </select>
                    </div>
                </div>

                <div class="d-flex justify-content-between align-items-center mt-4">
                    <a href="Cart.jsp" class="btn btn-outline-dark">
                        <i class="fas fa-arrow-left"></i> Quay lại Giỏ hàng
                    </a>
                    <button type="submit" class="btn btn-success btn-lg">
                        ✅ Xác nhận đặt hàng
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

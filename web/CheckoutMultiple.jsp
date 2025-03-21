<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="entity.Account, entity.CartItem, java.util.List" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>

<%
    HttpSession sessionObj = request.getSession();
    Account user = (Account) sessionObj.getAttribute("account");
    List<CartItem> cartItems = (List<CartItem>) sessionObj.getAttribute("cartItems");

    System.out.println("Checkout - User: " + (user == null ? "null" : user.getAccount_id()));
    System.out.println("Checkout - Cart Items: " + (cartItems == null ? "null" : cartItems.size()));

    if (user == null || cartItems == null || cartItems.isEmpty()) {
        System.out.println("Chuyển hướng về home.jsp do giỏ hàng trống");
        response.sendRedirect("home");
        return;
    }
%>


<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Thanh Toán Giỏ Hàng</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/button.css">
    <link rel="stylesheet" href="css/checkout.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <jsp:include page="Menu.jsp"/>

    <div class="container my-5">
        <h2 class="text-center fw-bold mb-4">Xác Nhận Đơn Hàng</h2>

        <div class="row justify-content-center">
            <div class="col-md-8">
                <!-- Hiển thị danh sách sách trong giỏ hàng -->
                <div class="card shadow-lg border-0 rounded-lg p-4">
                    <h4 class="fw-bold mb-3"><i class="fas fa-shopping-cart"></i> Danh sách sách trong giỏ hàng</h4>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Ảnh</th>
                                <th>Tên Sách</th>
                                <th>Hình Thức</th>
                                <th>Số Lượng</th>
                                <th>Giá</th>
                                <th>Tổng</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% double totalAmount = 0; %>
                            <% for (CartItem item : cartItems) { %>
                            <tr>
                                <td><img src="<%= item.getCover_image() %>" width="50" class="rounded"></td>
                                <td><%= item.getBookTitle() %></td>
                                <td><%= item.getBook_format() %></td>
                                <td><%= item.getQuantity() %></td>
                                <td>$<%= item.getPrice() %></td>
                                <td>$<%= item.getPrice() * item.getQuantity() %></td>
                            </tr>
                            <% totalAmount += item.getPrice() * item.getQuantity(); %>
                            <% } %>
                        </tbody>
                        <tfoot>
                            <tr>
                                <td colspan="5" class="text-end fw-bold">Tổng tiền:</td>
                                <td class="text-success fw-bold">$<%= totalAmount %></td>
                            </tr>
                        </tfoot>
                    </table>
                </div>


                <!-- Nút thanh toán -->
                <div class="text-center mt-4">
                    <form action="ProcessPaymentServlet" method="POST">
                        <button type="submit" class="btn btn-success btn-lg w-100 shadow-sm">
                            <i class="fas fa-credit-card"></i> Thanh Toán Ngay
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

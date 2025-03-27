<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="entity.Order" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Đơn hàng của tôi</title>
        <style>
            body {
                font-family: 'Segoe UI', sans-serif;
                background-color: #f9f9f9;
                margin: 0;
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

            tr:hover {
                background-color: #f1f1f1;
            }

            .btn {
                padding: 6px 12px;
                background-color: #007bff;
                color: white;
                border: none;
                text-decoration: none;
                border-radius: 4px;
                cursor: pointer;
            }

            .btn:hover {
                background-color: #0056b3;
            }

            .status {
                font-weight: bold;
            }

        </style>
    </head>
    <body>
        <h2>📦 Đơn hàng đã đặt</h2>

        <%
            List<Order> orders = (List<Order>) request.getAttribute("orders");
            if (orders == null || orders.isEmpty()) {
        %>
        <p style="text-align:center;">❌ Bạn chưa đặt đơn hàng nào.</p>
        <%
            } else {
        %>
        <table>
            <thead>
                <tr>
                    <th>Mã đơn hàng</th>
                    <th>Ngày đặt</th>
                    <th>Trạng thái</th>
                    <th>Địa chỉ giao</th>
                    <th>Chi tiết</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (Order o : orders) {
                %>
                <tr>
                    <td><%= o.getOrderId() %></td>
                    <td><%= o.getOrderDate() %></td>
                    <td class="status"><%= o.getStatus() %></td>
                    <td><%= o.getShippingAddress() %></td>
                    <td>
                        <form action="order-detail" method="get">
                            <input type="hidden" name="order_id" value="<%= o.getOrderId() %>">
                            <button class="btn">🔍 Xem</button>
                        </form>

                        <% if ("Pending".equals(o.getStatus()) || "Processing".equals(o.getStatus())) { %>
                        <form action="cancel-order" method="post"
                              onsubmit="return confirm('Bạn có chắc chắn muốn hủy đơn hàng này không?');">
                            <input type="hidden" name="order_id" value="<%= o.getOrderId() %>">
                            <button type="submit" class="btn btn-danger btn-sm">❌ Hủy</button>
                        </form>
                        <% } %>
                    </td>

                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
        <%
            }
        %>
        <a href="Cart.jsp" class="btn btn-outline-dark mb-2">
            <i class="fas fa-shopping-cart"></i> Quay lại Giỏ hàng
        </a>
    </body>
</html>

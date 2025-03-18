<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="entity.CartItem, java.util.List" %>
<%
    List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems");

    // Kiểm tra nếu giỏ hàng rỗng
    if (cartItems == null || cartItems.isEmpty()) {
%>
    <p>Giỏ hàng của bạn đang trống.</p>
<%
    } else {
%>

<h2>Giỏ Hàng</h2>

<form action="ClearCartServlet" method="POST">
    <button type="submit">Xóa Toàn Bộ Giỏ Hàng</button>
</form>

<table>
    <tr>
        <th>Tên Sách</th>
        <th>Số Lượng</th>
        <th>Loại</th>
        <th>Hành Động</th>
    </tr>
    <% for (CartItem item : cartItems) { %>
    <tr>
        <td><%= item.getBook_id() %></td>
        <td><%= item.getQuantity() %></td>
        <td><%= item.getBook_format() %></td>
        <td>
            <form action="RemoveCartItemServlet" method="POST">
                <input type="hidden" name="cart_item_id" value="<%= item.getCart_item_id() %>">
                <button type="submit">Xóa</button>
            </form>
        </td>
    </tr>
    <% } %>
</table>

<form action="CheckoutServlet" method="POST">
    <input type="hidden" name="total_price" value="100.0"> <!-- Giá sẽ được lấy từ backend -->
    <select name="payment_method">
        <option value="Credit Card">Thẻ Tín Dụng</option>
    </select>
    <button type="submit">Thanh Toán Tất Cả</button>
</form>
</form>

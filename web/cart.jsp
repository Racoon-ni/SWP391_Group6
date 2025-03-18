<%@ page import="dao.CartItemDAO, entity.CartItem, java.util.List" %>
<%
    Integer accountId = (Integer) session.getAttribute("accountId");
    if (accountId == null) {
        response.sendRedirect("Login.jsp");
        return;
    }

    CartItemDAO cartDAO = new CartItemDAO();
    List<CartItem> cartItems = cartDAO.getCartByUser(accountId);
%>

<h2>Your Shopping Cart</h2>
<table>
    <tr>
        <th>Book ID</th>
        <th>Quantity</th>
        <th>Format</th>
        <th>Action</th>
    </tr>
    <% for (CartItem item : cartItems) { %>
    <tr>
        <td><%= item.getBookId() %></td>
        <td><%= item.getQuantity() %></td>
        <td><%= item.getBookFormat() %></td>
        <td><a href="removeFromCart?cartItemId=<%= item.getCartItemId() %>">Remove</a></td>
    </tr>
    <% } %>
</table>

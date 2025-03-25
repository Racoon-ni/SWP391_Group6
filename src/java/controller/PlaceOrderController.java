package controller;

import dao.CartDAO;
import dao.OrderDAO;
import dao.TransactionDAO;
import entity.Account;
import entity.Book;
import entity.CartItem;
import entity.Order;
import entity.OrderItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/place-order")
public class PlaceOrderController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        String shippingAddress = request.getParameter("shipping_address");
        String paymentMethod = request.getParameter("payment_method");

        if (paymentMethod == null || shippingAddress == null || paymentMethod.trim().isEmpty() || shippingAddress.trim().isEmpty()) {
            response.sendRedirect("Cart.jsp");
            return;
        }

        try {
            OrderDAO orderDAO = new OrderDAO();
            TransactionDAO transactionDAO = new TransactionDAO();
            CartDAO cartDAO = new CartDAO();

            List<OrderItem> orderItems = new ArrayList<>();
            double totalPrice = 0;

            // ✅ Nếu mua từ giỏ hàng
            List<CartItem> cartItems = cartDAO.getCartItems(account.getAccount_id());
            if (cartItems != null && !cartItems.isEmpty()) {
                for (CartItem item : cartItems) {
                    totalPrice += item.getQuantity() * item.getPrice();

                    OrderItem orderItem = new OrderItem();
                    orderItem.setBookId(item.getBook_id());
                    orderItem.setBookFormat(item.getBook_format());
                    orderItem.setPrice(item.getPrice());
                    orderItem.setQuantity(item.getQuantity());
                    orderItems.add(orderItem);
                }
            } else {
                // ✅ Nếu mua 1 cuốn sách (Mua Ngay)
                Book selectedBook = (Book) session.getAttribute("selectedBook");
                Integer quantity = (Integer) session.getAttribute("selectedQuantity");
                String format = (String) session.getAttribute("selectedFormat");

                if (selectedBook == null || quantity == null || format == null) {
                    response.sendRedirect("home.jsp");
                    return;
                }

                totalPrice = selectedBook.getPrice() * quantity;

                OrderItem orderItem = new OrderItem();
                orderItem.setBookId(selectedBook.getBook_id());
                orderItem.setBookFormat(format);
                orderItem.setPrice(selectedBook.getPrice());
                orderItem.setQuantity(quantity);
                orderItems.add(orderItem);
            }

            // Giao dịch
            int transactionId = transactionDAO.createTransaction(account.getAccount_id(), totalPrice, paymentMethod);

            // Tạo đơn hàng
            Order order = new Order();
            order.setAccountId(account.getAccount_id());
            order.setTransactionId(transactionId);
            order.setOrderDate(new Timestamp(System.currentTimeMillis()));
            order.setStatus("Pending");
            order.setShippingAddress(shippingAddress);

            int orderId = orderDAO.createOrder(order);

            for (OrderItem oi : orderItems) {
                oi.setOrderId(orderId);
            }

            orderDAO.createOrderItems(orderItems);

            // Nếu mua từ giỏ hàng thì clear giỏ
            if (!orderItems.isEmpty() && cartItems != null && !cartItems.isEmpty()) {
                cartDAO.clearCart(account.getAccount_id());
            }

            // ✅ Clear session nếu mua 1 quyển
            session.removeAttribute("selectedBook");
            session.removeAttribute("selectedQuantity");
            session.removeAttribute("selectedFormat");

            response.sendRedirect("order-success.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}

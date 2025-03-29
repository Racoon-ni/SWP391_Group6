// src/controller/PlaceOrderController.java
package controller;

import dao.BookDAO;
import dao.CartDAO;
import dao.OrderDAO;
import dao.TransactionDAO;
import dao.TransactionDetailsDAO;
import entity.Account;
import entity.Book;
import entity.CartItem;
import entity.Order;
import entity.OrderItem;
import entity.TransactionDetails;
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
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");

        if (shippingAddress == null || shippingAddress.trim().isEmpty()
                || paymentMethod == null || paymentMethod.trim().isEmpty()) {
            response.sendRedirect("Cart.jsp");
            return;
        }

        try {
            OrderDAO orderDAO = new OrderDAO();
            TransactionDAO transactionDAO = new TransactionDAO();
            CartDAO cartDAO = new CartDAO();
            TransactionDetailsDAO transactionDetailsDAO = new TransactionDetailsDAO();
            BookDAO bookDAO = new BookDAO();
            

            List<OrderItem> orderItems = new ArrayList<>();
            List<TransactionDetails> transactionDetailses = new ArrayList<>();
            double totalPrice = 0;

            // ✅ Mua từ giỏ hàng
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
                    
                    Book b = bookDAO.getBookById(item.getBook_id());
                    
                    TransactionDetails transactionDetails = new TransactionDetails();
                    transactionDetails.setBook_id(item.getBook_id());
                    transactionDetails.setBookFormat(item.getBook_format());
                    transactionDetails.setPrice(item.getPrice());
                    transactionDetails.setQuantity(item.getQuantity());
                    transactionDetails.setFilePath(b.getFilePath());
                    
                    transactionDetailses.add(transactionDetails);
                }
            } else {
                // ✅ Mua ngay 1 cuốn
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
                
                //Book b = bookDAO.getBookById(item.getBook_id());
                    
                TransactionDetails transactionDetails = new TransactionDetails();
                transactionDetails.setBook_id(selectedBook.getBook_id());
                transactionDetails.setBookFormat(format);
                transactionDetails.setPrice(selectedBook.getPrice());
                transactionDetails.setQuantity(quantity);
                transactionDetails.setFilePath(selectedBook.getFilePath());

                transactionDetailses.add(transactionDetails);
            }

            int transactionId = transactionDAO.createTransaction(account.getAccount_id(), totalPrice, paymentMethod,shippingAddress);
            
            for (TransactionDetails transDetail : transactionDetailses) {
                transDetail.setTransactionId(transactionId);
                transactionDetailsDAO.CreateTransactionDetail(transDetail);
            }
            
            Order order = new Order();
            order.setAccountId(account.getAccount_id());
            order.setTransactionId(transactionId);
            order.setOrderDate(new Timestamp(System.currentTimeMillis()));
            order.setStatus("Pending");
            order.setShippingAddress(shippingAddress);
            order.setPhone(phone);
            order.setEmail(email);

            int orderId = orderDAO.createOrder(order);

            for (OrderItem item : orderItems) {
                item.setOrderId(orderId);
            }

            orderDAO.createOrderItems(orderItems);

            // ✅ Dọn giỏ nếu có
            if (cartItems != null && !cartItems.isEmpty()) {
                cartDAO.clearCart(account.getAccount_id());
            }

            // ✅ Dọn session nếu mua ngay
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

package controller;

import dao.CartDAO;
import dao.TransactionDAO;
import entity.CartItem;
import entity.Transaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "BuyNowController", urlPatterns = {"/buy-now"})
public class BuyNowController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer accountId = (Integer) session.getAttribute("accountId");

        if (accountId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int bookId = Integer.parseInt(request.getParameter("bookId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String bookFormat = request.getParameter("bookFormat");

        try {
            // Tạo đơn hàng ngay lập tức
            TransactionDAO transactionDAO = new TransactionDAO();
            List<CartItem> items = new ArrayList<>();
            items.add(new CartItem(0, accountId, bookId, quantity, bookFormat)); // Tạo cartItem tạm thời

            // Thanh toán đơn hàng ngay lập tức
            boolean success = transactionDAO.createTransaction(accountId, items);

            if (success) {
                response.sendRedirect("order-success.jsp");
            } else {
                response.sendRedirect("order-failure.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}

package controller;

import dao.CartDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Integer accountId = (Integer) session.getAttribute("accountId");
            double total_price = Double.parseDouble(request.getParameter("total_price"));
            String paymentMethod = request.getParameter("payment_method");
            
            CartDAO cartDAO = new CartDAO();
            boolean success = cartDAO.checkoutAllItems(accountId, total_price, paymentMethod);
            
            if (success) {
                response.sendRedirect("TransactionSuccess.jsp");
            } else {
                response.getWriter().println("<script>alert('Lỗi khi thanh toán giỏ hàng!'); window.location='Cart.jsp';</script>");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

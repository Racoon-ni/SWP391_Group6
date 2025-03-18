package controller;

import dao.CartDAO;
import entity.Account;
import entity.CartItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
             HttpSession session = request.getSession(false); // Không tạo session mới nếu chưa có
        Account user = (session != null) ? (Account) session.getAttribute("account") : null;

        if (user == null) {
            session = request.getSession(true);
            session.setAttribute("redirectPage", "CartServlet");
            response.sendRedirect("Login.jsp");
            return;
        }

        CartDAO cartDAO = new CartDAO();
        ArrayList<CartItem> cartItems = cartDAO.getCartItems(user.getAccountId());
         if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
         
        request.setAttribute("cartItems", cartItems);
        request.getRequestDispatcher("Cart.jsp").forward(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CartServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

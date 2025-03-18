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


@WebServlet("/RemoveCartItemServlet")
public class RemoveCartItemServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Integer cartItemId = Integer.parseInt(request.getParameter("cart_item_id"));
            
            CartDAO cartDAO = new CartDAO();
            boolean success = cartDAO.removeCartItem(cartItemId);
            
            if (success) {
                response.sendRedirect("CartServlet");
            } else {
                response.getWriter().println("<script>alert('Lỗi khi xóa sản phẩm khỏi giỏ hàng!'); window.location='Cart.jsp';</script>");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RemoveCartItemServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

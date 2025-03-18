package controller;

import dao.CartItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/cart")
public class CartController extends HttpServlet {
    private final CartItemDAO cartDAO = new CartItemDAO();
@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer accountId = (Integer) session.getAttribute("accountId");

        if (accountId == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        request.setAttribute("cartItems", cartDAO.getCartByUser(accountId));
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer accountId = (Integer) session.getAttribute("accountId");

        if (accountId == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        int bookId = Integer.parseInt(request.getParameter("bookId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String bookFormat = request.getParameter("bookFormat");

        boolean success = cartDAO.addToCart(accountId, bookId, quantity, bookFormat);

        if (success) {
            response.sendRedirect("cart.jsp");
        } else {
            response.sendRedirect("error.jsp");
        }
    }
}

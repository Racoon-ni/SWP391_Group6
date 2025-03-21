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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/cart")
public class CartController extends HttpServlet {

    private CartDAO cartDAO;

    @Override
    public void init() {
        cartDAO = new CartDAO();
    }

    // Hiển thị giỏ hàng
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("account");
            if (account == null) {
                response.sendRedirect("Login.jsp");
                return;
            }

            List<CartItem> cartItems = cartDAO.getCartItems(account.getAccount_id());
            request.setAttribute("cartItems", cartItems);
            request.getRequestDispatcher("Cart.jsp").forward(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Xử lý thêm, cập nhật, xóa giỏ hàng
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        int accountId = account.getAccount_id();
        String action = request.getParameter("action");

        try {
            // 1️⃣ Thêm sản phẩm vào giỏ hàng
            if ("add".equals(action)) {
                String bookIdStr = request.getParameter("book_id");
                String quantityStr = request.getParameter("quantity");
                String bookFormat = request.getParameter("book_format");

                if (bookIdStr == null || quantityStr == null || bookFormat == null
                        || bookIdStr.isEmpty() || quantityStr.isEmpty() || bookFormat.isEmpty()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu book_id, quantity hoặc book_format");
                    return;
                }

                int bookId = Integer.parseInt(bookIdStr);
                int quantity = Integer.parseInt(quantityStr);

                cartDAO.addToCart(accountId, bookId, quantity, bookFormat);
                response.sendRedirect("cart"); // Chuyển hướng về giỏ hàng
            } // 2️⃣ Cập nhật số lượng sách trong giỏ hàng
            else if ("update".equals(action)) {
                String bookIdStr = request.getParameter("book_id");
                String quantityStr = request.getParameter("quantity");

                if (bookIdStr == null || quantityStr == null || bookIdStr.isEmpty() || quantityStr.isEmpty()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu book_id hoặc quantity");
                    return;
                }

                int bookId = Integer.parseInt(bookIdStr);
                int quantity = Integer.parseInt(quantityStr);

                cartDAO.updateCartByBookId(accountId, bookId, quantity);
                response.sendRedirect("cart");
            } // 3️⃣ Xóa sản phẩm khỏi giỏ hàng theo `book_id`
            else if ("remove".equals(action)) {
                String bookIdStr = request.getParameter("book_id");

                if (bookIdStr == null || bookIdStr.isEmpty()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu book_id");
                    return;
                }

                int bookId = Integer.parseInt(bookIdStr);
                cartDAO.removeFromCartByBookId(accountId, bookId);
                response.sendRedirect("cart");
            } // 4️⃣ Xóa toàn bộ giỏ hàng
            else if ("clear".equals(action)) {
                cartDAO.clearCart(accountId);
                response.sendRedirect("cart");
            } else if ("checkout".equals(action)) {

                // Lấy danh sách sách trong giỏ hàng
                List<CartItem> cartItems = cartDAO.getCartItems(accountId);

                if (!cartItems.isEmpty()) {
                    session.setAttribute("cartItems", cartItems); // 🔥 Lưu toàn bộ giỏ hàng vào session
                    response.sendRedirect("CheckoutMultiple.jsp"); // 🆕 Chuyển hướng đến trang mới
                } else {
                    response.sendRedirect("Cart.jsp"); // Nếu giỏ hàng trống, quay lại giỏ hàng
                }
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Lỗi đầu vào: book_id và quantity phải là số");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

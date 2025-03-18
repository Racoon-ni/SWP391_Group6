package controller;

import dao.BookDAO;
import dao.CartDAO;
import entity.Account;
import entity.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("account");

        if (user == null) {
            session.setAttribute("redirectPage", request.getHeader("referer"));
            response.sendRedirect("Login.jsp");
            return;
        }

        // Lấy book_id từ request
        String bookIdStr = request.getParameter("book_id");

        if (bookIdStr == null || bookIdStr.isEmpty()) {
            response.getWriter().println("<script>alert('❌ Lỗi: Không tìm thấy ID sách!'); window.location='" + request.getHeader("referer") + "';</script>");
            return;
        }

        try {
            int bookId = Integer.parseInt(bookIdStr);

            // **Lấy thông tin sách từ Database**
            BookDAO bookDAO = new BookDAO();
            Book book = bookDAO.getBookById(bookId);

            if (book == null) {
                response.getWriter().println("<script>alert('⚠️ Lỗi: Sách không tồn tại trong hệ thống!'); window.location='" + request.getHeader("referer") + "';</script>");
                return;
            }

            // Kiểm tra sách còn hàng không
            int quantity = book.getStockQuantity();
            if (quantity <= 0) {
                response.getWriter().println("<script>alert('🚫 Lỗi: Sách đã hết hàng!'); window.location='" + request.getHeader("referer") + "';</script>");
                return;
            }

            String bookFormat = book.getBookType();
            if (bookFormat == null || bookFormat.isEmpty()) {
                response.getWriter().println("<script>alert('⚠️ Lỗi: Dữ liệu sách không hợp lệ!'); window.location='" + request.getHeader("referer") + "';</script>");
                return;
            }

            // **Thêm vào giỏ hàng**
            CartDAO cartDAO = new CartDAO();
            boolean success = cartDAO.addToCart(user.getAccount_id(), bookId, 1, bookFormat);

            if (success) {
                response.sendRedirect(request.getHeader("referer") + "?message=added");
            } else {
                response.getWriter().println("<script>alert('❌ Lỗi: Thêm vào giỏ hàng thất bại!'); window.location='" + request.getHeader("referer") + "';</script>");
            }

        } catch (NumberFormatException e) {
            response.getWriter().println("<script>alert('🚨 Lỗi: ID sách không hợp lệ!'); window.location='" + request.getHeader("referer") + "';</script>");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddToCartServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.getWriter().println("<script>alert('❌ Lỗi hệ thống: Không tìm thấy driver kết nối!'); window.location='" + request.getHeader("referer") + "';</script>");
        } catch (SQLException ex) {
            Logger.getLogger(AddToCartServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.getWriter().println("<script>alert('❌ Lỗi kết nối database!'); window.location='" + request.getHeader("referer") + "';</script>");
        }
    }
}

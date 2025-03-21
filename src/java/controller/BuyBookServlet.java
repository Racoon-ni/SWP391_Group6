package controller;

import dao.BookDAO;
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

@WebServlet("/BuyBookServlet")
public class BuyBookServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        // Kiểm tra nếu chưa đăng nhập thì chuyển hướng đến trang login
        if (account == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        // Nhận dữ liệu từ BookDetail.jsp
        String bookIdStr = request.getParameter("book_id");
        String bookFormat = request.getParameter("book_format");
        String quantityStr = request.getParameter("quantity");

        if (bookIdStr == null || bookFormat == null || quantityStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu thông tin đặt hàng");
            return;
        }

        try {
            int bookId = Integer.parseInt(bookIdStr);
            int quantity = Integer.parseInt(quantityStr);

            // Lấy thông tin sách từ database
            BookDAO bookDAO = new BookDAO();
            Book book = bookDAO.getBookById(bookId);

            if (book == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Sách không tồn tại");
                return;
            }

            // Lưu thông tin đơn hàng tạm thời vào session
            session.setAttribute("selectedBook", book);
            session.setAttribute("selectedFormat", bookFormat);
            session.setAttribute("selectedQuantity", quantity);

            // Chuyển hướng đến trang thanh toán
            response.sendRedirect("Checkout.jsp");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dữ liệu không hợp lệ");
        } catch (ClassNotFoundException e) {
            throw new ServletException(e);
        } catch (SQLException ex) {
            Logger.getLogger(BuyBookServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

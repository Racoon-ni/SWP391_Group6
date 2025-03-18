package controller;

import dao.BookDAO;
import dao.TransactionDAO;
import entity.Account;
import entity.Book;
import entity.Transactions;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebServlet("/BuyBookServlet")
public class BuyBookServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Account user = (Account) session.getAttribute("account"); // Lấy user đăng nhập
            
            // Kiểm tra nếu chưa đăng nhập
            if (user == null) {
                response.sendRedirect("Login.jsp");
                return;
            }
            
            // Lấy thông tin sách từ request
            int bookId = Integer.parseInt(request.getParameter("book_id"));
            String bookFormat = request.getParameter("book_format");
            
            BookDAO bookDAO = new BookDAO();
            TransactionDAO transactionDAO = new TransactionDAO();
            
            // Lấy thông tin sách
            Book book = bookDAO.getBookById(bookId);
            if (book == null) {
                response.sendRedirect("TransactionFailed.jsp");
                return;
            }
            
            // Kiểm tra nếu là sách vật lý và hết hàng
            if (bookFormat.equals("Physical") && book.getStockQuantity() <= 0) {
                session.setAttribute("errorMessage", "Sách đã hết hàng!");
                response.sendRedirect("TransactionFailed.jsp");
                return;
            }
            
            // Tạo giao dịch mới
            Transactions transaction = new Transactions();
            transaction.setAccount_id(user.getAccountId());
            transaction.setBook_id(bookId);
            transaction.setTotal_price(book.getPrice());
            transaction.setPayment_method("Credit Card"); // Có thể tích hợp PayPal/MoMo sau
            transaction.setTransaction_date(new Timestamp(System.currentTimeMillis()));
            transaction.setStatus("Pending");
            transaction.setIs_paid(false); // Mặc định chưa thanh toán
            
            boolean success = transactionDAO.createTransaction(transaction);
            
            // Nếu giao dịch thành công, giảm số lượng sách nếu là Physical
            if (success) {
                if (bookFormat.equals("Physical")) {
                    bookDAO.updateStockQuantity(bookId, book.getStockQuantity() - 1);
                }
                response.sendRedirect("TransactionSuccess.jsp");
            } else {
                response.sendRedirect("TransactionFailed.jsp");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BuyBookServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BuyBookServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

package controller;

import dao.BookDAO;
import entity.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/buy-now")
public class BuyNowController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int bookId = Integer.parseInt(request.getParameter("book_id"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String format = request.getParameter("book_format");

            BookDAO bookDAO = new BookDAO();
            Book book = bookDAO.getBookById(bookId);

            if (book != null && quantity > 0 && format != null) {
                HttpSession session = request.getSession();
                session.setAttribute("selectedBook", book);
                session.setAttribute("selectedQuantity", quantity);
                session.setAttribute("selectedFormat", format);
                response.sendRedirect("checkout-single.jsp");
            } else {
                response.sendRedirect("BookDetail.jsp?book_id=" + bookId);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("home.jsp");
        }
    }
}

package controller;

import dao.InteractionDAO;
import entity.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author LAM HUY HOANG
 */
@WebServlet(name = "EditUserCommentController", urlPatterns = {"/editUserComment"})
public class EditUserCommentController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int interactionId = Integer.parseInt(request.getParameter("interaction_id"));
        int bookId = Integer.parseInt(request.getParameter("book_id"));
        String newComment = request.getParameter("comment");
        double newRating = Double.parseDouble(request.getParameter("rating"));

        Account loginAccount = (Account) request.getSession().getAttribute("account");
        int accountId = loginAccount.getAccountId();

        InteractionDAO dao = new InteractionDAO();
        boolean success = dao.editUserComment(interactionId, accountId, newComment, newRating);

        if (success) {
            response.sendRedirect("BookDetail.jsp?book_id=" + bookId + "&message=Edit success");
        } else {
            response.sendRedirect("BookDetail.jsp?book_id=" + bookId + "&error=Edit failed");
        }
    }
}

package controller;

import dao.InteractionDAO;
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
@WebServlet(name = "DeleteUserCommentController", urlPatterns = {"/deleteUserComment"})
public class DeleteUserCommentController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int interactionId = Integer.parseInt(request.getParameter("id"));
        
        // Lấy accountId từ session của user đang login
        int accountId = (int) request.getSession().getAttribute("accountId");
        
        InteractionDAO dao = new InteractionDAO();
        boolean result = dao.deleteUserComment(interactionId, accountId);

        if (result) {
           int bookId = Integer.parseInt(request.getParameter("book_id"));
response.sendRedirect("BookDetail.jsp?book_id=" + bookId + "&message=Deleted successfully");

        } else {
            response.sendRedirect("userComments?error=Cannot delete this comment");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}

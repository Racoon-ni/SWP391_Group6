package controller;

import dao.AccountDAO;
import entity.Account;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ChangePasswordAdminController", urlPatterns = {"/changePassAdmin"})
public class ChangePasswordAdminController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            out.print("Bạn cần đăng nhập lại.");
            return;
        }

        Account account = (Account) session.getAttribute("account");
        if (!account.isRole()) {
            out.print("Bạn không có quyền đổi mật khẩu.");
            return;
        }

        Integer accountId = account.getAccountId();
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!newPassword.equals(confirmPassword)) {
            out.print("Mật khẩu mới không khớp.");
            return;
        }

        if (newPassword.length() < 8 || !newPassword.matches(".*[A-Z].*") || !newPassword.matches(".*\\d.*")) {
            out.print("Mật khẩu phải có ít nhất 8 ký tự, một chữ hoa và một số.");
            return;
        }

        AccountDAO accountDAO = new AccountDAO();
        try {
            boolean isPasswordChanged = accountDAO.changePassword(accountId, currentPassword, newPassword);
            if (isPasswordChanged) {
                out.print("Đổi mật khẩu thành công!");
            } else {
                out.print("Mật khẩu hiện tại không đúng.");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChangePasswordAdminController.class.getName()).log(Level.SEVERE, null, ex);
            out.print("Lỗi hệ thống, vui lòng thử lại.");
        }
    }
}

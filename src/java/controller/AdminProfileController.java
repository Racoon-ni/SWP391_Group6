package controller;

import dao.AccountDAO;
import entity.Account;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.UUID;

/**
 *
 * @author SE18-CE180628-Nguyen Pham Doan Trang
 */
@WebServlet(name = "AdminProfileController", urlPatterns = {"/adminProfile"})
public class AdminProfileController extends HttpServlet {

    private final AccountDAO accDAO = new AccountDAO(); // Sửa lỗi: AccountDAO thay vì Account

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        try {
            Account account = (Account) session.getAttribute("account");
            int accountId = account.getAccountId();

            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String oldImage = request.getParameter("oldImage");
            Part filePart = request.getPart("image");
            String fileName = oldImage;

            if (filePart != null && filePart.getSize() > 0) {
                fileName = UUID.randomUUID().toString() + "_" + filePart.getSubmittedFileName();
                String uploadPath = getServletContext().getRealPath("") + "uploads";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                filePart.write(uploadPath + File.separator + fileName);
            }

            boolean updated = accDAO.updateAccount(accountId, fullName, phone, address, fileName);
            if (updated) {
                session.setAttribute("successMessage", "Cập nhật thông tin thành công!");
                Account updateAcc = accDAO.getAccountById(accountId); // Sửa lỗi: đúng tên hàm
                session.setAttribute("acc", updateAcc); // Sửa lỗi: dùng updateAcc thay vì updatedUser
            } else {
                session.setAttribute("errorMessage", "Cập nhật thất bại!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Có lỗi xảy ra!");
        }
        response.sendRedirect("AdminProfile.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        try {
            Account account = (Account) session.getAttribute("account");
            int accountId = account.getAccountId();
            Account updatedAccount = accDAO.getAccountById(accountId); // Sửa lỗi: đúng tên hàm
            session.setAttribute("account", updatedAccount);
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Có lỗi xảy ra!");
        }
        response.sendRedirect("AdminProfile.jsp");
    }
}

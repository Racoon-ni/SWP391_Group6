/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AccountDAO;
import dao.UserDAO;
import entity.Account;
import entity.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

/**
 *
 * @author SE18-CE180628-Nguyen Pham Doan Trang
 */
@WebServlet(name = "AccountProfileController", urlPatterns = {"/accountProfile"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 100)   // 100MB
public class AccountProfileController extends HttpServlet {

    private final AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "login":
                    handleLogin(request, response);
                    break;
                case "update":
                    handleUpdateProfile(request, response);
                    break;
                case "updateProfile": // Thêm case này
                    handleUpdateProfile(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                    break;
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null && action.equals("getAccount")) {
            handleGetAccount(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Account account = accountDAO.login(username, password);
            if (account != null) {
                HttpSession session = request.getSession();
                session.setAttribute("account", account);
                response.sendRedirect("home.jsp"); // Redirect to home page after successful login
            } else {
                response.sendRedirect("login.jsp?error=1"); // Redirect back to login page with error
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection error");
        }
    }

    private void handleGetAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int accountId = Integer.parseInt(request.getParameter("accountId"));
        try {
            User user = new UserDAO().getUserById(accountId);
            if (user != null) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Account not found");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection error");
        }
    }

    // hương thức này để xử lý cập nhật thông tin người dùng
    private void handleUpdateProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int accountId = Integer.parseInt(request.getParameter("accountId"));
        String fullName = request.getParameter("full_name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        Part filePart = request.getPart("image");
        String fileName = getFileName(filePart);

        String uploadDirectory = request.getServletContext().getRealPath("/uploads");
        File uploadDir = new File(uploadDirectory);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String imagePath = null;
        if (fileName != null && !fileName.isEmpty()) {
            try ( InputStream input = filePart.getInputStream()) {
                File file = new File(uploadDirectory, fileName);
                Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                imagePath = "uploads/" + fileName; // Đường dẫn tương đối để lưu vào database
            } catch (IOException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to upload image");
                return;
            }
        } else {
            // Nếu không có ảnh mới được tải lên, giữ lại ảnh cũ
            try {
                UserDAO userDAO = new UserDAO();
                User existingUser = userDAO.getUserById(accountId);
                imagePath = existingUser.getImage();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection error");
                return;
            }
        }

        try {
            UserDAO userDAO = new UserDAO();
            boolean isUpdated = userDAO.updateUser(accountId, fullName, phone, address, imagePath);
            if (isUpdated) {
                // Sau khi cập nhật thành công, lấy lại thông tin người dùng đã cập nhật
                User updatedUser = userDAO.getUserById(accountId);

                // Cập nhật thông tin người dùng trong session (nếu bạn đang sử dụng session)
                HttpSession session = request.getSession();
                session.setAttribute("user", updatedUser);

                // Chuyển hướng về trang hồ sơ cá nhân để hiển thị thông tin đã cập nhật
                response.sendRedirect("accountProfile?action=getAccount&accountId=" + accountId);
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update profile");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection error");
        }
    }

//    private void handleUpdateProfile(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        int accountId = Integer.parseInt(request.getParameter("accountId"));
//        String fullName = request.getParameter("full_name");
//        String phone = request.getParameter("phone");
//        String address = request.getParameter("address");
//        String image = request.getParameter("image");
//
//        try {
//            UserDAO userDAO = new UserDAO();
//            boolean isUpdated = userDAO.updateUser(accountId, fullName, phone, address, image);
//            if (isUpdated) {
//                // Sau khi cập nhật thành công, lấy lại thông tin người dùng đã cập nhật
//                User updatedUser = userDAO.getUserById(accountId);
//                HttpSession session = request.getSession();
//                session.setAttribute("user", updatedUser);
//
//                // Chuyển hướng về trang hồ sơ cá nhân để hiển thị thông tin đã cập nhật
//                response.sendRedirect("accountProfile?action=getAccount&accountId=" + accountId);
//            } else {
//                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update profile");
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection error");
//        }
//    }
}

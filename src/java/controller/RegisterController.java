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
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Oanh Nguyen
 */
@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegistrerController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegistrerController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Khi GET thì điều hướng về trang đăng ký
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        // Lấy dữ liệu từ form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String fullname = request.getParameter("fullname");
        String address = request.getParameter("address");
        String image = request.getParameter("image");

        // Giữ lại dữ liệu khi xảy ra lỗi
        request.setAttribute("username", username);
        request.setAttribute("email", email);
        request.setAttribute("fullname", fullname);
        request.setAttribute("phone", phone);
        request.setAttribute("address", address);
        request.setAttribute("image", image);

        // Khởi tạo DAO
        AccountDAO accountDao = new AccountDAO();
        UserDAO userDao = new UserDAO();

        // Kiểm tra tài khoản đã tồn tại
        if (accountDao.isAccountExists(username)) {
            request.setAttribute("error", "❌ Tài khoản đã tồn tại. Vui lòng chọn tên khác!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Kiểm tra email đã tồn tại
        if (accountDao.isEmailExists(email)) {
            request.setAttribute("error", "❌ Email đã tồn tại. Vui lòng dùng email khác!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Kiểm tra mật khẩu mạnh
        if (!isValidPassword(password)) {
            request.setAttribute("error", "❌ Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Tạo user mới
        User newUser = new User(0, username, password, email, false, fullname, phone, address, image);

        try {
            // Thêm tài khoản vào bảng Account và lấy accountId
            int accountId = userDao.insertAccount(newUser);
            if (accountId > 0) {
                newUser.setAccountId(accountId);

                // Thêm vào bảng UserProfile
                boolean profileInserted = userDao.insertUserProfile(newUser);

                if (profileInserted) {
                    // Đăng ký thành công
                    request.setAttribute("successMsg", "✅ Đăng ký thành công! Vui lòng đăng nhập.");
                    request.getRequestDispatcher("Login.jsp").forward(request, response);
                } else {
                    // Lỗi khi thêm profile
                    request.setAttribute("error", "❌ Đăng ký thất bại (Lỗi khi tạo hồ sơ người dùng)!");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                }
            } else {
                // Lỗi khi thêm tài khoản
                request.setAttribute("error", "❌ Đăng ký thất bại (Lỗi khi tạo tài khoản)!");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // In log để debug
            request.setAttribute("error", "❌ Lỗi hệ thống: " + e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    // Hàm kiểm tra độ mạnh của mật khẩu
    private boolean isValidPassword(String password) {
        // Ít nhất 8 ký tự, có chữ hoa, chữ thường, số, ký tự đặc biệt
        return password != null && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

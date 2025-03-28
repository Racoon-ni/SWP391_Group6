package controller;

import dao.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Random;

/**
 *
 * @author Oanh Nguyen
 */
@WebServlet(name = "ForgotPassword", urlPatterns = {"/forgot-password"})
public class ForgotPassword extends HttpServlet {

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
            out.println("<title>Servlet ForgotPassword</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ForgotPassword at " + request.getContextPath() + "</h1>");
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
        
        processRequest(request, response);
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
//        System.out.println("Đã vào ForgotPasswordController"); // Kiểm tra có chạy không
        String email = request.getParameter("email"); // Chỉ lấy email vì chỉ nhập email thôi
        AccountDAO dao = new AccountDAO();

        boolean emailExists = dao.isEmailExists(email); // ✅ Dùng lại hàm cũ để kiểm tra

        response.setContentType("text/plain;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            if (emailExists) {
                // Tạo và gửi OTP
                String otp = generateOTP(); // ✅ Sinh OTP

                // Lưu vào session
                HttpSession session = request.getSession();
                session.setAttribute("otp", otp);
                session.setAttribute("email", email);
                session.setMaxInactiveInterval(300); // 5 phút

                // ✅ CHỖ NÀY LÀ CHỖ GỌI HÀM GỬI EMAIL
                SendEmail.send(email, "Mã OTP khôi phục mật khẩu", "Mã OTP của bạn là: " + otp);

                // Gửi kết quả về client
                response.getWriter().write("success"); // báo về JS để hiển thị form nhập OTP
            } else {
                // Không có email -> Báo lỗi
                out.write("fail");
            }
        }

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

    // Hàm sinh OTP
    private String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // 6 số
        return String.valueOf(otp);
    }
    
    
}

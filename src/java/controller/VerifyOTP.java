package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Oanh Nguyen
 */
@WebServlet(name = "VerifyOTP", urlPatterns = {"/verify-otp"})
public class VerifyOTP extends HttpServlet {

    private static final long serialVersionUID = 1L;

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
            out.println("<title>Servlet VerifyOTP</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VerifyOTP at " + request.getContextPath() + "</h1>");
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

        String otpInput = request.getParameter("otpInput"); // Lấy OTP người dùng nhập
        HttpSession session = request.getSession();
        String otpSession = (String) session.getAttribute("otp"); // Lấy OTP đã lưu trong session

        System.out.println("==== [DEBUG - Verify OTP] ====");
        System.out.println("OTP nhập vào từ người dùng: " + otpInput);
        System.out.println("OTP lưu trong session: " + otpSession);
        System.out.println("Session ID hiện tại: " + session.getId());

        response.setContentType("text/plain;charset=UTF-8");

        try ( PrintWriter out = response.getWriter()) {
            if (otpSession != null && otpSession.equals(otpInput)) {
                out.write("success"); // Đúng OTP
            } else {
                out.write("fail"); // Sai OTP
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

}

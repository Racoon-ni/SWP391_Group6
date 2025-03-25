package controller;

import dao.OrderDAO;
import entity.Account;
import entity.OrderItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/order-detail")
public class OrderDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Account account = (Account) (session != null ? session.getAttribute("account") : null);

        if (account == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        String orderIdStr = request.getParameter("order_id");

        if (orderIdStr == null || orderIdStr.isEmpty()) {
            response.sendRedirect("orders");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdStr);

            OrderDAO orderDAO = new OrderDAO();
            List<OrderItem> orderItems = orderDAO.getOrderItemsByOrderId(orderId);

            request.setAttribute("orderItems", orderItems);
            request.getRequestDispatcher("order-detail.jsp").forward(request, response);
        
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("orders");
        }
    }
}

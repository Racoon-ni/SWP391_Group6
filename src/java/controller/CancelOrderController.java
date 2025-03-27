package controller;

import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/cancel-order")
public class CancelOrderController extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String orderIdStr = request.getParameter("order_id");

        if (orderIdStr == null || orderIdStr.trim().isEmpty()) {
            System.out.println("❌ order_id bị null hoặc rỗng");
            response.sendRedirect("orders");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdStr);
            String currentStatus = orderDAO.getOrderById(orderId).getStatus();

            if ("Pending".equals(currentStatus) || "Processing".equals(currentStatus)) {
                orderDAO.updateOrderStatus(orderId, "Cancelled");
            }

            response.sendRedirect("orders");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}

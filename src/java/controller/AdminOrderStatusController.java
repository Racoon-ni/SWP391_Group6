package controller;

import dao.OrderDAO;
import entity.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/order/edit")
public class AdminOrderStatusController extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String orderIdStr = request.getParameter("order_id");

        if (orderIdStr == null) {
            response.sendRedirect(request.getContextPath() + "/manageOrder"); // ✅ tuyệt đối
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdStr);
            Order order = orderDAO.getOrderById(orderId);
            request.setAttribute("order", order);
            request.getRequestDispatcher("/admin/edit-order.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/manageOrder"); // ✅ fallback
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int orderId = Integer.parseInt(request.getParameter("order_id"));
            String status = request.getParameter("status");
            // 🛑 Kiểm tra trạng thái hiện tại trước khi cho phép cập nhật
            Order currentOrder = orderDAO.getOrderById(orderId);
            if (currentOrder == null || "Cancelled".equalsIgnoreCase(currentOrder.getStatus())) {
                response.sendRedirect(request.getContextPath() + "/manageOrder?error=cannot_edit_cancelled");
                return;
            }
            
            orderDAO.updateOrderStatus(orderId, status);

            // ✅ Redirect tuyệt đối về controller danh sách đơn hàng
            response.sendRedirect(request.getContextPath() + "/manageOrder");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/manageOrder");
        }
    }
}

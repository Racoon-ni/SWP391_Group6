package controller;

import dao.OrderDAO;
import entity.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/manageOrder")
public class ManageOrderController extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String status = request.getParameter("status");
            String accountIdStr = request.getParameter("account_id");
            String shippingAddress = request.getParameter("shipping_address");

            Integer accountId = null;
            if (accountIdStr != null && !accountIdStr.trim().isEmpty()) {
                try {
                    accountId = Integer.parseInt(accountIdStr);
                } catch (NumberFormatException ignored) {
                }
            }

            List<Order> orders = orderDAO.filterOrders(status, accountId, shippingAddress);
            request.setAttribute("orderList", orders);
            request.getRequestDispatcher("manageOrder.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}

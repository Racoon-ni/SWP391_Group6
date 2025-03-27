package controller;

import dao.OrderDAO;
import entity.Account;
import entity.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/orders")
public class OrderListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Account account = (Account) (session != null ? session.getAttribute("account") : null);

        if (account == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        try {
            OrderDAO orderDAO = new OrderDAO();
            List<Order> orders = orderDAO.getOrdersByAccountId(account.getAccount_id());

            request.setAttribute("orders", orders);
            request.getRequestDispatcher("order-list.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}

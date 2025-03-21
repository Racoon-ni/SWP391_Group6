<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="entity.Book, entity.Account" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>

<%
    HttpSession sessionObj = request.getSession();
    Account user = (Account) sessionObj.getAttribute("account");
    Book selectedBook = (Book) sessionObj.getAttribute("selectedBook");
    String selectedFormat = (String) sessionObj.getAttribute("selectedFormat");
    Integer selectedQuantity = (Integer) sessionObj.getAttribute("selectedQuantity");

    if (user == null || selectedBook == null) {
        response.sendRedirect("home.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Thanh Toán</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/button.css">
    <link rel="stylesheet" href="css/checkout.css">
</head>
<body>
    <jsp:include page="Menu.jsp"/>

    <div class="container my-5">
        <h2 class="text-center fw-bold mb-4">Xác Nhận Đơn Hàng</h2>

        <div class="row justify-content-center">
            <div class="col-md-6">
                <!-- Thông tin sách -->
                <div class="card shadow-lg border-0">
                    <div class="row g-0">
                        <div class="col-md-4 text-center p-3">
                            <img src="<%= selectedBook.getCoverImage() %>" class="img-fluid rounded shadow-sm">
                        </div>
                        <div class="col-md-8">
                            <div class="card-body">
                                <h4 class="card-title fw-bold"><%= selectedBook.getTitle() %></h4>
                                <p class="mb-1"><strong>Tác giả:</strong> <%= selectedBook.getAuthorName() %></p>
                                <p class="mb-1"><strong>Hình thức:</strong> <%= selectedFormat %></p>
                                <p class="mb-1"><strong>Số lượng:</strong> <%= selectedQuantity %></p>
                                <h5 class="text-success fw-bold mt-3">Tổng tiền: $<%= selectedBook.getPrice() * selectedQuantity %></h5>
                            </div>
                        </div>
                    </div>
                </div>
                
    
                <!-- Nút thanh toán -->
                <div class="text-center mt-4">
                    <form action="ProcessPaymentServlet" method="POST">
                        <input type="hidden" name="book_id" value="<%= selectedBook.getBook_id() %>">
                        <input type="hidden" name="quantity" value="<%= selectedQuantity %>">
                        <input type="hidden" name="book_format" value="<%= selectedFormat %>">
                        <button type="submit" class="btn btn-success btn-lg w-100 shadow-sm">
                            <i class="fas fa-credit-card"></i> Thanh Toán Ngay
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="Footer.jsp"/>
</body>
</html>

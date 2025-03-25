<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Manage Books</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f9fa;
            }
            .sidebar {
                width: 250px;
                height: 100vh;
                background: #343a40;
                color: white;
                position: fixed;
                padding-top: 20px;
            }
            .sidebar a {
                display: block;
                color: white;
                padding: 10px 20px;
                text-decoration: none;
                transition: 0.3s;
            }
            .sidebar a:hover {
                background: #495057;
            }
            .content {
                margin-left: 260px;
                padding: 20px;
            }
        </style>
    </head>
    <body>
        <div class="d-flex">
            <!-- Sidebar -->
            <div class="sidebar">
                <h4 class="text-center">Dashboard</h4>
                <a href="Dashboard.jsp"> <i class="fas fa-home"></i> Trang chủ</a>
                <a href="#"> <i class="fas fa-chart-bar"></i> Báo cáo</a>
                <a href="adminProfile"> <i class="fas fa-cog"></i> Cài đặt</a>
                <a href="manageAccount"> <i class="fas fa-users"></i> Quản lí người dùng</a>
                <a href="manageBook" class="active"> <i class="fas fa-book"></i> Quản lí sách</a>
                <a href="manageComment"><i class="fas fa-book"></i> Quản lí bình luận</a>
            <a href="warningUsers"><i class="fas fa-exclamation-triangle"></i> Người dùng bị cảnh báo</a>
                 <a href="manageOrder"><i class="fas fa-box"></i> Quản lí đơn hàng</a>
            </div>

        <!-- Main Content -->
        <div class="content">
            <h3 class="bg-dark text-white p-2 text-center">Manage Books</h3>

            <!-- Search Form -->
            <form action="SearchBookController" method="post" class="my-3 d-flex">
                <input type="text" name="search" class="form-control me-2" placeholder="Search by title...">
                <button type="submit" class="btn btn-primary">Search</button>
            </form>

            <!-- Add Book Button -->
            <div class="my-3">
                <a href="AddBook.jsp" class="btn btn-success">Add Book</a>
            </div>

            <!-- Book Table -->
            <table class="table table-bordered">
                <thead class="table-primary">
                    <tr>
                        <th>Title</th>
                        <th>Author</th>
                        <th>Price</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${list}" var="p">
                        <c:if test="${p.isDelete == false}">
                            <tr>
                                <td>${p.title}</td>
                                <td>${p.authorId}</td>
                                <td>${p.price}$</td>
                                <td>
                                    <a href="editBook.jsp?id=${p.book_id}" class="btn btn-success btn-sm">Edit</a>
                                    <a href="BookDetailByAdmin?id=${p.book_id}" class="btn btn-info btn-sm">Details</a>
                                    <a href="deleteBook?id=${p.book_id}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?')">Delete</a>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Footer -->
            <div class="text-center mt-4">
                <p>&copy; Company 2025</p>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
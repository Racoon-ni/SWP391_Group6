<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Manage Accounts</title>
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
        <!-- Sidebar -->
        <div class="sidebar">
            <h4 class="text-center py-3">📊 Dashboard</h4>
            <a href="Dashboard.jsp"><i class="fas fa-home"></i> Trang chủ</a>
            <a href="#"><i class="fas fa-chart-bar"></i> Báo cáo</a>
            <a href="#"><i class="fas fa-cog"></i> Cài đặt</a>
            <a href="manageAccount"><i class="fas fa-user"></i> Quản lí người dùng</a>
            <a href="manageBook"><i class="fas fa-book"></i> Quản lí sách</a>
        </div>

        <!-- Main Content -->
        <div class="content">
            <h3 class="bg-dark text-white p-2 text-center">Manage Accounts</h3>

            <!-- Search Form -->
            <form action="searchAccount" method="post" class="my-3 d-flex">
                <input type="text" name="search" class="form-control me-2" placeholder="Search by username...">
                <button type="submit" class="btn btn-primary">Search</button>
            </form>

            <!-- Account Table -->
            <table class="table table-bordered">
                <thead class="table-primary">
                    <tr>
                        <th>Full Name</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${accountList}" var="acc">
                        <c:if test="${acc.role == false}">
                            <tr>
                                <td>${acc.fullName}</td>
                                <td>${acc.username}</td>
                                <td>${acc.email}</td>
                                <td>${acc.phone}</td>
                                

                                
                                <td>
                                    <a href="AccountDetail?id=${acc.accountId}" class="btn btn-info btn-sm">Details</a>
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

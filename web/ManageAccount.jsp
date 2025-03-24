<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<<<<<<< HEAD
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
        <a href="adminProfile"><i class="fas fa-cog"></i> Cài đặt</a>
        <a href="manageAccount"><i class="fas fa-user"></i> Quản lí người dùng</a>
        <a href="manageBook"><i class="fas fa-book"></i> Quản lí sách</a>
    </div>
    
    <!-- Main Content -->
    <div class="content">
        <h3 class="bg-dark text-white p-2 text-center">Manage Accounts</h3>
        
        <!-- Search Form -->
        <form action="searchAccount" method="post" class="my-3 d-flex">
            <input type="text" name="search" class="form-control me-2" placeholder="Search by username or email...">
            <button type="submit" class="btn btn-primary">Search</button>
        </form>
        
        <!-- Account Table -->
        <table class="table table-bordered">
            <thead class="table-primary">
                <tr>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${accountList}" var="acc">
                    <tr>
                        <td>${acc.username}</td>
                        <td>${acc.email}</td>
                        <td>
                            <c:choose>
                                <c:when test="${acc.role}">
                                    <span class="badge bg-danger">Admin</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-primary">Customer</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a href="editAccount.jsp?id=${acc.accountId}" class="btn btn-success btn-sm">Edit</a>
                            <a href="detailsAccount.jsp?id=${acc.accountId}" class="btn btn-info btn-sm">Details</a>
                            <c:if test="${!acc.role}">
                                <a href="deleteAccount?id=${acc.accountId}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?')">Delete</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
        <!-- Footer -->
        <div class="text-center mt-4">
            <p>&copy; Company 2025</p>
=======
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

            /* Custom Lock/Unlock Button - Orange */
            .btn-lock {
                background-color: #ff9800; /* Orange */
                border-color: #e68900;
            }

            .btn-unlock {
                background-color: #1a73e8; /* Orange */
                border-color: #1a73e8;
                color: white;
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
            <a href="manageComment"><i class="fas fa-book"></i> Quản lí bình luận</a>
            <a href="warningUsers"><i class="fas fa-exclamation-triangle"></i> Người dùng bị cảnh báo</a>
>>>>>>> 5969258d2346294a4874948f9246419422533aa2
        </div>

        <!-- Main Content -->
        <div class="content">
            <h3 class="bg-dark text-white p-2 text-center">Manage Accounts</h3>

            <!-- Search Form -->
            <form action="searchAccount" method="post" class="my-3 d-flex">
                <input type="text" name="search" class="form-control me-2" placeholder="Search by username or email...">
                <button type="submit" class="btn btn-primary">Search</button>
            </form>

            <!-- Account Table -->
            <table class="table table-bordered">
                <thead class="table-primary">
                    <tr>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${accountList}" var="acc">
                        <tr>
                            <td>${acc.username}</td>
                            <td>${acc.email}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${acc.role}">
                                        <span class="badge bg-danger">Admin</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-primary">Customer</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a href="editAccount.jsp?id=${acc.accountId}" class="btn btn-success btn-sm">Edit</a>
                                <a href="detailsAccount.jsp?id=${acc.accountId}" class="btn btn-info btn-sm">Details</a>
                                <c:if test="${!acc.role}">
                                    <a href="deleteAccount?id=${acc.accountId}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?')">Delete</a>
                                </c:if>
                                <c:choose>
                                    <c:when test="${acc.status == 1}">
                                        <a href="LockAccountController?id=${acc.accountId}" class="btn btn-lock btn-sm" onclick="return confirm('Are you sure?')">Lock</a>
                                    </c:when>
                                        
                                    <c:when test="${acc.status == 0}">
                                        <a href="LockAccountController?id=${acc.accountId}" class="btn btn-unlock btn-sm" onclick="return confirm('Are you sure?')">Unlock</a>
                                    </c:when>
                                </c:choose>

                            </td>
                        </tr>
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

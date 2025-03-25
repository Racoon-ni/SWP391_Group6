<%@page import="entity.Account"%>
<%@page import="dao.AccountDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Account Details</title>
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
        <h3 class="bg-dark text-white p-2 text-center">Account Details</h3>
        
        <c:if test="${not empty message}">
            <div class="alert alert-warning">${message}</div>
        </c:if>
        
            
            

            <div class="card shadow-sm p-3 mb-4">
                <div class="row g-3">
                    <div class="col-md-4 text-center">
                        <img src="${account1.image}" alt="User Image" class="img-thumbnail" width="150">
                    </div>
                    <div class="col-md-8">
                        <p><strong>ID:</strong> ${account1.accountId}</p>
                        <p><strong>Username:</strong> ${account1.username}</p>
                        <p><strong>Email:</strong> ${account1.email}</p>
                        <p><strong>Họ và tên:</strong> ${account1.fullName}</p>
                        <p><strong>Số điện thoại:</strong> ${account1.phone}</p>
                        <p><strong>Địa chỉ:</strong> ${account1.address}</p>
                    </div>
                </div>
            </div>
        
        <a href="manageAccount" class="btn btn-secondary">Back to Account List</a>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

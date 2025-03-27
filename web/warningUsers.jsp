<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Danh sách người dùng bị cảnh báo</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
          <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="YOUR_INTEGRITY_HASH" crossorigin="anonymous" referrerpolicy="no-referrer" />
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
             .sidebar a i {
                margin-right: 10px;
            }
            .content {
                margin-left: 260px;
                padding: 20px;
            }
            .table th {
                text-align: center;
            }
            .warning-badge {
                background-color: #ff9800;
                color: white;
                padding: 5px 10px;
                border-radius: 5px;
                font-weight: bold;
            }
            .lock-btn {
                background-color: blue;
                color: white;
            }
            .lock-btn:disabled {
                background-color: grey;
            }
        </style>
    </head>
    <body>
        <div class="sidebar">
            <h4 class="text-center py-3">📊 Dashboard</h4>
            <a href="Dashboard.jsp"><i class="fas fa-home"></i> Trang chủ</a>
            <a href="#"><i class="fas fa-chart-bar"></i> Báo cáo</a>
            <a href="adminProfile"><i class="fas fa-cog"></i> Cài đặt</a>
            <a href="manageAccount"><i class="fas fa-user"></i> Quản lí người dùng</a>
            <a href="manageBook"><i class="fas fa-book"></i> Quản lí sách</a>
            <a href="manageOrder"><i class="fas fa-box"></i> Quản lí đơn hàng</a>
            <a href="manageComment"><i class="fas fa-book"></i> Quản lí bình luận</a>
            <a href="warningUsers"><i class="fas fa-exclamation-triangle"></i> Người dùng bị cảnh báo</a>
        </div>
        <div class="content">
            <h3 class="bg-dark text-white p-2 text-center">📋 Danh sách người dùng bị cảnh báo</h3>

            <table class="table table-bordered text-center">
                <thead class="table-danger">
                    <tr>
                        <th>ID Người Dùng</th>
                        <th>Tên Người Dùng</th>
                        <th>Số lần cảnh báo</th>
                        <th>Thời gian cảnh báo</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="warning" items="${warningList}">
                        <tr>
                            <td>${warning.accountId}</td>
                            <td>${warning.username}</td>
                            <td>
                                <span class="warning-badge">${warning.warningCount}</span>
                            </td>
                            <td>${warning.warningTime}</td>
                            <td>
                                <form action="unlockUser" method="post">
                                    <input type="hidden" name="account_id" value="${warning.accountId}">
                                    <button type="submit" class="btn lock-btn btn-sm"
                                            <c:if test="${warning.warningCount < 3}">disabled</c:if>>

                                                <i class="fas fa-lock"></i>Mở khóa bình luận
                                            </button>
                                    </form>
                                </td>
                            </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

    </body>
</html>

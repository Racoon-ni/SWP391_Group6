<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Người Dùng Báo Cáo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h3 class="text-center bg-danger text-white p-2">Danh sách người dùng báo cáo bình luận</h3>

        <table class="table table-bordered">
            <thead class="table-warning">
                <tr>
                    <th>ID báo cáo</th>
                    <th>Tên Người Dùng</th>
                    <th>Lý Do Báo Cáo</th>
                    <th>Thời Gian Báo Cáo</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${reportList}" var="report">
                    <tr>
                        <td>${report.accountId}</td>
                        <td>${report.username}</td>
                        <td>${report.note}</td>
                        <td>${report.warningTime}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <a href="manageComment" class="btn btn-secondary">Quay lại</a>
    </div>
</body>
</html>

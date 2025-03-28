<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quản Lý Bình Luận</title>
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
            .content {
                margin-left: 260px;
                padding: 20px;
            }
            .sidebar a i {
                margin-right: 10px;
            }
            .btn-lock {
                background-color: #ff9800;
                border-color: #e68900;
            }
            .btn-unlock {
                background-color: #1a73e8;
                border-color: #1a73e8;
                color: white;
            }
            .btn-warning-user {
                background-color: #ff5722;
                border-color: #e64a19;
                color: white;
            }
            .btn-warning-user:hover {
                background-color: #d84315;
                border-color: #bf360c;
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
            <a href="manageBooks"><i class="fas fa-book"></i> Quản lí sách</a>
            <a href="manageOrder"><i class="fas fa-box"></i> Quản lí đơn hàng</a>
            <a href="manageComment"><i class="fas fa-book"></i> Quản lí bình luận</a>
            <a href="warningUsers"><i class="fas fa-exclamation-triangle"></i> Người dùng bị cảnh báo</a>
        </div>

        <div class="content">
            <h3 class="bg-dark text-white p-2 text-center">Quản Lý Bình Luận</h3>

            <form action="manageComment" method="get" class="d-flex mb-3">
                <select name="status" class="form-select me-2">
                    <option value="">-- Chọn Trạng Thái --</option>
                    <option value="0" ${param.status == '0' ? 'selected' : ''}>Hoạt động</option>
                    <option value="1" ${param.status == '1' ? 'selected' : ''}>Bị báo cáo</option>
                    <option value="2" ${param.status == '2' ? 'selected' : ''}>Đã vô hiệu hóa</option>
                </select>
                <button type="submit" class="btn btn-primary">Lọc</button>
            </form>

            <table class="table table-bordered">
                <thead class="table-primary">
                    <tr>
                        <th>ID Bình Luận</th>
                        <th>ID Sách</th>
                        <th>Tên Người Dùng</th>
                        <th>Đánh Giá</th>
                        <th>Bình Luận</th>
                        <th>Ngày Tạo</th>
                        <th>Trạng Thái</th>
                        <th>Hành Động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${interactionList}" var="interac">
                        <tr>
                            <td>${interac.interaction_id}</td>
                            <td>${interac.book_id}</td>
                            <td>${interac.username}</td>
                            <td>${interac.rating}</td>
                            <td>${interac.comment}</td>
                            <td>${interac.created_at}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${interac.status == 0}">
                                        <span class="badge bg-primary">Hoạt động</span>
                                    </c:when>
                                    <c:when test="${interac.status == 1}">
                                        <a href="viewReportedUsers?commentId=${interac.interaction_id}" class="badge bg-danger" style="text-decoration: none;">
                                            Bị báo cáo
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-secondary">Đã vô hiệu hóa</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a href="deleteComment?id=${interac.interaction_id}" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc chắn không?')">Xóa</a>
                                <c:if test="${interac.status == 1}">
                                    <a href="warningUser?id=${interac.account_id}&interaction_id=${interac.interaction_id}" class="btn btn-warning-user btn-sm">Cảnh báo người dùng</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
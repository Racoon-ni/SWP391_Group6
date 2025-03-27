<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Quản lý đơn hàng</title>
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
                top: 0;
                left: 0;
                padding-top: 20px;
            }
            .sidebar a i {
                margin-right: 10px;
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
                margin-left: 260px; /* 👈 đẩy nội dung ra khỏi sidebar */
                padding: 30px;
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
            <a href="manageOrder"><i class="fas fa-box"></i> Quản lí đơn hàng</a>
            <a href="manageComment"><i class="fas fa-book"></i> Quản lí bình luận</a>
            <a href="warningUsers"><i class="fas fa-exclamation-triangle"></i> Người dùng bị cảnh báo</a>
        </div>

        <!-- Main Content -->
        <div class="content">
            <h3 class="text-center mb-4">📦 Quản lý đơn hàng</h3>
            <form method="get" action="manageOrder" class="row g-3 mb-4">
                <div class="col-md-3">
                    <label for="status" class="form-label">Trạng thái</label>
                    <select name="status" id="status" class="form-select">
                        <option value="">-- Tất cả --</option>
                        <option value="Pending" ${param.status == 'Pending' ? 'selected' : ''}>Đang chờ</option>
                        <option value="Processing" ${param.status == 'Processing' ? 'selected' : ''}>Đang xử lý</option>
                        <option value="Shipped" ${param.status == 'Shipped' ? 'selected' : ''}>Đang giao</option>
                        <option value="Delivered" ${param.status == 'Delivered' ? 'selected' : ''}>Đã giao</option>
                        <option value="Cancelled" ${param.status == 'Cancelled' ? 'selected' : ''}>Đã hủy</option>
                    </select>
                </div>

                <div class="col-md-3">
                    <label for="accountId" class="form-label">ID người đặt</label>
                    <input type="number" name="account_id" id="accountId" value="${param.account_id}" class="form-control">
                </div>

                <div class="col-md-4">
                    <label for="shipping" class="form-label">Địa chỉ giao hàng</label>
                    <input type="text" name="shipping_address" id="shipping" value="${param.shipping_address}" class="form-control">
                </div>

                <div class="col-md-2 d-flex align-items-end">
                    <button type="submit" class="btn btn-primary w-100">Lọc</button>
                </div>
            </form>
            <table class="table table-bordered table-hover bg-white">
                <thead class="table-dark">
                    <tr>
                        <th>Mã đơn</th>
                        <th>Người đặt (ID)</th>
                        <th>Ngày đặt</th>
                        <th>Trạng thái</th>
                        <th>Địa chỉ giao hàng</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${orderList}">
                        <tr>
                            <td>${order.orderId}</td>
                            <td>${order.accountId}</td>
                            <td>${order.orderDate}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${order.status eq 'Cancelled'}">
                                        <span class="badge bg-secondary">${order.status}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <form method="post" action="admin/order/edit" class="d-flex">
                                            <input type="hidden" name="order_id" value="${order.orderId}">
                                            <select name="status" class="form-select me-2">
                                                <option value="Pending"     ${order.status eq 'Pending' ? 'selected' : ''}>Đang chờ</option>
                                                <option value="Processing"  ${order.status eq 'Processing' ? 'selected' : ''}>Đang xử lý</option>
                                                <option value="Shipped"     ${order.status eq 'Shipped' ? 'selected' : ''}>Đang giao</option>
                                                <option value="Delivered"   ${order.status eq 'Delivered' ? 'selected' : ''}>Đã giao</option>
                                                <option value="Cancelled"   ${order.status eq 'Cancelled' ? 'selected' : ''}>Đã Hủy</option>
                                            </select>
                                            <button type="submit" class="btn btn-primary btn-sm">Cập nhật</button>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td>${order.shippingAddress}</td>
                            <td>
                                <a href="order-detail?order_id=${order.orderId}" class="btn btn-info btn-sm">Chi tiết</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

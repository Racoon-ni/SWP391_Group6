<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="entity.Account" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Profile</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <style>
            .sidebar {
                width: 250px;
                height: 100vh;
                background-color: #2C3E50;
                padding-top: 20px;
                position: fixed;
                left: 0;
                top: 0;
                overflow-y: auto;
            }

            .sidebar h4 {
                color: white;
                text-align: center;
                margin-bottom: 20px;
            }

            .sidebar a {
                display: block;
                color: white;
                padding: 10px 20px;
                text-decoration: none;
                font-size: 16px;
            }

            .sidebar a:hover,
            .sidebar a.active {
                background-color: #1A252F;
            }

            .sidebar a i {
                margin-right: 10px;
            }

            body {
                margin-left: 250px;
            }

            .error-message {
                color: red;
                font-size: 14px;
            }
        </style>
    </head>
    <body>
        <div class="d-flex">
            <div class="sidebar">
                <h4 class="text-center py-3">📊 Dashboard</h4>
                <a href="Dashboard.jsp"><i class="fas fa-home"></i> Trang chủ</a>
                <a href="#"><i class="fas fa-chart-bar"></i> Báo cáo</a>
                <a href="adminProfile"><i class="fas fa-cog"></i> Cài đặt</a>
                <a href="manageAccount"><i class="fas fa-user"></i> Quản lí người dùng</a>
                <a href="manageBook"><i class="fas fa-book"></i> Quản lí sách</a>
            </div>

            <div class="container mt-4">
                <div class="header bg-dark text-white p-2">
                    <h3>Admin Profile</h3>
                </div>
                <div class="card mt-3 p-4">
                    <h4>Thông tin tài khoản</h4>
                    <%
                        Account admin = (Account) session.getAttribute("account");
                        if (admin != null && admin.isRole()) {
                    %>
                    <table class="table table-bordered">
                        <tr>
                            <th>ID:</th>
                            <td><%= admin.getAccountId() %></td>
                        </tr>
                        <tr>
                            <th>Username:</th>
                            <td><%= admin.getUsername() %></td>
                        </tr>
                        <tr>
                            <th>Password:</th>
                            <td>******** <button class="btn btn-primary btn-sm" id="changePasswordBtn">Thay đổi</button></td>
                        </tr>
                        <tr>
                            <th>Email:</th>
                            <td><%= admin.getEmail() %></td>
                        </tr>
                    </table>
                    <% } else { %>
                    <p class="text-danger">Bạn không có quyền truy cập trang này.</p>
                    <% } %>
                </div>

            </div>
        </div>

        <!-- Popup Change Password -->
        <div id="changePasswordModal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Thay đổi mật khẩu</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="changePasswordForm">
                            <div class="mb-3">
                                <label for="currentPassword" class="form-label">Mật khẩu hiện tại</label>
                                <input type="password" class="form-control" id="currentPassword" required>
                                <span class="error-message" id="errorCurrentPassword"></span>
                            </div>
                            <div class="mb-3">
                                <label for="newPassword" class="form-label">Mật khẩu mới</label>
                                <input type="password" class="form-control" id="newPassword" required>
                                <span class="error-message" id="errorNewPassword"></span>
                            </div>
                            <div class="mb-3">
                                <label for="confirmPassword" class="form-label">Nhập lại mật khẩu mới</label>
                                <input type="password" class="form-control" id="confirmPassword" required>
                                <span class="error-message" id="errorConfirmPassword"></span>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" id="savePassword">Lưu</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    </div>
                </div>
            </div>
        </div>

        <script>
            $(document).ready(function () {
                $("#changePasswordBtn").click(function () {
                    $("#changePasswordModal").modal("show");
                });

                $("#savePassword").click(function () {
                    let currentPassword = $("#currentPassword").val().trim();
                    let newPassword = $("#newPassword").val().trim();
                    let confirmPassword = $("#confirmPassword").val().trim();
                    let valid = true;

                    $(".error-message").text(""); // Xóa lỗi cũ

                    if (!currentPassword) {
                        $("#errorCurrentPassword").text("Vui lòng nhập mật khẩu hiện tại.");
                        valid = false;
                    }
                    if (newPassword.length < 8 || !/[A-Z]/.test(newPassword) || !/\d/.test(newPassword)) {
                        $("#errorNewPassword").text("Mật khẩu phải có ít nhất 8 ký tự, một chữ hoa và một số.");
                        valid = false;
                    }
                    if (newPassword !== confirmPassword) {
                        $("#errorConfirmPassword").text("Mật khẩu xác nhận không khớp.");
                        valid = false;
                    }

                    if (!valid)
                        return;

                    $.post("changePassAdmin", {
                        currentPassword: currentPassword,
                        newPassword: newPassword,
                        confirmPassword: confirmPassword // Đã thêm vào đây
                    }, function (response) {
                        alert(response);
                        if (response === "Đổi mật khẩu thành công!") {
                            $("#changePasswordModal").modal("hide");
                            location.reload();
                        }
                    }).fail(function () {
                        alert("Lỗi hệ thống, vui lòng thử lại.");
                    });
                });
            });
        </script>

    </body>
</html>
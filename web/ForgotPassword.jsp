<%-- 
    Document   : ForgotPassword
    Created on : Feb 24, 2025
    Author     : Oanh Nguyen
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quên mật khẩu</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
            body {
                background: url('images/background.jpg') no-repeat center center fixed;
                background-size: cover;
                min-height: 100vh;
                display: flex;
                justify-content: center;
                align-items: center;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }

            .card {
                width: 100%;
                max-width: 400px;
                padding: 30px;
                border-radius: 15px;
                box-shadow: 0 8px 20px rgba(0, 0, 0, 0.3);
                background-color: rgba(255, 255, 255, 0.95);
            }

            h3 {
                margin-bottom: 25px;
                font-weight: 600;
                color: #333;
            }

            label {
                font-weight: 500;
                color: #555;
            }

            .btn {
                border-radius: 8px;
                font-weight: 600;
            }

            .btn-primary:hover {
                background: #0056b3;
            }
            .btn-success:hover {
                background: #1e7e34;
            }
            .btn-warning:hover {
                background: #e0a800;
            }

            .info-text {
                font-size: 0.9rem;
                color: #777;
                margin-top: 10px;
                text-align: center;
            }

            .form-control {
                border-radius: 8px;
            }
        </style>
    </head>
    <body>
        <div class="card">
            <h3 class="text-center"><i class="fas fa-unlock-alt"></i> Quên mật khẩu</h3>

            <!-- Form nhập email hoặc username -->

            <form id="forgotForm" method="post">
                <div class="form-group">
                    <label><i class="fas fa-envelope"></i> Email:</label>
                    <input type="email" name="email" id="email" class="form-control" placeholder="Nhập email đăng nhập của bạn" required>
                </div>
                <button type="submit" class="btn btn-primary btn-block" id="sendOtpBtn">Gửi yêu cầu</button>
            </form>

            <!-- Form nhập OTP -->

            <form id="otpForm" method="post" style="display: none;">
                <div class="form-group mt-3">
                    <label><i class="fas fa-key"></i> Nhập mã OTP:</label>
                    <input type="text" name="otp" id="otp" class="form-control" placeholder="Nhập mã OTP" required>
                </div>
                <!-- Nút gửi lại OTP với bộ đếm -->
                <button type="button" id="resendOtpBtn" class="btn btn-link" disabled>Gửi lại mã OTP (<span id="countdown">60</span>s)</button>
                <button type="submit" class="btn btn-success btn-block">Xác nhận OTP</button>
            </form>

            <!-- Form nhập mật khẩu mới -->

            <form id="resetPasswordForm" method="post" style="display: none;">
                <div class="form-group mt-3">
                    <label><i class="fas fa-lock"></i> Mật khẩu mới:</label>
                    <input type="password" name="newPassword" id="newPassword" class="form-control" placeholder="Nhập mật khẩu mới" required>
                </div>
                <div class="form-group mt-3">
                    <label><i class="fas fa-lock"></i> Xác nhận mật khẩu mới:</label>
                    <input type="password" name="confirmPassword" id="confirmPassword" class="form-control" placeholder="Nhập lại mật khẩu mới" required>
                </div>
                <button type="submit" class="btn btn-warning btn-block">Đổi mật khẩu</button>
            </form>

            <a href="Login.jsp" class="btn btn-secondary btn-block mt-3"><i class="fas fa-sign-in-alt"></i> Quay lại đăng nhập</a>

            <div class="info-text">Bạn sẽ nhận mã OTP qua email nếu thông tin hợp lệ.</div>
        </div>

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
            $(document).ready(function () {
                let currentEmail = ''; // Biến lưu email hiện tại để dùng cho các bước sau
                let countdownInterval;
                // ======= Bước 1: Gửi yêu cầu OTP ============
                $("#forgotForm").submit(function (event) {
                    event.preventDefault(); // Ngăn reload trang

                    currentEmail = $("#email").val(); // Lưu email người dùng nhập

                    // Gửi yêu cầu lấy OTP
                    $.post("forgot-password", {email: currentEmail}, function (response) {
                        if (response === "success") {
                            alert("✅ Mã OTP đã được gửi tới email của bạn!");
                            $("#forgotForm").hide(); // Ẩn form nhập email
                            $("#otpForm").fadeIn(); // Hiện form nhập OTP
                            startCountdown(); // Bắt đầu đếm ngược cho nút gửi lại OTP
                        } else {
                            alert("❌ Email không tồn tại hoặc nhập sai!");
                        }
                    });
                });
                // ======= Bước 2: Xác nhận OTP ============
                $("#otpForm").submit(function (event) {
                    event.preventDefault(); // Ngăn reload trang

                    var otpCode = $("#otp").val(); // Lấy OTP người dùng nhập

                    // Gửi yêu cầu xác thực OTP
                    $.post("verify-otp", {otpInput: otpCode}, function (response) {
                        if (response === "success") {
                            alert("✅ Xác nhận OTP thành công!");
                            clearInterval(countdownInterval); // Dừng đếm ngược khi thành công
                            $("#otpForm").hide(); // Ẩn form nhập OTP
                            $("#resetPasswordForm").fadeIn(); // Hiện form đổi mật khẩu
                        } else {
                            alert("❌ Mã OTP không đúng. Vui lòng thử lại!");
                        }
                    });
                });
                // ======= Bước 3: Đổi mật khẩu ============
                $("#resetPasswordForm").submit(function (event) {
                    event.preventDefault(); // Ngăn reload trang

                    var newPassword = $("#newPassword").val();
                    var confirmPassword = $("#confirmPassword").val();

                    var passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

                    // Kiểm tra mật khẩu có khớp xác nhận không
                    if (newPassword !== confirmPassword) {
                        alert("❌ Mật khẩu xác nhận không khớp!");
                        return;
                    }

                    // Kiểm tra độ mạnh của mật khẩu
                    if (!passwordRegex.test(newPassword)) {
                        alert("❌ Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt!");
                        return;
                    }

                    // Gửi yêu cầu cập nhật mật khẩu
                    console.log("Email:", currentEmail);
                    console.log("New Password:", newPassword);
                    $.post("reset-password", {email: currentEmail, newPassword: newPassword}, function (response) {
                        if (response === "success") {
                            alert("✅ Đổi mật khẩu thành công! Hãy đăng nhập lại.");
                            window.location.href = "Login.jsp"; // Chuyển về trang login
                        } else {
                            alert("❌ Có lỗi xảy ra, vui lòng thử lại sau!");
                        }
                    });
                });
                // ======= Xử lý gửi lại mã OTP và đếm ngược ============
                function startCountdown() {
                    let countdown = 60;
                    $("#resendOtpBtn").prop("disabled", true);
                    $("#countdown").text(countdown);
                    countdownInterval = setInterval(function () {
                        countdown--;
                        $("#countdown").text(countdown);
                        if (countdown <= 0) {
                            clearInterval(countdownInterval);
                            $("#resendOtpBtn").prop("disabled", false).text("Gửi lại mã OTP");
                        }
                    }, 1000);
                }

                // Sự kiện khi bấm nút gửi lại OTP
                $("#resendOtpBtn").click(function () {
                    if (!$(this).prop("disabled")) {
                        $.post("forgot-password", {email: currentEmail}, function (response) {
                            if (response === "success") {
                                alert("✅ Mã OTP mới đã được gửi tới email của bạn!");

                                // Reset lại nội dung và bắt đầu đếm ngược
                                $("#resendOtpBtn").prop("disabled", true).html('Gửi lại mã OTP (<span id="countdown">60</span>s)');
                                startCountdown(); // Bắt đầu lại đếm ngược
                            } else {
                                alert("❌ Không thể gửi lại mã OTP, thử lại sau!");
                            }
                        });
                    }
                });
            });






        </script>
    </body>
</html>

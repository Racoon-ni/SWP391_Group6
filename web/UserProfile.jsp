<%@ page import="entity.User, dao.UserDAO" %>
<%@ page import="entity.Book, dao.BookDAO" %>
<%@ page import="entity.Account, dao.AccountDAO" %>
<%@ page import="entity.TransactionDetails, dao.TransactionDetailsDAO" %>
<%@ page session="true" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.List" %>

<%
    UserDAO userDAO = new UserDAO();
    AccountDAO accountDAO = new AccountDAO();
    BookDAO bookDAO = new BookDAO();
    Integer accountId = (Integer) session.getAttribute("accountId");
    TransactionDetailsDAO transactionDetailsDAO = new TransactionDetailsDAO();   
    if (accountId == null) {
        response.sendRedirect("Login.jsp");
        return;
    }

    User user = userDAO.getUserById(accountId);
    Account acc = accountDAO.getAccountById(accountId);
    List<TransactionDetails> transactionDetails = transactionDetailsDAO.getAllTransactionsByUserId(accountId);
    request.setAttribute("transactionDetails", transactionDetails);
    // Get the active tab from request parameter or set default
    String activeTab = request.getParameter("tab") != null ? request.getParameter("tab") : "personalInfo";
   
    if (request.getMethod().equalsIgnoreCase("POST")) {
        String fullName = request.getParameter("full_name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String image = request.getParameter("image");

        boolean result = userDAO.updateUser(accountId, fullName, phone, address, image);
        if (result) {
            response.sendRedirect("UserProfile.jsp?success=true&tab=personalInfo");
        } else {
            session.setAttribute("updateError", "true");
            response.sendRedirect("UserProfile.jsp?success=false&tab=personalInfo");
        }
    }
%>


<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Thông tin cá nhân</title>
        <link rel="stylesheet" type="text/css" href="css/userProfile.css">
        <link rel="stylesheet" type="text/css" href="css/style.css">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="YOUR_INTEGRITY_HASH" crossorigin="anonymous" referrerpolicy="no-referrer" />

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">

    </head>

    <body>

        <!-- Popup Notification -->
        <div id="successNotification" class="notification-popup success" style="display:none;">
            <strong>Thành công!</strong> <span id="successMessage"></span>
        </div>
        <div id="errorNotification" class="notification-popup error" style="display:none;">
            <strong>Thất bại!</strong> <span id="errorMessage"></span>
        </div>

        <% if (session.getAttribute("successMessage") != null) { %>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                document.getElementById('successMessage').innerText = '<%= session.getAttribute("successMessage") %>';
                showNotification('successNotification');
            });
        </script>
        <% session.removeAttribute("successMessage"); %>
        <% } %>

        <% if (session.getAttribute("errorMessage") != null) { %>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                document.getElementById('errorMessage').innerText = '<%= session.getAttribute("errorMessage") %>';
                showNotification('errorNotification');
            });
        </script>
        <% session.removeAttribute("errorMessage"); %>
        <% } %>


        <!-- Navbar -->
        <div class="navbar navbar-expand-md bg-dark navbar-dark">
            <div class="container">
                <a href="index.jsp" class="navbar-brand text-info">OBRW</a>

                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainmenu"><span
                        class="navbar-toggler-icon"></span></button>

                <div class="collapse navbar-collapse" id="mainmenu">
                    <li class="navbar-nav nav-item">
                        <c:if test="${empty sessionScope.account}">
                            <a href="Login.jsp" class="user-account for-buy">
                                <i class="icon icon-user"></i><span>Login</span>
                            </a>
                        </c:if>

                        <c:if test="${not empty sessionScope.account}">
                            <a class="nav-link" href="UserProfile.jsp">Hello, ${sessionScope.account.username}</a>
                            <a class="nav-link" href="logout">Logout</a>
                        </c:if> 
                    </li>
                </div>
            </div>
        </div>

        <div class="container mt-5" style="width: 1500px; max-width: 100%; margin: 0 auto; padding: 10px; background-color: #23272A; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);">
            <div class="content-card"
                 style="margin-bottom: 0; border-radius: 0 0 4px 4px; background: #23272A; position: relative;">
                <div class="row">

                    <div class="col-md-4 sidebar-container">
                        <div class="sidebar">
                            <div class="avatar-container">
                                <img src="<%= user.getImage() %>" class="avatar" alt="Avatar">
                                <div class="display-name"><%= user.getFullName() %></div>
                            </div>
                            <h4 class="text-warning">Cài đặt tài khoản</h4>
                            <a id="sidebar-personalInfo" class="<%= activeTab.equals("personalInfo") ? "active" : "" %>" onclick="changeTab('personalInfo')">Tài khoản của tôi</a>
                            <a id="sidebar-profile" class="<%= activeTab.equals("profile") ? "active" : "" %>" onclick="changeTab('profile')">Hồ sơ</a>
                            <h4 class="text-warning mt-4">Thanh toán</h4>
                            <a id="sidebar-paymentHistory" class="<%= activeTab.equals("paymentHistory") ? "active" : "" %>" onclick="changeTab('paymentHistory')">Lịch sử thanh toán</a>
                            <h4 class="text-warning">Cài đặt bảo mật</h4>
                            <a id="sidebar-changePassword" class="<%= activeTab.equals("changePassword") ? "active" : "" %>" onclick="changeTab('changePassword')">Đổi mật khẩu</a>
                        </div>
                    </div>

                    <div class="col-md-8">
                        <div class="card p-4">
                            <!-- TAB CONTENT -->
                            <div class="tab-content">

                                <!-- Tab 1: Thông tin cá nhân -->
                                <div class="tab-pane <%= activeTab.equals("personalInfo") ? "show active" : "" %>" id="personalInfo">
                                    <div class="p-4 bg-light text-dark rounded shadow-lg">
                                        <h4 class="fw-bold text-primary mb-3">Thông tin cá nhân</h4>

                                        <div class="mb-3">
                                            <p class="mb-1"><strong class="fw-bold">Tên hiển thị:</strong></p>
                                            <p class="text-primary fw-bold"><%= user.getFullName() %></p>
                                        </div>

                                        <div class="mb-3">
                                            <p class="mb-1"><strong class="fw-bold">Số điện thoại:</strong></p>
                                            <p class="text-dark"><%= user.getPhone() %></p>
                                        </div>

                                        <div class="mb-3">
                                            <p class="mb-1"><strong class="fw-bold">Địa chỉ:</strong></p>
                                            <p class="text-dark"><%= user.getAddress() %></p>
                                        </div>

                                        <div class="mb-3">
                                            <p class="mb-1"><strong class="fw-bold">Tên đăng nhập:</strong></p>
                                            <p class="text-dark"><%= user.getUsername() %></p>
                                        </div>

                                        <div class="mb-3">
                                            <p class="mb-1"><strong class="fw-bold">Email:</strong></p>
                                            <p class="text-dark"><%= user.getEmail() %></p>
                                        </div>

                                        <button class="btn btn-primary mt-3 px-4 py-2 fw-bold" onclick="changeTab('profile')">Thay đổi thông tin</button>
                                    </div>
                                </div>

                                <!-- Tab 2: Hồ sơ (Chỉnh sửa thông tin) -->
                                <div class="tab-pane <%= activeTab.equals("profile") ? "show active" : "" %>" id="profile">
                                    <div class="p-4 bg-light text-dark rounded shadow-lg">
                                        <h4 class="fw-bold text-primary mb-3">Cập nhật thông tin cá nhân</h4>
                                        <form method="POST" action="accountProfile?action=updateProfile" class="mt-3" enctype="multipart/form-data" id="profileForm">
                                            <!-- Thêm input hidden để truyền accountId -->
                                            <input type="hidden" name="accountId" value="<%= user.getAccountId() %>">

                                            <div class="mb-3">
                                                <label class="form-label fw-bold">Ảnh đại diện:</label>
                                                <% if (user.getImage() != null && !user.getImage().isEmpty()) { %>
                                                <img src="<%= user.getImage() %>" alt="Ảnh đại diện hiện tại" width="100">
                                                <% } %>
                                                <input type="file" name="image" class="form-control border-primary">
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label fw-bold">Tên hiển thị:</label>
                                                <input type="text" name="full_name" class="form-control border-primary" value="<%= user.getFullName() %>" id="fullNameInput">
                                                <div id="fullNameError" class="text-danger"></div>
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label fw-bold">Số điện thoại:</label>
                                                <input type="text" name="phone" class="form-control border-primary" value="<%= user.getPhone() %>" id="phoneInput">
                                                <div id="phoneError" class="text-danger"></div>
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label fw-bold">Địa chỉ:</label>
                                                <input type="text" name="address" class="form-control border-primary" value="<%= user.getAddress() %>" id="addressInput">
                                                <div id="addressError" class="text-danger"></div>
                                            </div>
                                            <div class="d-flex justify-content-between">
                                                <button type="submit" class="btn btn-success fw-bold px-4 py-2">Cập nhật</button>
                                                <button type="button" class="btn btn-secondary fw-bold px-4 py-2" onclick="changeTab('personalInfo')">Hủy</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>

                                <!-- Tab 3: Thanh toán - Lịch sử thanh toán -->
                                <div class="tab-pane <%= activeTab.equals("paymentHistory") ? "show active" : "" %>" id="paymentHistory">
                                    <div class="p-4 bg-light text-dark rounded shadow-lg">
                                        <h4 class="fw-bold text-primary mb-3">Lịch sử thanh toán</h4>

                                        <% 
                                           for (TransactionDetails trans : transactionDetails) {
                                           Book book = bookDAO.getBookById(trans.getBook_id());
                                           String shipping = transactionDetailsDAO.getShippingAddress(trans.getTransactionId());
                                           trans.setShippingAddress(shipping); // Gán địa chỉ vào object TransactionDetails
                                        %>


                                        <!-- Bắt đầu một "cart item" -->
                                        <div class="card mb-3">
                                            <div class="row g-0">
                                                <div class="col-md-2">
                                                    <img src="<%= book.getCoverImage() %>" alt="Cover" class="img-fluid rounded-start" style="object-fit: cover; width: 100%; height: 120px;">
                                                </div>
                                                <div class="col-md-8">
                                                    <div class="card-body">
                                                        <h5 class="card-title"><%= book.getTitle() %></h5>
                                                        <p class="card-text">
                                                            Số lượng: <%= trans.getQuantity() %><br>
                                                            Giá: <%= trans.getPrice() %> đ
                                                        </p>
                                                        <div class="d-flex justify-content-between align-items-center">
                                                            <div>
                                                                <button class="btn btn-success btn-sm" onclick="buyAgain(<%= trans.getTransactionDetailId() %>)">Mua lại</button>
                                                                <button class="btn btn-primary btn-sm" onclick="reviewBook(<%= book.getBook_id() %>)">Đánh giá</button>
                                                            </div>
                                                            <!-- Nút để hiển thị chi tiết đơn hàng -->
                                                            <button class="btn btn-gray btn-sm" data-bs-toggle="collapse" data-bs-target="#transactionDetails_<%= trans.getTransactionDetailId() %>" aria-expanded="false" aria-controls="transactionDetails_<%= trans.getTransactionDetailId() %>">
                                                                Xem thêm chi tiết
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <!-- Khu vực chi tiết đơn hàng (ẩn/hiện) -->
                                            <div class="collapse" id="transactionDetails_<%= trans.getTransactionDetailId() %>">
                                                <div class="card-body">
                                                    <h5 class="card-title">Chi tiết đơn hàng</h5>
                                                    <p class="card-text">
                                                        <strong>Tên sách:</strong> <%= book.getTitle() %><br>
                                                        <strong>Số lượng:</strong> <%= trans.getQuantity() %><br>
                                                        <strong>Giá:</strong> $ <%= trans.getPrice() %><br>
                                                        <strong>Địa chỉ giao hàng:</strong> <%= trans.getShippingAddress() != null ? trans.getShippingAddress() : "Không có" %><br>
                                                        <strong>Ngày giao dịch:</strong> <%= trans.getTransactionDate() != null ? trans.getTransactionDate() : "Không có" %>
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- Kết thúc một "cart item" -->

                                        <% } %>

                                    </div>

                                </div>


                                <!-- Tab 4: Đổi mật khẩu -->
                                <div class="tab-pane <%= activeTab.equals("changePassword") ? "show active" : "" %>" id="changePassword">
                                    <div class="p-4 bg-light text-dark rounded shadow-lg">
                                        <h4 class="fw-bold text-primary mb-3">Đổi mật khẩu</h4>
                                        <form method="POST" action="ChangePasswordServlet" class="mt-3">
                                            <div class="mb-3">
                                                <label class="form-label fw-bold">Mật khẩu hiện tại:</label>
                                                <div class="input-group">
                                                    <input type="password" name="currentPassword" class="form-control border-primary" required id="currentPassword">
                                                    <button class="btn btn-outline-secondary" type="button" id="toggleCurrentPassword">
                                                        <i class="fa fa-eye" aria-hidden="true"></i>
                                                    </button>
                                                </div>
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label fw-bold">Mật khẩu mới:</label>
                                                <div class="input-group">
                                                    <input type="password" name="newPassword" class="form-control border-primary" required id="newPassword">
                                                    <button class="btn btn-outline-secondary" type="button" id="toggleNewPassword">
                                                        <i class="fa fa-eye" aria-hidden="true"></i>
                                                    </button>
                                                </div>
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label fw-bold">Xác nhận mật khẩu mới:</label>
                                                <div class="input-group">
                                                    <input type="password" name="confirmPassword" class="form-control border-primary" required id="confirmPassword">
                                                    <button class="btn btn-outline-secondary" type="button" id="toggleConfirmPassword">
                                                        <i class="fa fa-eye" aria-hidden="true"></i>
                                                    </button>
                                                </div>
                                            </div>
                                            <div class="d-flex justify-content-between">
                                                <button type="submit" class="btn btn-success fw-bold px-4 py-2">Cập nhật</button>
                                                <button type="button" class="btn btn-secondary fw-bold px-4 py-2" onclick="changeTab('changePassword')">Hủy</button>
                                            </div>
                                        </form>
                                    </div>


                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Footer container with isolation -->

            <jsp:include page="Footer.jsp"></jsp:include>


                <script>

                    function validateProfileForm() {
                        let isValid = true;

                        // Lấy giá trị input
                        let fullName = document.getElementById("fullNameInput").value.trim();
                        let phone = document.getElementById("phoneInput").value.trim();
                        let address = document.getElementById("addressInput").value.trim();

                        // Xóa thông báo lỗi cũ
                        document.getElementById("fullNameError").innerText = "";
                        document.getElementById("phoneError").innerText = "";
                        document.getElementById("addressError").innerText = "";

                        // Kiểm tra từng trường có bị trống không
                        if (fullName === "") {
                            document.getElementById("fullNameError").innerText = "Tên hiển thị không được để trống.";
                            isValid = false;
                        }

                        if (phone === "") {
                            document.getElementById("phoneError").innerText = "Số điện thoại không được để trống.";
                            isValid = false;
                        } else if (!/^\d{10,11}$/.test(phone)) {
                            document.getElementById("phoneError").innerText = "Số điện thoại không hợp lệ (10-11 chữ số).";
                            isValid = false;
                        }

                        if (address === "") {
                            document.getElementById("addressError").innerText = "Địa chỉ không được để trống.";
                            isValid = false;
                        }

                        return isValid;
                    }

                    document.getElementById("profileForm").addEventListener("submit", function (event) {
                        if (!validateProfileForm()) {
                            event.preventDefault(); // Ngăn form submit nếu có lỗi
                        }
                    });

                    // Function to show popup notification
                    function showNotification(type) {
                        const notification = document.getElementById(type + 'Notification');
                        notification.classList.add('show');
                        // Hide after 3 seconds
                        setTimeout(() => {
                            notification.classList.remove('show');
                            notification.classList.add('hide');
                            // Remove hide class after animation completes
                            setTimeout(() => {
                                notification.classList.remove('hide');
                            }, 300);
                        }, 3000);
                    }

                    // Function to change tabs
                    function changeTab(tabId) {
                        // Hide all tabs
                        document.querySelectorAll('.tab-pane').forEach(tab => {
                            tab.classList.remove('show', 'active');
                        });

                        // Remove active class from sidebar links
                        document.querySelectorAll('.sidebar a').forEach(link => {
                            link.classList.remove('active');
                        });

                        // Check if the tab exists before trying to show it
                        const tabElement = document.getElementById(tabId);
                        if (tabElement) {
                            // Show selected tab
                            tabElement.classList.add('show', 'active');
                        } else {
                            console.error(`Tab with ID '${tabId}' not found.`);
                        }

                        // Check if the sidebar link exists before trying to add active class
                        const sidebarLink = document.getElementById('sidebar-' + tabId);
                        if (sidebarLink) {
                            // Add active class to sidebar link
                            sidebarLink.classList.add('active');
                        } else {
                            console.error(`Sidebar link with ID 'sidebar-${tabId}' not found.`);
                        }

                        // Update URL with tab parameter without refreshing
                        const url = new URL(window.location.href);
                        url.searchParams.set('tab', tabId);
                        window.history.pushState({}, '', url);
                    }

                    // Check for URL parameters on page load
                    document.addEventListener('DOMContentLoaded', function () {
                        const urlParams = new URLSearchParams(window.location.search);
                        const success = urlParams.get('success');
                        if (success === 'true') {
                            showNotification('success');
                        } else if (success === 'false') {
                            showNotification('error');
                        }

                        // Clean success parameter from URL after handling
                        if (success) {
                            const url = new URL(window.location.href);
                            url.searchParams.delete('success');
                            window.history.replaceState({}, document.title, url);
                        }

                        // If tab parameter exists, switch to the correct tab
                        const tab = urlParams.get('tab');
                        if (tab) {
                            changeTab(tab); // Call changeTab to select the appropriate tab
                        }
                    });


                    function showNotification(id) {
                        var notification = document.getElementById(id);
                        notification.style.display = 'block'; // Make it visible
                        notification.classList.add('show');
                        setTimeout(function () {
                            notification.classList.remove('show');
                            notification.style.display = 'none'; // Hide it after the animation
                        }, 3000); // 3 seconds
                    }

                    document.getElementById('toggleCurrentPassword').addEventListener('click', function (e) {
                        togglePassword('currentPassword', this);
                    });

                    document.getElementById('toggleNewPassword').addEventListener('click', function (e) {
                        togglePassword('newPassword', this);
                    });

                    document.getElementById('toggleConfirmPassword').addEventListener('click', function (e) {
                        togglePassword('confirmPassword', this);
                    });

                    function togglePassword(inputId, button) {
                        var input = document.getElementById(inputId);
                        var icon = button.querySelector('i');
                        if (input.type === "password") {
                            input.type = "text";
                            icon.classList.remove('fa-eye');
                            icon.classList.add('fa-eye-slash');
                        } else {
                            input.type = "password";
                            icon.classList.remove('fa-eye-slash');
                            icon.classList.add('fa-eye');
                        }
                    }


                    // Hàm xử lý mua lại sách (chưa có API backend, có thể điều hướng đến trang mua lại)
                    function buyAgain(transactionId) {
                        alert("Mua lại sách cho giao dịch ID: " + transactionId);
                        // window.location.href = "buyAgain.jsp?transactionId=" + transactionId;
                    }

                    // Hàm mở trang đánh giá sách
                    function reviewBook(book_id) {
                        window.location.href = "BookDetail.jsp?book_id=" + book_id;
                    }


            </script>


            <!-- Bootstrap JS (với Popper.js) -->
            <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>
    </body>
</html>
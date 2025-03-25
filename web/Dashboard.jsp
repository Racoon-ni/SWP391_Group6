<%-- 
    Document   : Dashboard
    Created on : Mar 4, 2025, 4:24:15 PM
    Author     : Oanh Nguyen
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="dashboard.css">
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>

<<<<<<< HEAD
    <!-- Sidebar -->
    <div class="sidebar">
        <h4 class="text-center py-3">📊 Dashboard</h4>
        <a href="#"><i class="fas fa-home"></i> Trang chủ</a>
        <a href="#"><i class="fas fa-chart-bar"></i> Báo cáo</a>
        <a href="adminProfile"><i class="fas fa-cog"></i> Cài đặt</a>
        <a href="manageAccount"><i class="fas fa-user"></i> Quản lí người dùng</a>
        <a href="manageBook"><i class="fas fa-book"></i> Quản lí sách</a>
    </div>
    
    <!-- Main Content -->
    <div class="content">
<!--        <div class="alert alert-success">✅ Cảm ơn bạn đã ghé thăm!</div>
        <div class="alert alert-danger">⚠ Có lỗi xảy ra! Hãy kiểm tra lại.</div>
        -->
        <!-- Stats Cards -->
        <div class="row">
            <div class="col-md-3">
                <div class="card text-center p-3">
                    <i class="fas fa-users fa-3x text-primary"></i>
                    <h3>80</h3>
                    <p>Users</p>
=======
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
            .card {
                box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
                border-radius: 10px;
                transition: transform 0.2s;
            }
            .card:hover {
                transform: scale(1.05);
            }
            .table thead {
                background: #007bff;
                color: white;
            }
        </style>
    </head>
    <body>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="dashboard.css">
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
            .card {
                box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
                border-radius: 10px;
                transition: transform 0.2s;
            }
            .card:hover {
                transform: scale(1.05);
            }
            .table thead {
                background: #007bff;
                color: white;
            }
        </style>
    </head>
    <body>

        <!-- Sidebar -->
        <div class="sidebar">
            <h4 class="text-center py-3">📊 Dashboard</h4>
            <a href="#"><i class="fas fa-home"></i> Trang chủ</a>
            <a href="#"><i class="fas fa-chart-bar"></i> Báo cáo</a>
            <a href="#"><i class="fas fa-cog"></i> Cài đặt</a>
            <a href="manageAccount"><i class="fas fa-user"></i> Quản lí người dùng</a>
            <a href="manageBook"><i class="fas fa-book"></i> Quản lí sách</a>
            <a href="manageComment"><i class="fas fa-book"></i> Quản lí bình luận</a>
            <a href="warningUsers"><i class="fas fa-exclamation-triangle"></i> Người dùng bị cảnh báo</a>
        </div>

        <!-- Main Content -->
        <div class="content">
            <!--        <div class="alert alert-success">✅ Cảm ơn bạn đã ghé thăm!</div>
                    <div class="alert alert-danger">⚠ Có lỗi xảy ra! Hãy kiểm tra lại.</div>
            -->
            <!-- Stats Cards -->
            <div class="row">
                <div class="col-md-3">
                    <div class="card text-center p-3">
                        <i class="fas fa-users fa-3x text-primary"></i>
                        <h3>80</h3>
                        <p>Users</p>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-center p-3">
                        <i class="fas fa-server fa-3x text-danger"></i>
                        <h3>16</h3>
                        <p>Servers</p>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-center p-3">
                        <i class="fas fa-file-alt fa-3x text-success"></i>
                        <h3>225</h3>
                        <p>Documents</p>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-center p-3">
                        <i class="fas fa-ticket-alt fa-3x text-warning"></i>
                        <h3>62</h3>
                        <p>Tickets</p>
                    </div>
>>>>>>> 5969258d2346294a4874948f9246419422533aa2
                </div>
            </div>

            <!-- Tables -->
            <div class="row mt-4">
                <div class="col-md-6">
                    <h5>📡 Servers</h5>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Server</th>
                                <th>IP</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>1</td>
                                <td>RDVMPC001</td>
                                <td>238.103.153.37</td>
                                <td>✅</td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>RDVMPC002</td>
                                <td>66.48.63.170</td>
                                <td>❌</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-6">
                    <h5>👤 Users</h5>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Username</th>
                                <th>Role</th>
                                <th>Account</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>1</td>
                                <td>Joe Bloggs</td>
                                <td><span class="badge bg-danger">Super Admin</span></td>
                                <td>A223045</td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>Timothy Hernandez</td>
                                <td><span class="badge bg-primary">Admin</span></td>
                                <td>AUA26783</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
<<<<<<< HEAD
        
        <!-- Tables -->
        <div class="row mt-4">
            <div class="col-md-6">
                <h5>📡 Servers</h5>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Server</th>
                            <th>IP</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>1</td>
                            <td>RDVMPC001</td>
                            <td>238.103.153.37</td>
                            <td>✅</td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>RDVMPC002</td>
                            <td>66.48.63.170</td>
                            <td>❌</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="col-md-6">
                <h5>👤 Users</h5>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Username</th>
                            <th>Role</th>
                            <th>Account</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>1</td>
                            <td>Joe Bloggs</td>
                            <td><span class="badge bg-danger">Super Admin</span></td>
                            <td>A223045</td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>Timothy Hernandez</td>
                            <td><span class="badge bg-primary">Admin</span></td>
                            <td>AUA26783</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        
    </div>
=======
>>>>>>> 5969258d2346294a4874948f9246419422533aa2
        <!-- Sidebar -->
        <div class="sidebar">
            <h4 class="text-center py-3">📊 Dashboard</h4>
            <a href="#"><i class="fas fa-home"></i> Trang chủ</a>
            <a href="#"><i class="fas fa-chart-bar"></i> Báo cáo</a>
            <a href="#"><i class="fas fa-cog"></i> Cài đặt</a>
            <a href="manageAccount"><i class="fas fa-user"></i> Quản lí người dùng</a>
            <a href="manageBook"><i class="fas fa-book"></i> Quản lí sách</a>
            <a href="manageOrder"><i class="fas fa-box"></i> Quản lí đơn hàng</a>
        </div>

        <!-- Main Content -->
        <div class="content">
            <!--        <div class="alert alert-success">✅ Cảm ơn bạn đã ghé thăm!</div>
                    <div class="alert alert-danger">⚠ Có lỗi xảy ra! Hãy kiểm tra lại.</div>
            -->
            <!-- Stats Cards -->
            <div class="row">
                <div class="col-md-3">
                    <div class="card text-center p-3">
                        <i class="fas fa-users fa-3x text-primary"></i>
                        <h3>80</h3>
                        <p>Users</p>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-center p-3">
                        <i class="fas fa-server fa-3x text-danger"></i>
                        <h3>16</h3>
                        <p>Servers</p>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-center p-3">
                        <i class="fas fa-file-alt fa-3x text-success"></i>
                        <h3>225</h3>
                        <p>Documents</p>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-center p-3">
                        <i class="fas fa-ticket-alt fa-3x text-warning"></i>
                        <h3>62</h3>
                        <p>Tickets</p>
                    </div>
                </div>
            </div>

            <!-- Tables -->
            <div class="row mt-4">
                <div class="col-md-6">
                    <h5>📡 Servers</h5>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Server</th>
                                <th>IP</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>1</td>
                                <td>RDVMPC001</td>
                                <td>238.103.153.37</td>
                                <td>✅</td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>RDVMPC002</td>
                                <td>66.48.63.170</td>
                                <td>❌</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-6">
                    <h5>👤 Users</h5>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Username</th>
                                <th>Role</th>
                                <th>Account</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>1</td>
                                <td>Joe Bloggs</td>
                                <td><span class="badge bg-danger">Super Admin</span></td>
                                <td>A223045</td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>Timothy Hernandez</td>
                                <td><span class="badge bg-primary">Admin</span></td>
                                <td>AUA26783</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

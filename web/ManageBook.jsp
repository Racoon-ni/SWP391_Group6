<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Books</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
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
                margin-left: 250px; /* Để nội dung chính không bị che */
            }

        </style>

    </head>
    <body>
        <div class="d-flex">
            <!-- Sidebar -->
            <div class="sidebar">
                <h4 class="text-center">Dashboard</h4>
                <a href="Dashboard.jsp"> <i class="fas fa-home"></i> Trang chủ</a>
                <a href="#"> <i class="fas fa-chart-bar"></i> Báo cáo</a>
                <a href="#"> <i class="fas fa-cog"></i> Cài đặt</a>
                <a href="manageAccount"> <i class="fas fa-users"></i> Quản lí người dùng</a>
                <a href="manageBook" class="active"> <i class="fas fa-book"></i> Quản lí sách</a>
                <a href="manageComment"><i class="fas fa-book"></i> Quản lí bình luận</a>
            <a href="warningUsers"><i class="fas fa-exclamation-triangle"></i> Người dùng bị cảnh báo</a>
                 <a href="manageOrder"><i class="fas fa-box"></i> Quản lí đơn hàng</a>
            </div>

            <!-- Main Content -->
            <div class="container mt-4">
                <!-- Header -->
                <div class="header bg-dark text-white p-2">
                    <h3>Manage Books</h3>
                </div>

                <!-- Search Form -->
                <form action="SearchBookController" method="post" class="my-3 d-flex">
                    <input type="text" name="search" class="form-control me-2" placeholder="Search by title...">
                    <button type="submit" class="btn btn-primary">Search</button>
                </form>

                <!-- Add Book Button -->
                <div class="my-3">
                    <a href="AddBook.jsp" class="btn btn-success">Add Book</a>
                </div>

                <!-- Book Table -->
                <table class="table table-bordered">
                    <thead class="table-primary">
                        <tr>
                            <th>Title</th>
                            <th>Author</th>
                            <th>Price</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${list}" var="p">
                            <c:if test="${p.isDelete == false}">
                                <tr>
                                    <td>${p.title}</td>
                                    <td>${p.authorId}</td>
                                    <td>${p.price}$</td>
                                    <td>
                                        <a href="editBook.jsp?id=${p.book_id}" class="btn btn-success btn-sm">Edit</a>
                                        <a href="detailsBook.jsp?id=${p.book_id}" class="btn btn-info btn-sm">Details</a>
                                        <a href="deleteBook?id=${p.book_id}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?')">Delete</a>
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
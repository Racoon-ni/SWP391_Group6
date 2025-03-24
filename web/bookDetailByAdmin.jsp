<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book Detail</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
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

            .book-image {
                max-width: 200px;
                height: auto;
            }

            .error-message {
                color: red;
                font-weight: bold;
                margin: 10px 0;
            }
        </style>
    </head>
    <body>

        <div class="d-flex">
            <!-- Sidebar -->
            <div class="sidebar">
                <h4 class="text-center">Dashboard</h4>
                <a href="Dashboard.jsp"><i class="fas fa-home"></i> Trang chủ</a>
                <a href="#"><i class="fas fa-chart-bar"></i> Báo cáo</a>
                <a href="#"><i class="fas fa-cog"></i> Cài đặt</a>
                <a href="manageAccount"><i class="fas fa-users"></i> Quản lí người dùng</a>
                <a href="manageBook" class="active"><i class="fas fa-book"></i> Quản lí sách</a>
            </div>

            <!-- Main Content -->
            <div class="container mt-4">
                <div class="header bg-dark text-white p-3">
                    <h3>Book Detail</h3>
                </div>

                <c:if test="${not empty error}">
                    <div class="alert alert-danger" role="alert">
                        ${error}
                    </div>
                </c:if>

                <c:if test="${not empty book}">
                    <div class="card mt-3">
                        <div class="card-body">
                            <div class="row">
                                <!-- Book Cover -->
                                <div class="col-md-4 text-center">
                                    <img src="${book.coverImage}" alt="Book Cover" class="book-image img-thumbnail">
                                </div>

                                <!-- Book Information -->
                                <div class="col-md-8">
                                    <h4>${book.title}</h4>
                                    <p><strong>Author:</strong> ${book.authorName}</p>
                                    <p><strong>Description:</strong> ${book.description}</p>
                                    <p><strong>Price:</strong> ${book.price}$</p>
                                    <p><strong>Publisher:</strong> ${book.publisher}</p>
                                    <p><strong>Publication Year:</strong> ${book.publicationYear}</p>
                                    <p><strong>Language:</strong> ${book.language}</p>
                                    <p><strong>Stock Quantity:</strong> ${book.stockQuantity}</p>
                                    <p><strong>Book Type:</strong> ${book.bookType}</p>
                                    <p><strong>Series:</strong> ${book.seriesName}</p>
                                    <p><strong>Categories:</strong> 
                                        <c:forEach items="${book.categories.split(', ')}" var="category">
                                            <span class="badge bg-primary">${category}</span>
                                        </c:forEach>
                                    </p>

                                    <!-- Action Buttons -->
                                    <a href="EditBook?id=${book.book_id}" class="btn btn-success">Edit</a>
                                    <a href="manageBook" class="btn btn-secondary">Back</a>
                                    <a href="DeleteBook?id=${book.book_id}" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this book?')">Delete</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
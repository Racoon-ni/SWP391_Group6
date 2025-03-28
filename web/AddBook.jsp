<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm Sách</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2>Thêm Sách Mới</h2>
         <c:if test="${not empty error}">
                <div style="color: red; text-align: center; margin-bottom: 15px;">
                    ${error}
                </div>
            </c:if>
        <form action="AddBookController" method="POST" >
            <div class="form-group">
                <label for="title">Tiêu Đề (Title):</label>
                <input type="text" class="form-control" id="title" name="title" required>
            </div>
            <div class="form-group">
                <label for="author">Tác Giả (Author):</label>
                <input type="text" class="form-control" id="author" name="author" required>
            </div>
            <div class="form-group">
                <label for="description">Mô Tả (Description):</label>
                <textarea class="form-control" id="description" name="description" rows="4" required></textarea>
            </div>
            <div class="form-group">
                <label for="price">Giá (Price):</label>
                <input type="number" class="form-control" id="price" name="price" required>
            </div>
            <div class="form-group">
                <label for="coverImage">Hình Ảnh Bìa (Cover Image):</label>
                <input type="url" class="form-control" id="coverImage" name="coverImage" required>
            </div>
            <div class="form-group">
                <label for="filePath">Đường Dẫn Tệp (File Path):</label>
                <input type="text" class="form-control" id="filePath" name="filePath" required>
            </div>
            <div class="form-group">
                <label for="category">Danh Mục (Category):</label>
                <input type="text" class="form-control" id="category" name="category" required>
            </div>
            <div class="form-group">
                <label for="bookType">Loại Sách (Book Type):</label>
                <select class="form-control" id="bookType" name="bookType" required>
                    <option value="Physical">Sách Vật Lý (Physical)</option>
                    <option value="EBook">Sách Điện Tử (EBook)</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Thêm Sách</button>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

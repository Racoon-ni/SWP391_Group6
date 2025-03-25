<%-- 
    Document   : AddBook
    Created on : Mar 4, 2025, 5:15:40 PM
    Author     : Oanh Nguyen
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Book Entry Form</title>
        <!-- <link rel="stylesheet" href="addBook.css"> -->
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }
            .container {
                background: white;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                width: 400px;
            }
            h2 {
                text-align: center;
                color: #333;
            }
            label {
                font-weight: bold;
                display: block;
                margin-top: 10px;
            }
            input, textarea {
                width: 100%;
                padding: 8px;
                margin-top: 5px;
                border: 1px solid #ccc;
                border-radius: 5px;
            }
            input[type="submit"] {
                background-color: #28a745;
                color: white;
                border: none;
                padding: 10px;
                margin-top: 15px;
                cursor: pointer;
                font-size: 16px;
            }
            input[type="submit"]:hover {
                background-color: #218838;
            }
        </style>
    </head>

    <body>
        <div class="container">
            <h2>Book Entry Form</h2>
            <c:if test="${not empty error}">
                <div style="color: red; text-align: center; margin-bottom: 15px;">
                    ${error}
                </div>
            </c:if>
            <form action="addBook" method="post">
                <label for="title">Title:</label>
                <input type="text" id="title" value="${list.title}" name="title" required>

                <label for="author">Author:</label>
                <input type="text" id="author" value="${list.author}" name="author" required>

                <label for="description">Description:</label>
                <input id="description" value="${list.description}" name="description"></input>                

                <label for="price">Price:</label>
                <input type="number" step="0.01" value="${list.price}"id="price" min="1" name="price">

                <label for="cover_image">Cover Image (URL):</label>
                <input type="url" id="cover_image" value="${list.cover_image}"name="cover_image">

                <label for="file_path">File Path:</label>
                <input type="url" id="file_path" value="${list.file_path}" name="file_path">



                <input type="submit" value="Save Book">
            </form>

        </div>
    </body>
</html>


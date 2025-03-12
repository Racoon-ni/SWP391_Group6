<%-- 
    Document   : viewAccount
    Created on : Mar 4, 2025, 3:03:43 AM
    Author     : Oanh Nguyen
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Admin - Account List</title>
        <link rel="stylesheet" href="../css/style.css">
    </head>
    <body>
        <h2>Danh sách tài khoản</h2>

        <table border="1">
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Email</th>
                <th>Role</th>
            </tr>
            <c:forEach var="account" items="${accountList}">
                <tr>
                    <td>${account.accountId}</td>
                    <td>${account.username}</td>
                    <td>${account.email}</td>
                    <td>
                        <c:choose>
                            <c:when test="${account.role}">
                                Admin
                            </c:when>
                            <c:otherwise>
                                User
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <a href="../admin/dashboard.jsp">Quay lại trang Admin</a>
    </body>
</html>

<%@page import="entity.Account"%>
<%@page import="entity.Category"%>
<%@ page import="entity.Book" %>

<%@page import="dao.CategoryDAO"%>
<%@page import="dao.BookDAO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    int currentPage = 1;
    int booksPerPage = 8;

    // Lấy categoryId từ URL (nếu có)
    String categoryId = request.getParameter("categoryId");

    // Lấy trang hiện tại từ URL (nếu có)
    if (request.getParameter("page") != null) {
        currentPage = Integer.parseInt(request.getParameter("page"));
    }

    // Khởi tạo DAO
    BookDAO bookDAO = new BookDAO();
    CategoryDAO categoryDAO = new CategoryDAO();

    // Lấy danh sách sách theo category (nếu có) và phân trang
    List<Book> books = bookDAO.getBooksByCategoryAndFilter(categoryId, null, currentPage, booksPerPage);

    // Lấy danh sách danh mục
    List<Category> categories = categoryDAO.getAllCategory();

    // Lấy tổng số sách để tính số trang
    int totalBooks = bookDAO.getTotalBooks(categoryId, null);
    int totalPages = (int) Math.ceil((double) totalBooks / booksPerPage);

    // Đặt danh sách sách vào request để hiển thị trên JSP
    request.setAttribute("books", books);
    request.setAttribute("categories", categories);
    request.setAttribute("totalPages", totalPages);
    request.setAttribute("currentPage", currentPage);
%>

<!DOCTYPE html>
<html>
    <head>
        <title>All Books</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <link rel="stylesheet" type="text/css" href="css/allBook.css">
    </head>
    <body>
        <jsp:include page="Menu.jsp"></jsp:include>

            <div class="container mt-4">
                <div class="row">
                    <!-- Sidebar Categories -->
                    <div class="col-md-3">
                        <h3>Loại sách</h3>
                        <ul class="list-group">
                        <% for (Category category : categories) { %>
                        <li class="list-group-item <%= (categoryId != null && categoryId.equals(String.valueOf(category.getCategoryId()))) ? "active" : "" %>">
                            <a href="AllBook.jsp?categoryId=<%= category.getCategoryId() %>&page=1" class="text-decoration-none"><%= category.getCategoryName() %></a>
                        </li>
                        <% } %>
                    </ul>
                </div>

                <!-- Book Grid -->
                <div class="col-md-9">
                    <div class="row book-grid">
                        <% for (Book book : books) { %>
                        <div class="col-lg-3 col-md-4 col-sm-6 d-flex align-items-stretch">
                            <div class="product-item w-100">
                                <figure class="product-style">
                                    <img src="<%= book.getCoverImage() %>" alt="<%= book.getTitle() %>" class="w-100">
                                    <button type="button" class="add-to-cart">Add to Cart</button>
                                </figure>
                                <figcaption>
                                    <h3><a href="BookDetail.jsp?book_id=<%= book.getBook_id() %>"><%= book.getTitle() %></a></h3>
                                    <span><%= book.getAuthorName() %></span>
                                    <div class="item-price">$ <%= book.getPrice() %></div>
                                </figcaption>
                            </div>
                        </div>
                        <% } %>
                    </div>

                    <!-- Pagination -->
                    <nav>
                        <ul class="pagination justify-content-center py-5">
                            <% for (int i = 1; i <= totalPages; i++) { %>
                            <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                                <a class="page-link" href="AllBook.jsp?page=<%= i %>&categoryId=<%= categoryId != null ? categoryId : "" %>"><%= i %></a>
                            </li>
                            <% } %>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>

        <footer>
            <jsp:include page="Footer.jsp"></jsp:include>
        </footer>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

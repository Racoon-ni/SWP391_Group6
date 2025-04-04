<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="entity.Book" %>
<%@ page import="dao.BookDAO" %>
<%@ page import="entity.Category, dao.CategoryDAO" %>
<%@ page import="java.util.List, java.util.Map" %>
<jsp:useBean id="bookDAO" class="dao.BookDAO" scope="request"/>

<% List<Category> categories = (List<Category>) request.getAttribute("topCategories");
%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <title>OBRW - Online book reading website</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="format-detection" content="telephone=no">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="author" content="">
        <meta name="keywords" content="">
        <meta name="description" content="">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/normalize.css">
        <link rel="stylesheet" type="text/css" href="icomoon/icomoon.css">
        <link rel="stylesheet" type="text/css" href="css/vendor.css">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <link rel="stylesheet" type="text/css" href="css/home.css">

    </head>

    <body data-bs-spy="scroll" data-bs-target="#header" tabindex="0">
        <jsp:include page="Menu.jsp"></jsp:include>

            <!-- Banner Carousel with 2-second interval -->
            <!-- Billboard Section -->
            <section id="billboard" class="py-5">
                <div class="container">
                    <div id="bannerCarousel" class="carousel slide" data-ride="carousel" data-interval="2000">
                        <div class="carousel-inner">
                        <c:forEach items="${randomBooks}" var="book" varStatus="status">
                            <div class="carousel-item ${status.first ? 'active' : ''}">
                                <div class="row align-items-center">
                                    <!-- Hình ảnh bên trái -->
                                    <div class="col-md-6 text-center">
                                        <a href="BookDetail.jsp?book_id=${book.book_id}">
                                            <img src="${book.cover_image}" alt="Book Cover ${status.index + 1}"
                                                 class="img-fluid rounded shadow-lg" style="max-height: 400px;">
                                        </a>
                                    </div>
                                    <!-- Nội dung bên phải -->
                                    <div class="col-md-6">
                                        <h2 class="display-4 text-dark font-weight-bold">${book.title}</h2>
                                        <p class="lead text-muted">${book.description}</p>
                                        <a href="BookDetail.jsp?book_id=${book.book_id}" class="btn btn-primary mt-3">Read More</a>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <!-- Carousel Controls -->
                    <a class="carousel-control-prev" href="#bannerCarousel" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="carousel-control-next" href="#bannerCarousel" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>
        </section>

        <!-- Featured Books Section -->
        <section id="featured-books" class="py-5 my-5">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="section-header align-center">
                            <div class="title"><span>Some quality items</span></div>
                            <h2 class="section-title">Featured Books</h2>
                        </div>
                        <div class="product-list" data-aos="fade-up">
                            <div class="row d-flex flex-wrap align-items-stretch">
                                <!-- Kiểm tra nếu list4B không rỗng -->
                                <c:if test="${not empty list4B}">
                                    <c:forEach var="book" items="${list4B}">
                                        <div class="col-md-3">
                                            <div class="product-item">
                                                <figure class="product-style">
                                                    <img src="${book.coverImage}" alt="${book.title}" class="product-item">
                                                    <button type="button" class="add-to-cart">Add to Cart</button>
                                                </figure>
                                                <figcaption>
                                                    <h3><a href="BookDetail.jsp?book_id=${book.bookId}">${book.title}</a></h3>
                                                    <span>${book.authorName}</span>
                                                    <div class="item-price">$ ${book.price}</div>
                                                </figcaption>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>

                                <!-- Nếu list4B rỗng -->
                                <c:if test="${empty list4B}">
                                    <p class="text-center">No featured books available.</p>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- Popular Books -->
        <section id="popular-books" class="bookshelf py-5 my-5">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="section-header align-center">
                            <div class="title"><span>Some quality items</span></div>
                            <h2 class="section-title">Popular Books</h2>
                        </div>

                        <ul class="tabs">
                            <li data-tab-target="#all-genre" class="active tab">All Genre</li>
                                <%
                                    List<Category> topCategories = (List<Category>) request.getAttribute("topCategories");
                                    if (topCategories != null) {
                                        for (Category category : topCategories) {
                                %>
                            <li data-tab-target="#<%= category.getCategoryName().toLowerCase().replaceAll("\\s+", "-") %>" class="tab">
                                <%= category.getCategoryName() %>
                            </li>
                            <%
                                    }
                                }
                            %>
                        </ul>

                        <div class="tab-content">
                            <!-- All Genres -->
                            <div id="all-genre" data-tab-content class="active">
                                <div class="row">
                                    <%
                                        List<Book> list8Book = (List<Book>) request.getAttribute("list8Book");
                                        if (list8Book != null && !list8Book.isEmpty()) {
                                            for (Book book : list8Book) {
                                    %>
                                    <div class="col-md-3">
                                        <div class="product-item">
                                            <figure class="product-style">
                                                <img src="<%= book.getCoverImage() %>" alt="Book Cover" class="product-item">
                                                <button type="button" class="add-to-cart">Add to Cart</button>
                                            </figure>
                                            <figcaption>
                                                <h3><a href="BookDetail.jsp?book_id=<%= book.getBook_id() %>"><%= book.getTitle() %></a></h3>
                                                <span><%= book.getAuthorName() %></span>
                                                <div class="item-price">$ <%= book.getPrice() %></div>
                                            </figcaption>
                                        </div>
                                    </div>
                                    <%
                                            }
                                        } else {
                                    %>
                                    <p class="text-center">No books available.</p>
                                    <% } %>
                                </div>
                            </div>
                            <!-- Books by Category -->
                            <%
                                Map<Integer, List<Book>> booksByCategory = (Map<Integer, List<Book>>) request.getAttribute("booksByCategory");
                                if (topCategories != null) {
                                    for (Category category : topCategories) {
                                        List<Book> books = booksByCategory.get(category.getCategoryId());
                            %>
                            <div id="<%= category.getCategoryName().toLowerCase().replaceAll("\\s+", "-") %>" data-tab-content>
                                <div class="row">
                                    <%
                                        if (books != null && !books.isEmpty()) {
                                            for (Book book : books) {
                                    %>
                                    <div class="col-md-3">
                                        <div class="product-item">
                                            <figure class="product-style">
                                                <img src="<%= book.getCoverImage() %>" alt="Book Cover" class="product-item">
                                                <button type="button" class="add-to-cart">Add to Cart</button>
                                            </figure>
                                            <figcaption>
                                                <h3><a href="BookDetail.jsp?book_id=<%= book.getBook_id() %>"><%= book.getTitle() %></a></h3>
                                                <span><%= book.getAuthorName() %></span>
                                                <div class="item-price">$ <%= book.getPrice() %></div>
                                            </figcaption>
                                        </div>
                                    </div>
                                    <%
                                            }
                                        } else {
                                    %>
                                    <p class="text-center">No books in this category.</p>
                                    <% } %>
                                </div>
                            </div>
                            <%
                                    }
                                }
                            %>
                        </div>
                    </div>
                </div>
            </div>
        </section>



        <footer>
            <jsp:include page="Footer.jsp"></jsp:include>
        </footer>

        <script src="js/jquery-1.11.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
        crossorigin="anonymous"></script>
        <script src="js/plugins.js"></script>
        <script src="js/script.js"></script>

    </body>
</html>

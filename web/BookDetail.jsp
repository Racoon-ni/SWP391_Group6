<%-- 
    Document   : BookDetail
    Created on : Mar 4, 2025, 10:01:39 AM
    Author     : SE18-CE180628-Nguyen Pham Doan Trang
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="entity.Book, dao.BookDAO, dao.UserWarningDAO, java.util.List, entity.Account, entity.Interaction, dao.InteractionDAO, java.text.SimpleDateFormat" %>

<%
    String bookIdParam = request.getParameter("book_id");
    Book book = null;
    List<Book> similarBooks = null;
    List<Interaction> comments = null;
    double averageRating = 0;
    int reviewCount = 0;
    boolean hasCommented = false;
    boolean isBlockedComment = false;

    if (bookIdParam != null) {
        try {
            int bookId = Integer.parseInt(bookIdParam);
            BookDAO bookDAO = new BookDAO();
            book = bookDAO.getBookById(bookId);
            similarBooks = bookDAO.getSimilarBooks(bookId);
            
            InteractionDAO interactionDAO = new InteractionDAO();
            comments = interactionDAO.getCommentsByBookId(bookId);
            averageRating = interactionDAO.getAverageRatingByBookId(bookId);
            reviewCount = interactionDAO.getReviewCountByBookId(bookId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    Account loginAccount = (Account) session.getAttribute("account");
    isBlockedComment = (loginAccount != null && loginAccount.getStatus() == 2);
    if (loginAccount != null && comments != null) {
        int userId = loginAccount.getAccountId();
        UserWarningDAO warningDAO = new UserWarningDAO();
        for (Interaction comment : comments) {
            if (comment.getAccount_id() == userId) {
                hasCommented = true;
            }
            boolean hasReported = warningDAO.hasUserReportedComment(userId, comment.getInteraction_id());
            comment.setHasReported(hasReported);
        }
    }
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title><%= (book != null) ? book.getTitle() : "Book Details" %></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <!--        <link rel="stylesheet" type="text/css" href="css/style.css">-->
        <link rel="stylesheet" type="text/css" href="css/button.css">

    </head>
    <body>
        <jsp:include page="Menu.jsp"/>

        <div class="container my-5">
            <% if (book != null) { %>
            <!-- Breadcrumb navigation -->
            <%
                String[] categoryNames = new String[0];
                if (book != null) {
                    try {
                        String categories = new BookDAO().getCategoriesForBook(book.getBook_id());
                        categoryNames = categories.split(", ");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            %>
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="index.jsp">Home</a></li>
                    <% for (String category : categoryNames) { %>
                    <li class="breadcrumb-item"><a href="#"><%= category %></a></li>
                    <% } %>
                    <li class="breadcrumb-item active" aria-current="page"><%= book.getTitle() %></li>
                </ol>
            </nav>

            <div class="row">
                <div class="col-md-4 text-center">
                    <img src="<%= book.getCoverImage() %>" alt="<%= book.getTitle() %>" class="img-fluid rounded shadow-lg">

                    <div class="button-container">
    <form action="cart" method="post">
        <input type="hidden" name="action" value="add">
        <input type="hidden" name="book_id" value="<%= book.getBook_id() %>">
        <input type="hidden" name="quantity" value="1">
        <input type="hidden" name="book_format" value="Physical">
        <button type="submit" class="btn-cart">
            <i class="fas fa-shopping-cart"></i> Add To Cart
        </button>
    </form>

    <form action="buy-now" method="POST">
        <input type="hidden" name="book_id" value="<%= book.getBook_id() %>">
        <input type="hidden" name="book_format" value="Physical">
        <input type="hidden" name="quantity" value="1">
        <button type="submit" class="btn-buy">
            <i class="fas fa-credit-card"></i> Buy Book
        </button>
    </form>
</div>

                </div>

                <div class="col-md-8">
                    <div class="card p-4">
                        <h4 class="display-4 mb-4"><%= book.getTitle() %></h4>
                        <div class="mb-3">
                            <div class="d-flex align-items-center">
                                <div class="ratings me-2">
                                    <% for (int i = 1; i <= 5; i++) { %>
                                    <% if (i <= Math.round(averageRating)) { %>
                                    <i class="fa fa-star text-warning"></i>
                                    <% } else { %>
                                    <i class="fa fa-star text-secondary"></i>
                                    <% } %>
                                    <% } %>
                                </div>
                                <span class="text-muted"><%= String.format("%.1f", averageRating) %> (<%= reviewCount %> đánh giá)</span>
                            </div>
                        </div>
                        <!-- Các thông tin khác của sách -->
                        <div class="row">
                            <div class="col-12 col-md-6">
                                <p><strong>Nhà xuất bản:</strong> <%= book.getPublisher() %></p>
                            </div>
                            <div class="col-12 col-md-6">
                                <p><strong>Tác giả:</strong> <%= book.getAuthorName() %></p>
                            </div>
                            <div class="col-12 col-md-6">
                                <p><strong>Hình thức sách:</strong> <%= book.getBookType() %></p>
                            </div>
                            <div class="col-12 col-md-6">
                                <p class="lead text-success"><strong>Giá:</strong> $<%= book.getPrice() %></p>
                            </div>
                            <div class="col-12 col-md-6">
                                <p><strong>Trạng thái:</strong> 
                                    <% if (book.getStockQuantity() > 0) { %>
                                    <span class="text-success">Còn hàng</span>
                                    <% } else { %>
                                    <span class="text-danger">Hết hàng</span>
                                    <% } %>
                                </p>
                            </div>
                        </div>
                    </div>

                    <!-- Sản phẩm tương tự -->
                    <div class="mt-5">
                        <h3 class="mb-3">Xem thêm các sản phẩm tương tự</h3>
                        <div class="row row-cols-2 row-cols-md-4 g-3">
                            <% if (similarBooks != null && !similarBooks.isEmpty()) { %>
                            <% for (Book sBook : similarBooks) { %>
                            <div class="col">
                                <div class="card h-100 text-center shadow-sm" style="width: 100%; max-width: 200px;">
                                    <img src="<%= sBook.getCoverImage() %>" class="card-img-top" alt="<%= sBook.getTitle() %>" style="height: 180px; width: 100%; object-fit: contain;">
                                    <div class="card-body p-2">
                                        <h6 class="card-title text-truncate">
                                            <a href="BookDetail.jsp?book_id=<%= sBook.getBook_id() %>" class="text-dark text-decoration-none">
                                                <%= sBook.getTitle() %>
                                            </a>
                                        </h6>
                                        <p class="fw-bold text-danger mb-0">$<%= sBook.getPrice() %></p>
                                    </div>
                                </div>
                            </div>
                            <% } %>
                            <% } else { %>
                            <p class="text-muted">Không có sản phẩm tương tự.</p>
                            <% } %>
                        </div>
                    </div>

                    <!-- Thông tin chi tiết -->
                    <div class="mt-5">
                        <h3>Thông tin chi tiết</h3>
                        <ul class="list-group">
                            <li class="list-group-item"><strong>Ngôn ngữ:</strong> <%= book.getLanguage() %></li>
                            <li class="list-group-item"><strong>Năm xuất bản:</strong> <%= book.getPublicationYear() %></li>
                            <li class="list-group-item"><strong>Kho hàng:</strong> <%= book.getStockQuantity() %> sản phẩm</li>
                            <% if (book.getSeriesName() != null) { %>
                            <li class="list-group-item"><strong>Bộ sách:</strong> <%= book.getSeriesName() %> (Tập <%= book.getVolumeNumber() %>)</li>
                            <% } %>
                        </ul>
                    </div>

                    <!-- Mô tả sản phẩm -->
                    <div class="mt-5">
                        <h3>Mô tả sản phẩm</h3>
                        <p><%= book.getDescription() %></p>
                    </div>

                    <!-- Customer reviews section -->
                    <div class="mt-5">
                        <h3 class="mb-3">Đánh giá (<%= reviewCount %>)</h3>

                        <!-- Display Success/Error Messages -->
                        <% if (session.getAttribute("errorMessage") != null) { %>
                        <div class="alert alert-danger" role="alert">
                            <%= session.getAttribute("errorMessage") %>
                            <% session.removeAttribute("errorMessage"); %>
                        </div>
                        <% } %>

                        <% if (session.getAttribute("successMessage") != null) { %>
                        <div class="alert alert-success" role="alert">
                            <%= session.getAttribute("successMessage") %>
                            <% session.removeAttribute("successMessage"); %>
                        </div>
                        <% } %>

                        <!-- Comment form -->
                        <% if (loginAccount != null) { %>
                        <% if (isBlockedComment) { %>
                        <div class="alert alert-danger" role="alert">
                            Tài khoản của bạn đã bị chặn bình luận. Vui lòng liên hệ quản trị viên để biết thêm chi tiết.
                        </div>
                        <% } else if (!hasCommented) { %>
                        <div class="card mb-4 p-3">
                            <h5>Viết đánh giá của bạn</h5>
                            <form action="CommentController" method="post">
                                <input type="hidden" name="book_id" value="<%= book.getBook_id() %>">
                                <div class="mb-3">
                                    <label for="rating" class="form-label">Đánh giá sao</label>
                                    <div class="rating">
                                        <input type="hidden" name="rating" id="rating" value="" required>
                                        <div class="star-rating">
                                            <i class="far fa-star star-icon" data-rating="1"></i>
                                            <i class="far fa-star star-icon" data-rating="2"></i>
                                            <i class="far fa-star star-icon" data-rating="3"></i>
                                            <i class="far fa-star star-icon" data-rating="4"></i>
                                            <i class="far fa-star star-icon" data-rating="5"></i>
                                            <span class="rating-text ms-2"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label for="comment" class="form-label">Nội dung đánh giá</label>
                                    <textarea class="form-control" id="comment" name="comment" rows="3" required></textarea>
                                </div>
                                <button type="submit" class="btn btn-primary">Gửi đánh giá</button>
                            </form>
                        </div>
                        <% } else { %>
                        <div class="alert alert-warning" role="alert">
                            Bạn đã đánh giá cuốn sách này trước đó.
                        </div>
                        <% } %>
                        <% } else { %>
                        <div class="alert alert-info" role="alert">
                            Vui lòng <a href="Login.jsp" class="alert-link">đăng nhập</a> để viết đánh giá.
                        </div>
                        <% } %>

                        <!-- Display comments -->
                        <% if (comments != null && !comments.isEmpty()) { %>
                        <div class="comments-section">
                            <% for (Interaction comment : comments) { %>
                            <div class="card mb-3">
                                <div class="card-body">
                                    <div class="d-flex mb-2">
                                        <div class="flex-shrink-0">
                                            <img src="https://static.vecteezy.com/system/resources/previews/026/434/417/original/default-avatar-profile-icon-of-social-media-user-photo-vector.jpg" 
                                                 alt="<%= comment.getUsername() %>" 
                                                 class="rounded-circle" width="50" height="50">
                                        </div>
                                        <div class="flex-grow-1 ms-3">
                                            <h5 class="mb-0"><%= comment.getUsername() %></h5>
                                            <div class="ratings">
                                                <% for (int i = 1; i <= 5; i++) { %>
                                                <% if (i <= comment.getRating()) { %>
                                                <i class="fa fa-star text-warning"></i>
                                                <% } else { %>
                                                <i class="fa fa-star text-secondary"></i>
                                                <% } %>
                                                <% } %>
                                                <span class="text-muted ms-2">
                                                    <%= comment.getCreated_at() != null ? dateFormat.format(comment.getCreated_at()) : "" %>
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                    <p class="card-text mb-2"><%= comment.getComment() %></p>
                                    <div class="d-flex justify-content-end gap-2">
                                        <% if (loginAccount != null && comment.getAccount_id() == loginAccount.getAccountId()) { %>
                                        <div class="dropdown">
                                            <button class="btn btn-sm btn-outline-secondary" type="button" 
                                                    id="dropdownMenuButton<%= comment.getInteraction_id() %>" 
                                                    data-bs-toggle="dropdown" aria-expanded="false">
                                                <i class="fas fa-ellipsis-v"></i>
                                            </button>
                                            <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton<%= comment.getInteraction_id() %>">
                                                <li>
                                                    <button class="dropdown-item edit-comment-btn" 
                                                            data-interaction-id="<%= comment.getInteraction_id() %>"
                                                            data-rating="<%= comment.getRating() %>"
                                                            data-comment="<%= comment.getComment() %>"
                                                            data-bs-toggle="modal" data-bs-target="#editCommentModal">
                                                        <i class="fa fa-edit me-2"></i> Sửa
                                                    </button>
                                                </li>
                                                <li>
                                                    <form action="deleteUserComment" method="post" 
      onsubmit="return confirm('Are you sure you want to delete this comment?');" 
      class="d-inline">
    <input type="hidden" name="id" value="<%= comment.getInteraction_id() %>">
    <input type="hidden" name="book_id" value="<%= book.getBook_id() %>">
    <button type="submit" class="dropdown-item text-danger">
        <i class="fa fa-trash me-2"></i> Xóa
    </button>
</form>

                                                </li>
                                            </ul>
                                        </div>
                                        <% } %>
                                        <% if (loginAccount != null && comment.getAccount_id() != loginAccount.getAccountId() && !comment.isHasReported()) { %>
                                        <button type="button" class="btn btn-sm btn-outline-danger report-comment-btn" 
                                                data-interaction-id="<%= comment.getInteraction_id() %>"
                                                data-bs-toggle="modal" data-bs-target="#reportCommentModal">
                                            <i class="fa fa-flag"></i>
                                        </button>
                                        <% } else if (loginAccount != null && comment.isHasReported()) { %>
                                        <span class="badge bg-secondary">Đã báo cáo</span>
                                        <% } %>
                                    </div>
                                </div>
                            </div>
                            <% } %>
                        </div>
                        <% } else { %>
                        <div class="alert alert-light" role="alert">
                            Chưa có đánh giá nào cho sách này. Hãy là người đầu tiên đánh giá!
                        </div>
                        <% } %>
                    </div>
                </div>
            </div>
            <% } else { %>
            <div class="alert alert-danger">Không tìm thấy sách.</div>
            <% } %>
        </div>

<!-- Modal Báo cáo bình luận -->
<div class="modal fade" id="reportCommentModal" tabindex="-1" aria-labelledby="reportCommentModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form id="reportCommentForm" action="ReportCommentController" method="post">
                <input type="hidden" name="interaction_id" id="reportInteractionId" value="">
                <input type="hidden" name="book_id" value="<%= book.getBook_id() %>">
                <div class="modal-header">
                    <h5 class="modal-title" id="reportCommentModalLabel">Báo cáo bình luận vi phạm</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="reportReason" class="form-label">Lý do báo cáo:</label>
                        <select class="form-select" id="reportReason" name="reason" required>
                            <option value="">Chọn lý do báo cáo</option>
                            <option value="Ngôn từ xúc phạm hoặc không phù hợp">Ngôn từ xúc phạm hoặc không phù hợp</option>
                            <option value="Spam hoặc quảng cáo">Spam hoặc quảng cáo</option>
                            <option value="Quấy rối hoặc bắt nạt">Quấy rối hoặc bắt nạt</option>
                            <option value="Thông tin sai sự thật">Thông tin sai sự thật</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-danger">Gửi báo cáo</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- Modal Chỉnh sửa bình luận -->
<div class="modal fade" id="editCommentModal" tabindex="-1" aria-labelledby="editCommentModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form id="editCommentForm" action="editUserComment" method="post">
                <input type="hidden" name="interaction_id" id="editInteractionId" value="">
                <input type="hidden" name="book_id" value="<%= book.getBook_id() %>">
                <div class="modal-header">
                    <h5 class="modal-title" id="editCommentModalLabel">Chỉnh sửa đánh giá của bạn</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="editRating" class="form-label">Đánh giá sao</label>
                        <div class="rating">
                            <input type="hidden" name="rating" id="editRating" value="" required>
                            <div class="edit-star-rating">
                                <i class="far fa-star star-icon" data-rating="1"></i>
                                <i class="far fa-star star-icon" data-rating="2"></i>
                                <i class="far fa-star star-icon" data-rating="3"></i>
                                <i class="far fa-star star-icon" data-rating="4"></i>
                                <i class="far fa-star star-icon" data-rating="5"></i>
                                <span class="edit-rating-text ms-2"></span>
                            </div>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="editComment" class="form-label">Nội dung đánh giá</label>
                        <textarea class="form-control" id="editComment" name="comment" rows="3" required></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                </div>
            </form>
        </div>
    </div>
</div>


        <footer>
            <jsp:include page="Footer.jsp"/>
        </footer>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            // Handle Report Comment Modal
            document.addEventListener('DOMContentLoaded', function () {
                const reportButtons = document.querySelectorAll('.report-comment-btn');
                reportButtons.forEach(button => {
                    button.addEventListener('click', function () {
                        const interactionId = this.getAttribute('data-interaction-id');
                        document.getElementById('reportInteractionId').value = interactionId;
                    });
                });
            });

            // Handle Star Rating for New Comment
            document.addEventListener('DOMContentLoaded', function () {
                const stars = document.querySelectorAll('.star-rating .star-icon');
                const ratingInput = document.getElementById('rating');
                const ratingText = document.querySelector('.rating-text');

                const ratingLabels = {
                    1: "Kém",
                    2: "Bình thường",
                    3: "Tốt",
                    4: "Rất tốt",
                    5: "Xuất sắc"
                };

                stars.forEach(star => {
                    star.addEventListener('mouseover', function () {
                        const rating = this.getAttribute('data-rating');
                        highlightStars(rating);
                    });

                    star.addEventListener('click', function () {
                        const rating = this.getAttribute('data-rating');
                        ratingInput.value = rating;
                        highlightStars(rating);
                        ratingText.textContent = rating + ' - ' + ratingLabels[rating];
                    });
                });

                document.querySelector('.star-rating').addEventListener('mouseleave', function () {
                    const currentRating = ratingInput.value;
                    highlightStars(currentRating);
                });

                function highlightStars(rating) {
                    stars.forEach(star => {
                        const starRating = star.getAttribute('data-rating');
                        if (starRating <= rating) {
                            star.classList.remove('far');
                            star.classList.add('fas', 'text-warning');
                        } else {
                            star.classList.remove('fas', 'text-warning');
                            star.classList.add('far');
                        }
                    });
                }
            });

            // Handle Edit Comment Modal
            document.addEventListener('DOMContentLoaded', function () {
                const editButtons = document.querySelectorAll('.edit-comment-btn');
                const editStars = document.querySelectorAll('.edit-star-rating .star-icon');
                const editRatingInput = document.getElementById('editRating');
                const editRatingText = document.querySelector('.edit-rating-text');
                const editCommentTextarea = document.getElementById('editComment');

                const ratingLabels = {
                    1: "Kém",
                    2: "Bình thường",
                    3: "Tốt",
                    4: "Rất tốt",
                    5: "Xuất sắc"
                };

                editButtons.forEach(button => {
                    button.addEventListener('click', function () {
                        const interactionId = this.getAttribute('data-interaction-id');
                        const rating = this.getAttribute('data-rating');
                        const comment = this.getAttribute('data-comment');

                        document.getElementById('editInteractionId').value = interactionId;
                        editRatingInput.value = rating;
                        highlightEditStars(rating);
                        editRatingText.textContent = rating + ' - ' + ratingLabels[rating];
                        editCommentTextarea.value = comment;
                    });
                });

                editStars.forEach(star => {
                    star.addEventListener('mouseover', function () {
                        const rating = this.getAttribute('data-rating');
                        highlightEditStars(rating);
                    });

                    star.addEventListener('click', function () {
                        const rating = this.getAttribute('data-rating');
                        editRatingInput.value = rating;
                        highlightEditStars(rating);
                        editRatingText.textContent = rating + ' - ' + ratingLabels[rating];
                    });
                });

                document.querySelector('.edit-star-rating').addEventListener('mouseleave', function () {
                    const currentRating = editRatingInput.value;
                    highlightEditStars(currentRating);
                });

                function highlightEditStars(rating) {
                    editStars.forEach(star => {
                        const starRating = star.getAttribute('data-rating');
                        if (starRating <= rating) {
                            star.classList.remove('far');
                            star.classList.add('fas', 'text-warning');
                        } else {
                            star.classList.remove('fas', 'text-warning');
                            star.classList.add('far');
                        }
                    });
                }
            });
        </script>
        <style>
            .star-icon {
                font-size: 1.5rem;
                cursor: pointer;
                transition: color 0.2s;
            }
            .star-rating, .edit-star-rating {
                display: flex;
                align-items: center;
            }
            .rating-text, .edit-rating-text {
                font-size: 0.9rem;
                color: #6c757d;
            }
        </style>
    </body>
</html>
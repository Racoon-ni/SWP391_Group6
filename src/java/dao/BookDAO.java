/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DBConnect;
import entity.Book;
import entity.Category;
import dao.CategoryDAO;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author SE18-CE180628-Nguyen Pham Doan Trang
 */
public class BookDAO {

    PreparedStatement ps = null;
    ResultSet rs = null;
    private Connection conn;

    public List<Book> getAllBooksByAdmin() throws ClassNotFoundException {
        List<Book> list = new ArrayList<>();
        String query = "SELECT * FROM Books"; // 🔴 Lấy tất cả sách, kể cả sách ẩn

        try ( Connection conn = DBConnect.connect();  PreparedStatement pstmt = conn.prepareStatement(query);  ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getInt("author_id"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("cover_image"),
                        rs.getString("file_path"),
                        rs.getString("publisher"),
                        rs.getInt("publication_year"),
                        rs.getInt("stock_quantity"),
                        rs.getString("language"),
                        rs.getInt("series_id"),
                        rs.getInt("volume_number"),
                        rs.getString("book_type"),
                        rs.getInt("created_by"),
                        rs.getBoolean("isDelete") // 🔴 Lấy trạng thái isDelete
                );
                list.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Book> searchBook(String searchString) throws ClassNotFoundException {
        List<Book> list = new ArrayList<>();
        String query = "SELECT * FROM Books WHERE title LIKE ? AND isDelete = 0";

        try ( Connection conn = DBConnect.connect();  PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "%" + searchString + "%"); // Tìm kiếm tiêu đề chứa searchString
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getInt("author_id"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("cover_image"),
                        rs.getString("file_path"),
                        rs.getString("publisher"),
                        rs.getInt("publication_year"),
                        rs.getInt("stock_quantity"),
                        rs.getString("language"),
                        rs.getInt("series_id"),
                        rs.getInt("volume_number"),
                        rs.getString("book_type"),
                        rs.getInt("created_by"),
                        rs.getBoolean("isDelete")
                );
                System.out.println("Tìm thấy sách: " + book.getTitle() + " | isDelete: " + book.getIsDelete());
                list.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public BookDAO() throws ClassNotFoundException, SQLException {
        this.conn = new DBConnect().connect(); // Luôn khởi tạo kết nối khi tạo BookDAO
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        BookDAO dao = new BookDAO();
        List<Book> list = dao.getAllBooks();
        List<Category> listC = dao.getAllCategory();

        for (Category o : listC) {
            System.out.println(o);
        }
    }

    // Lấy danh sách tất cả sách kèm tên tác giả và thể loại
    public List<Book> getAllBooks() throws ClassNotFoundException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.*, a.name AS authorName, s.name AS seriesName, "
                + "(SELECT STRING_AGG(c.name, ', ') FROM BookCategory bc "
                + "JOIN Category c ON bc.category_id = c.category_id WHERE bc.book_id = b.book_id) AS categories "
                + "FROM Books b "
                + "JOIN Author a ON b.author_id = a.author_id "
                + "LEFT JOIN BookSeries s ON b.series_id = s.series_id";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("book_id"), rs.getString("title"), rs.getInt("author_id"), rs.getString("description"),
                        rs.getDouble("price"), rs.getString("cover_image"), rs.getString("file_path"), rs.getString("publisher"),
                        rs.getInt("publication_year"), rs.getInt("stock_quantity"), rs.getString("language"),
                        rs.getInt("series_id"), rs.getInt("volume_number"), rs.getString("book_type"), rs.getInt("created_by")
                );
                book.setAuthorName(rs.getString("authorName"));
                book.setSeriesName(rs.getString("seriesName"));
                book.setCategories(rs.getString("categories"));
                book.setIsDelete(rs.getBoolean("isDelete"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return books;
    }

    // Lấy sách theo ID
    public Book getBookById(int bookId) throws ClassNotFoundException {
        String sql = "SELECT b.*, a.name AS authorName, s.name AS seriesName, "
                + "(SELECT STRING_AGG(c.name, ', ') FROM BookCategory bc "
                + "JOIN Category c ON bc.category_id = c.category_id WHERE bc.book_id = b.book_id) AS categories "
                + "FROM Books b "
                + "JOIN Author a ON b.author_id = a.author_id "
                + "LEFT JOIN BookSeries s ON b.series_id = s.series_id "
                + "WHERE b.book_id = ?";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, bookId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Book book = new Book(
                        rs.getInt("book_id"), rs.getString("title"), rs.getInt("author_id"), rs.getString("description"),
                        rs.getDouble("price"), rs.getString("cover_image"), rs.getString("file_path"), rs.getString("publisher"),
                        rs.getInt("publication_year"), rs.getInt("stock_quantity"), rs.getString("language"),
                        rs.getInt("series_id"), rs.getInt("volume_number"), rs.getString("book_type"), rs.getInt("created_by")
                );
                book.setAuthorName(rs.getString("authorName"));
                book.setSeriesName(rs.getString("seriesName"));
                book.setCategories(rs.getString("categories"));
                book.setIsDelete(rs.getBoolean("isDelete"));
                return book;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return null;
    }

    // Lấy danh sách tất cả danh mục
    public List<Category> getAllCategory() {
        List<Category> list = new ArrayList<>();
        String query = "SELECT * FROM Category";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Category(rs.getInt("category_id"), rs.getString("name")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return list;
    }

    // Lấy sách theo danh mục
    public List<Book> getBooksByCategory(int categoryId) throws ClassNotFoundException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.*, a.name AS authorName, s.name AS seriesName, "
                + "(SELECT STRING_AGG(c.name, ', ') FROM BookCategory bc "
                + "JOIN Category c ON bc.category_id = c.category_id WHERE bc.book_id = b.book_id) AS categories "
                + "FROM Books b "
                + "JOIN Author a ON b.author_id = a.author_id "
                + "LEFT JOIN BookSeries s ON b.series_id = s.series_id "
                + "JOIN BookCategory bc ON b.book_id = bc.book_id "
                + "WHERE bc.category_id = ?";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, categoryId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("book_id"), rs.getString("title"), rs.getInt("author_id"), rs.getString("description"),
                        rs.getDouble("price"), rs.getString("cover_image"), rs.getString("file_path"), rs.getString("publisher"),
                        rs.getInt("publication_year"), rs.getInt("stock_quantity"), rs.getString("language"),
                        rs.getInt("series_id"), rs.getInt("volume_number"), rs.getString("book_type"), rs.getInt("created_by")
                );
                book.setAuthorName(rs.getString("authorName"));
                book.setSeriesName(rs.getString("seriesName"));
                book.setCategories(rs.getString("categories"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return books;
    }

    // Lấy 5 sách ngẫu nhiên (chỉ lấy ảnh bìa và mô tả)
    public List<Map<String, String>> getRandomBooks() {
        List<Map<String, String>> books = new ArrayList<>();
        String query = "SELECT TOP 5 cover_image, description FROM Books ORDER BY NEWID()";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, String> book = new HashMap<>();
                book.put("cover_image", Optional.ofNullable(rs.getString("cover_image")).orElse("./images/default-cover-book-1.jpg"));
                book.put("description", Optional.ofNullable(rs.getString("description")).orElse("No description available."));
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return books;
    }

    // Lấy danh sách sách sắp xếp theo ID, kèm tên tác giả và danh mục
    public List<Book> getSort() {
        List<Book> list = new ArrayList<>();
        String query = "SELECT b.*, a.name AS authorName, "
                + "(SELECT STRING_AGG(c.name, ', ') FROM BookCategory bc "
                + " JOIN Category c ON bc.category_id = c.category_id WHERE bc.book_id = b.book_id) AS categories "
                + "FROM Books b JOIN Author a ON b.author_id = a.author_id ORDER BY b.book_id ASC";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Book book = extractBookFromResultSet(rs);
                list.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return list;
    }

    // Lấy 4 sách ngẫu nhiên, kèm tên tác giả và danh mục
    public List<Book> getTop4() {
        List<Book> list = new ArrayList<>();
        String query = "SELECT TOP 4 b.*, a.name AS authorName, "
                + "(SELECT STRING_AGG(c.name, ', ') FROM BookCategory bc "
                + " JOIN Category c ON bc.category_id = c.category_id WHERE bc.book_id = b.book_id) AS categories "
                + "FROM Books b JOIN Author a ON b.author_id = a.author_id ORDER BY NEWID()";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Book book = extractBookFromResultSet(rs);
                list.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return list;
    }

    // Lấy top 8 sách theo danh mục, kèm tên tác giả và danh mục
    public List<Book> getTopBooksByCategory(int categoryId) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT TOP 8 b.*, a.name AS authorName, "
                + "(SELECT STRING_AGG(c.name, ', ') FROM BookCategory bc "
                + " JOIN Category c ON bc.category_id = c.category_id WHERE bc.book_id = b.book_id) AS categories "
                + "FROM Books b JOIN Author a ON b.author_id = a.author_id "
                + "JOIN BookCategory bc ON b.book_id = bc.book_id WHERE bc.category_id = ?";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            ps.setInt(1, categoryId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Book book = extractBookFromResultSet(rs);
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return books;
    }

    // Lấy 8 sách ngẫu nhiên, kèm tên tác giả và danh mục
    public List<Book> getTop8Books() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT TOP 8 b.*, a.name AS authorName, "
                + "(SELECT STRING_AGG(c.name, ', ') FROM BookCategory bc "
                + " JOIN Category c ON bc.category_id = c.category_id WHERE bc.book_id = b.book_id) AS categories "
                + "FROM Books b JOIN Author a ON b.author_id = a.author_id ORDER BY NEWID()";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Book book = extractBookFromResultSet(rs);
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return books;
    }

    // Phương thức để trích xuất dữ liệu từ ResultSet và tạo đối tượng Book
    private Book extractBookFromResultSet(ResultSet rs) throws SQLException {
        Book book = new Book(
                rs.getInt("book_id"),
                rs.getString("title"),
                rs.getInt("author_id"),
                rs.getString("description"),
                rs.getDouble("price"),
                rs.getString("cover_image"),
                rs.getString("file_path"),
                rs.getString("publisher"),
                rs.getInt("publication_year"),
                rs.getInt("stock_quantity"),
                rs.getString("language"),
                rs.getInt("series_id"),
                rs.getInt("volume_number"),
                rs.getString("book_type"),
                rs.getInt("created_by")
        );
        book.setAuthorName(rs.getString("authorName"));
        book.setCategories(rs.getString("categories"));
        return book;
    }

    // Phương thức này sẽ tìm các sách có cùng thể loại hoặc cùng tác giả với sách hiện tại.
    public List<Book> getSimilarBooks(int bookId) throws ClassNotFoundException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT TOP 4 b.*, a.name AS authorName, "
                + "   (SELECT STRING_AGG(c.name, ', ') FROM BookCategory bc "
                + "    JOIN Category c ON bc.category_id = c.category_id WHERE bc.book_id = b.book_id) AS categories "
                + "FROM Books b "
                + "JOIN Author a ON b.author_id = a.author_id "
                + "WHERE b.book_id <> ? "
                + "AND (b.author_id = (SELECT author_id FROM Books WHERE book_id = ?) "
                + "     OR b.book_id IN (SELECT book_id FROM BookCategory WHERE category_id IN "
                + "        (SELECT category_id FROM BookCategory WHERE book_id = ?))) "
                + "ORDER BY NEWID()";

        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, bookId);
            ps.setInt(2, bookId);
            ps.setInt(3, bookId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Book book = extractBookFromResultSet(rs);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return books;
    }

    // Get categories for a specific book
    public String getCategoriesForBook(int bookId) throws ClassNotFoundException {
        String categories = "";
        String sql = "SELECT STRING_AGG(c.name, ', ') AS categories "
                + "FROM BookCategory bc "
                + "JOIN Category c ON bc.category_id = c.category_id "
                + "WHERE bc.book_id = ?";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, bookId);
            rs = ps.executeQuery();
            if (rs.next()) {
                categories = rs.getString("categories");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return categories;
    }

    public List<Book> getBooksByCategoryAndFilter(String categoryId, String filter, int page, int booksPerPage) {
        List<Book> books = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT b.*, a.name AS authorName, s.name AS seriesName, "
                + "(SELECT STRING_AGG(c.name, ', ') FROM BookCategory bc "
                + "JOIN Category c ON bc.category_id = c.category_id WHERE bc.book_id = b.book_id) AS categories "
                + "FROM Books b "
                + "JOIN Author a ON b.author_id = a.author_id "
                + "LEFT JOIN BookSeries s ON b.series_id = s.series_id "
        );

        // Nếu có category, thêm điều kiện lọc
        if (categoryId != null && !categoryId.isEmpty()) {
            sql.append(" JOIN BookCategory bc ON b.book_id = bc.book_id WHERE bc.category_id = ? ");
        } else {
            sql.append(" WHERE 1=1 ");
        }

        // Nếu có bộ lọc, thêm điều kiện sắp xếp
        if ("priceAsc".equals(filter)) {
            sql.append(" ORDER BY b.price ASC ");
        } else if ("priceDesc".equals(filter)) {
            sql.append(" ORDER BY b.price DESC ");
        } else if ("nameAsc".equals(filter)) {
            sql.append(" ORDER BY b.title ASC ");
        } else if ("nameDesc".equals(filter)) {
            sql.append(" ORDER BY b.title DESC ");
        } else {
            sql.append(" ORDER BY b.book_id DESC "); // Mặc định sắp xếp theo ID
        }

        // Phân trang
        sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ");

        try {
            ps = conn.prepareStatement(sql.toString());
            int paramIndex = 1;

            if (categoryId != null && !categoryId.isEmpty()) {
                ps.setInt(paramIndex++, Integer.parseInt(categoryId));
            }

            ps.setInt(paramIndex++, (page - 1) * booksPerPage);
            ps.setInt(paramIndex++, booksPerPage);

            rs = ps.executeQuery();
            while (rs.next()) {
                books.add(extractBookFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return books;
    }

    public int getTotalBooks(String categoryId, String filter) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) AS total FROM Books b ");

        if (categoryId != null && !categoryId.isEmpty()) {
            sql.append(" JOIN BookCategory bc ON b.book_id = bc.book_id WHERE bc.category_id = ? ");
        } else {
            sql.append(" WHERE 1=1 ");
        }

        try {
            ps = conn.prepareStatement(sql.toString());
            if (categoryId != null && !categoryId.isEmpty()) {
                ps.setInt(1, Integer.parseInt(categoryId));
            }

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return 0;
    }

    // Đóng tài nguyên
    public void closeResources() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isBookExists(String title, int auID) throws ClassNotFoundException {
        String query = "SELECT COUNT(*) FROM Books WHERE title = ? AND author_id=?";

        try ( Connection conn = DBConnect.connect();  PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, title);
            pstmt.setInt(2, auID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu COUNT > 0 thì sách đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isAuthorExists(String authorName) throws ClassNotFoundException {
        String query = "SELECT COUNT(*) FROM Author WHERE name = ? "; // Giả sử có cột isDelete để kiểm tra xóa mềm

        try ( Connection conn = DBConnect.connect();  PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, authorName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu COUNT > 0 thì tác giả đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isCategoryExists(String categoryName) throws ClassNotFoundException {
        String query = "SELECT COUNT(*) FROM Category WHERE name = ? AND isDelete = 0"; // Giả sử có cột isDelete để kiểm tra xóa mềm

        try ( Connection conn = DBConnect.connect();  PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, categoryName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu COUNT > 0 thì danh mục đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteBook(int bookID) throws ClassNotFoundException {
        String query = "UPDATE Books SET isDelete = 1 WHERE book_id = ?";

        try ( Connection conn = DBConnect.connect();  PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, bookID);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getAuthorIdByName(String authorName) throws ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnect.connect(); // Kết nối đến cơ sở dữ liệu

            // Câu lệnh SQL để lấy author_id dựa trên tên tác giả
            String query = "SELECT author_id FROM Author WHERE name = ?"; // Giả sử có cột isDelete để kiểm tra xóa mềm
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, authorName);

            // Thực thi câu lệnh SQL
            rs = pstmt.executeQuery();

            // Kiểm tra kết quả
            if (rs.next()) {
                return rs.getInt("author_id"); // Trả về author_id nếu tìm thấy
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng các tài nguyên
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1; // Trả về -1 nếu không tìm thấy hoặc có lỗi xảy ra
    }

    public int addAuthor(String authorName) throws ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnect.connect(); // Kết nối đến cơ sở dữ liệu

            // Kiểm tra xem tác giả đã tồn tại chưa
            if (isAuthorExists(authorName)) {
                // Nếu tác giả đã tồn tại, lấy author_id
                String getAuthorIdQuery = "SELECT author_id FROM Author WHERE name = ?";
                pstmt = conn.prepareStatement(getAuthorIdQuery);
                pstmt.setString(1, authorName);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    return rs.getInt("author_id"); // Trả về author_id nếu tác giả đã tồn tại
                }
            } else {
                // Nếu tác giả chưa tồn tại, thêm tác giả mới vào bảng Author
                String insertAuthorQuery = "INSERT INTO Author (name) VALUES (?)";
                pstmt = conn.prepareStatement(insertAuthorQuery, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, authorName);
                pstmt.executeUpdate();

                // Lấy author_id vừa được tạo
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // Trả về author_id mới
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng các tài nguyên
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1; // Trả về -1 nếu có lỗi xảy ra
    }

    public int addCategory(String categoryName) throws ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnect.connect(); // Kết nối đến cơ sở dữ liệu

            // Kiểm tra xem danh mục đã tồn tại chưa
            if (isCategoryExists(categoryName)) {
                // Nếu danh mục đã tồn tại, lấy category_id
                String getCategoryIdQuery = "SELECT category_id FROM Category WHERE name = ? ";
                pstmt = conn.prepareStatement(getCategoryIdQuery);
                pstmt.setString(1, categoryName);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    return rs.getInt("category_id"); // Trả về category_id nếu danh mục đã tồn tại
                }
            } else {
                // Nếu danh mục chưa tồn tại, thêm danh mục mới vào bảng Category
                String insertCategoryQuery = "INSERT INTO Category (name) VALUES (?)";
                pstmt = conn.prepareStatement(insertCategoryQuery, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, categoryName);
                pstmt.executeUpdate();

                // Lấy category_id vừa được tạo
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // Trả về category_id mới
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng các tài nguyên
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1; // Trả về -1 nếu có lỗi xảy ra
    }

    public int addBook(Book book, int authorId) throws ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnect.connect(); // Kết nối đến cơ sở dữ liệu

            // Câu lệnh SQL để thêm sách vào bảng Books và trả về book_id
            String insertBookQuery = "INSERT INTO Books (title, author_id, description, price, cover_image, file_path, book_type) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(insertBookQuery, PreparedStatement.RETURN_GENERATED_KEYS);

            // Thiết lập các tham số cho câu lệnh SQL từ đối tượng Book
            pstmt.setString(1, book.getTitle());
            pstmt.setInt(2, authorId);
            pstmt.setString(3, book.getDescription());
            pstmt.setDouble(4, book.getPrice());
            pstmt.setString(5, book.getCoverImage());
            pstmt.setString(6, book.getFilePath());
            pstmt.setString(7, book.getBookType());

            // Thực thi câu lệnh SQL
            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                // Lấy book_id vừa được tạo
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // Trả về book_id
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng các tài nguyên
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1; // Trả về -1 nếu có lỗi xảy ra
    }

    public boolean addBookCategory(int bookId, int categoryId) throws ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnect.connect(); // Kết nối đến cơ sở dữ liệu

            // Câu lệnh SQL để thêm bản ghi vào bảng BookCategory
            String insertQuery = "INSERT INTO BookCategory (book_id, category_id) VALUES (?, ?)";
            pstmt = conn.prepareStatement(insertQuery);

            // Thiết lập các tham số cho câu lệnh SQL
            pstmt.setInt(1, bookId);
            pstmt.setInt(2, categoryId);

            // Thực thi câu lệnh SQL
            int rowsInserted = pstmt.executeUpdate();

            // Trả về true nếu thêm thành công
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        } finally {
            // Đóng các tài nguyên
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Book getBookByIdForAdmin(int bookId) throws ClassNotFoundException {
        Book book = null;
        String sql = "SELECT b.*, a.name AS authorName, bs.name AS seriesName, "
                + "STRING_AGG(c.name, ', ') AS categories "
                + "FROM Books b "
                + "LEFT JOIN Author a ON b.author_id = a.author_id "
                + "LEFT JOIN BookSeries bs ON b.series_id = bs.series_id "
                + "LEFT JOIN BookCategory bc ON b.book_id = bc.book_id "
                + "LEFT JOIN Category c ON bc.category_id = c.category_id "
                + "WHERE b.book_id = ? "
                + "GROUP BY b.book_id, b.title, b.author_id, b.description, b.price, "
                + "b.cover_image, b.file_path, b.publisher, b.publication_year, "
                + "b.stock_quantity, b.language, b.series_id, b.volume_number, "
                + "b.book_type, b.created_by, b.isDelete, a.name, bs.name";
        try ( Connection conn = DBConnect.connect();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            System.out.println("Executing query for book ID: " + bookId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Book found in database: " + rs.getString("title"));
                book = new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getInt("author_id"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("cover_image"),
                        rs.getString("file_path"),
                        rs.getString("publisher"),
                        rs.getInt("publication_year"),
                        rs.getInt("stock_quantity"),
                        rs.getString("language"),
                        rs.getInt("series_id"),
                        rs.getInt("volume_number"),
                        rs.getString("book_type"),
                        rs.getInt("created_by"),
                        rs.getBoolean("isDelete"),
                        rs.getString("authorName"),
                        rs.getString("seriesName"),
                        rs.getString("categories")
                );
            } else {
                System.out.println("No book found with ID: " + bookId);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return book;

    }
}

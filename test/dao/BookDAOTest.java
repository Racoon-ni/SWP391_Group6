package dao;

import entity.Book;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ThinhLVCE181726 <your.name at your.org>
 */
public class BookDAOTest {

    public BookDAOTest() {
    }

    @Test
    public void testSearchBook() throws Exception {
        BookDAO dao = new BookDAO();

        // 1. Thêm 1 quyển "Java Programming"
        Book book = new Book();
        book.setTitle("Java Programming");
        book.setPrice(20.0);
        dao.addBook(book);

        // 2. Gọi searchBook("Java")
        List<Book> result = dao.searchBook("Java");
        assertFalse("Tìm 'Java' => list không rỗng", result.isEmpty());

        // 3. Kiểm tra tiêu đề
        boolean found = false;
        for (Book b : result) {
            if (b.getTitle().contains("Java")) {
                found = true;
                break;
            }
        }
        assertTrue("Phải có book title chứa 'Java'", found);

        // 4. Tìm từ khoá "NoSuchKey123" => expect rỗng
        List<Book> notFound = dao.searchBook("NoSuchKey123");
        assertTrue("Không có => list rỗng", notFound.isEmpty());
    }

    @Test
    public void testAddBook() throws Exception {
        BookDAO dao = new BookDAO();

        // [1] Tạo Book hợp lệ
        Book validBook = new Book();
        validBook.setTitle("UnitTest Book");
        validBook.setAuthorName("Test Author");
        validBook.setDescription("UnitTestDemo");
        validBook.setPrice(10.0);
        validBook.setCoverImage("cover.jpg");
        validBook.setFilePath("ebook.pdf");
 

        // [2] Gọi hàm addBook
        boolean result = dao.addBook(validBook);

        // [3] Kiểm tra kết quả
        assertTrue("Thêm sách hợp lệ => kết quả phải true", result);

        // [4] Kiểm tra DB có thật sự lưu sách? (VD: searchBook)
        List<Book> searchResults = dao.searchBook("UnitTest Book");
        boolean found = false;
        for (Book b : searchResults) {
            if ("UnitTest Book".equals(b.getTitle())) {
                found = true;
                break;
            }
        }
        assertTrue("Phải tìm thấy sách 'UnitTest Book' trong DB", found);

    }

}

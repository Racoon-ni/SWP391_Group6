/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookDAO;
import entity.Book;
import entity.Category;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Oanh Nguyen
 */
@WebServlet(name = "AddBookController", urlPatterns = {"/AddBookController"})
public class AddBookController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws SQLException,ServletException, IOException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            // Get data from the form
            String title = request.getParameter("title");
        String author = request.getParameter("author");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        String coverImage = request.getParameter("coverImage");
        String filePath = request.getParameter("filePath");
        String category = request.getParameter("category");
        String bookType = request.getParameter("bookType");
//            String category = request.getParameter("category");
            Book book = new Book(0, title, author, description, price, coverImage, filePath, category, bookType);
            BookDAO bookDAO = new BookDAO();
           
            boolean isExists = bookDAO.isBookExists(title,bookDAO.getAuthorIdByName(author));
            if (isExists) {
                
                request.setAttribute("error", "Duplicate");
                request.getRequestDispatcher("AddBook.jsp").forward(request, response);
            } 
            int idAuthor= bookDAO.addAuthor(author);
            
            int idCategory= bookDAO.addCategory(category);
            int isAdded = bookDAO.addBook(book,idAuthor);
            boolean add= bookDAO.addBookCategory(isAdded, idCategory);
            if (add) {
                request.setAttribute("error", "Add successful");
                request.getRequestDispatcher("AddBook.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Failed to add product");
                request.setAttribute("list", book);
                request.getRequestDispatcher("AddBook.jsp").forward(request, response);
            }

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

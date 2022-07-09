/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.tblbook;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import phucln.utils.DBHelpers;

/**
 *
 * @author ASUS
 */
public class TblBookDAO implements Serializable {

    public TblBookDTO getBookByID(String bookID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblBookDTO result = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "SELECT title, image, description, price, author, categoryID, quantity, importDate, status "
                        + "FROM tblBook "
                        + "WHERE bookID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, bookID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String title = rs.getString("title");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    Float price = rs.getFloat("price");
                    String author = rs.getString("author");
                    String categoryID = rs.getString("categoryID");
                    int quantity = rs.getInt("quantity");
                    Date importDate = rs.getDate("importDate");
                    String status = rs.getString("status");
                    result = new TblBookDTO(bookID, title, image, description, price, author, categoryID, quantity, importDate, status);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public boolean createNewBook(String bookID, String title, String image, String description, float price, String author, String categoryID, int quantity) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "INSERT INTO tblBook (bookID, title, image, description, price, author, categoryID, quantity, importDate, status) "
                        + "VALUES (?,?,?,?,?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, bookID);
                stm.setString(2, title);
                stm.setString(3, image);
                stm.setString(4, description);
                stm.setFloat(5, price);
                stm.setString(6, author);
                stm.setString(7, categoryID);
                stm.setInt(8, quantity);
                long time = System.currentTimeMillis();
                Date today = new Date(time);
                stm.setDate(9, today);
                stm.setString(10, "Active");
                int rowsOfEffected = stm.executeUpdate();
                if (rowsOfEffected == 1) {
                    result = true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    List<TblBookDTO> listBooks;

    public List<TblBookDTO> getListBooks() {
        return listBooks;
    }

    public void searchBooksByAdmin(String bookTitle, String bookCategoryID, float priceFrom, int searchQuantity, float priceTo, String statusSearch) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "SELECT bookID, title, image, description, price, author, categoryID, quantity, importDate, status "
                        + "FROM tblBook "
                        + "WHERE title LIKE ? AND categoryID LIKE ? AND status LIKE ? AND quantity > ? AND price >= ? AND price <= ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + bookTitle + "%");
                stm.setString(2, "%" + bookCategoryID + "%");
                stm.setString(3, "%" + statusSearch + "%");
                stm.setInt(4, searchQuantity);
                stm.setFloat(5, priceFrom);
                stm.setFloat(6, priceTo);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (listBooks == null) {
                        listBooks = new ArrayList<>();
                    }
                    String bookID = rs.getString("bookID");
                    String title = rs.getString("title");
                    String image = rs.getString("image");
                    image = "./Img/" + image;
                    String description = rs.getString("description");
                    float price = rs.getFloat("price");
                    String author = rs.getString("author");
                    String txtCategoryID = rs.getString("categoryID");
                    int quantity = rs.getInt("quantity");
                    Date importDate = rs.getDate("importDate");
                    String status = rs.getString("status");
                    TblBookDTO bookDTO = new TblBookDTO(bookID, title, image, description, price, author, txtCategoryID, quantity, importDate, status);
                    listBooks.add(bookDTO);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public void searchBooks(String bookTitle, String bookCategoryID, float priceFrom, float priceTo) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "SELECT bookID, title, image, description, price, author, categoryID, quantity, importDate, status "
                        + "FROM tblBook "
                        + "WHERE title LIKE ? AND categoryID LIKE ? AND status = ? AND quantity > ? AND price >= ? AND price <= ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + bookTitle + "%");
                stm.setString(2, "%" + bookCategoryID + "%");
                stm.setString(3, "Active");
                stm.setInt(4, 0);
                stm.setFloat(5, priceFrom);
                stm.setFloat(6, priceTo);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (listBooks == null) {
                        listBooks = new ArrayList<>();
                    }
                    String bookID = rs.getString("bookID");
                    String title = rs.getString("title");
                    String image = rs.getString("image");
                    image = "./Img/" + image;
                    String description = rs.getString("description");
                    float price = rs.getFloat("price");
                    String author = rs.getString("author");
                    String txtCategoryID = rs.getString("categoryID");
                    int quantity = rs.getInt("quantity");
                    Date importDate = rs.getDate("importDate");
                    String status = rs.getString("status");
                    TblBookDTO bookDTO = new TblBookDTO(bookID, title, image, description, price, author, txtCategoryID, quantity, importDate, status);
                    listBooks.add(bookDTO);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public boolean deleteBook(String bookID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "UPDATE tblBook "
                        + "SET status = ? "
                        + "WHERE bookID = ?";
                stm = con.prepareCall(sql);
                stm.setString(1, "Inactive");
                stm.setString(2, bookID);
                int rowsOfEffected = stm.executeUpdate();
                if (rowsOfEffected == 1) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public boolean updateBookByBookID(String bookID, String bookTitle, String image, String description,
            float price, String author, String categoryID, int quantity, String status) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "UPDATE tblBook "
                        + "SET title = ?, image = ?, description = ?, price = ?, author = ?, categoryID = ?, quantity = ?, status = ?, importDate = ? "
                        + "WHERE bookID = ?";
                stm = con.prepareCall(sql);
                stm.setString(1, bookTitle);
                stm.setString(2, image);
                stm.setString(3, description);
                stm.setFloat(4, price);
                stm.setString(5, author);
                stm.setString(6, categoryID);
                stm.setInt(7, quantity);
                stm.setString(8, status);
                Date today = new Date(System.currentTimeMillis());
                stm.setDate(9, today);
                stm.setString(10, bookID);
                int rowsOfEffected = stm.executeUpdate();
                if (rowsOfEffected == 1) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public boolean updateBookQuantityByBookID(String bookID, int quantity) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "UPDATE tblBook "
                        + "SET quantity = ? "
                        + "WHERE bookID = ?";
                stm = con.prepareCall(sql);
                stm.setInt(1, quantity);
                stm.setString(2, bookID);
                int rowsOfEffected = stm.executeUpdate();
                if (rowsOfEffected == 1) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
}

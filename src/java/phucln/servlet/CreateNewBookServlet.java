/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import phucln.tblaccount.TblAccountDTO;
import phucln.tblbook.CreateNewBookError;
import phucln.tblbook.TblBookDAO;
import phucln.tblbook.TblBookDTO;
import phucln.tblcategory.TblCategoryDAO;
import phucln.tblcategory.TblCategoryDTO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "CreateNewBookServlet", urlPatterns = {"/CreateNewBookServlet"})
public class CreateNewBookServlet extends HttpServlet {

    private static Logger log;
    private final String SEARCH_PAGE = "LoadCategoryServlet";
    private final String INSERT_NEW_BOOK_PAGE = "LoadCategoryServlet";
    private final String SUCCESS_PAGE = "success.html";

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        log = Logger.getLogger(this.getClass().getName());
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static CreateNewBookError checkParameter(String bookID, String title, String image, String description, String price,
            String author, String quantity) {
        boolean flag = false;
        CreateNewBookError error = new CreateNewBookError();

        if (bookID.length() <= 0 || bookID.length() > 20) {
            flag = true;
            error.setBookIDLengthError("Book ID must > 0 and <= 20 characters");
        }
        try {
            TblBookDAO bookDAO = new TblBookDAO();
            TblBookDTO bookDTO = bookDAO.getBookByID(bookID);
            if (bookDTO != null) {
                flag = true;
                error.setBookIDDuplicateError("Book ID is duplicated");
            }
        } catch (NamingException e) {
            log.error(e);
        } catch (SQLException e) {
            log.error(e);
        }

        if (title.length() > 50 || title.length() == 0) {
            flag = true;
            error.setTitleLengthError("Title must > 0 and <= 50 characters");
        }
        if (image.length() <= 0 || image.length() > 50) {
            flag = true;
            error.setImageEmptyError("Image must > 0 and <= 50 characters");
        }
        if (description.length() <= 0 || description.length() > 30) {
            flag = true;
            error.setDescriptionLengthError("Description must > 0 and <= 30 characters");
        }
        if (price.length() <= 0) {
            flag = true;
            error.setPriceEmtpyError("Price must not empty");
        }

        try {
            float bookPrice = Float.parseFloat(price);
            if (bookPrice <= 0) {
                flag = true;
                error.setPriceNegativeError("Price must > 0");
            }
        } catch (NumberFormatException e) {
            flag = true;
            error.setPriceFormatError("Price must be number only");
        }

        if (author.length() <= 0 || author.length() > 30) {
            flag = true;
            error.setAuthorLengthError("Author must > 0 and <= 30 characters");
        }
        try {
            int authorInt = Integer.parseInt(author);
            flag = true;
            error.setAuthorFormatError("Author must not be number only");
        } catch (NumberFormatException e) {
        }

        if (quantity.length() <= 0) {
            flag = true;
            error.setQuantityLengthError("Quantity must not empty");
        }

        try {
            int bookQuantity = Integer.parseInt(quantity);
            if (bookQuantity <= 0) {
                flag = true;
                error.setQuantityNegativeError("Quantity must > 0");
            }
        } catch (NumberFormatException e) {
            flag = true;
            error.setQuantityFormatError("Quantity must be number only");
        }

        if (flag == false) {
            return null;
        }
        return error;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        String url = SEARCH_PAGE;

        String bookID = request.getParameter("txtInsertBookID");
        bookID = bookID.trim();
        String title = request.getParameter("txtInsertBookTitle");
        title = title.trim();
        String image = request.getParameter("txtInsertBookImage");
        image = image.trim();
        String description = request.getParameter("txtInsertBookDescription");
        description = description.trim();
        String price = request.getParameter("txtInsertBookPrice");
        price = price.trim();
        String author = request.getParameter("txtInsertBookAuthor");
        author = author.trim();
        String category = request.getParameter("txtInsertBookCategory");
        category = category.trim();
        String quantity = request.getParameter("txtInsertBookQuantity");
        quantity = quantity.trim();

        try {
            if (session != null) {
                TblAccountDTO account = (TblAccountDTO) session.getAttribute("account");
                String role = (String) session.getAttribute("role");
                if (account != null && role.equals("Admin")) {
                    CreateNewBookError error
                            = checkParameter(bookID, title, image, description, price, author, quantity);
                    if (error == null) {

                        File f;
                        File newFile;
                        try {
                            f = new File(image);
                            String extension = "";
                            int i = image.lastIndexOf(".");
                            if (i > 0) {
                                extension = image.substring(i + 1);
                            }
                            String realPath = this.getClass().getClassLoader().getResource("").getPath();
                            String[] pathSplit = realPath.split("build");
                            String path = pathSplit[0] + "web" + "/Img/" + f.getName();
                            newFile = new File(path);
                            BufferedImage bi = ImageIO.read(f);
                            ImageIO.write(bi, extension, newFile);
                            newFile.createNewFile();
                            TblCategoryDAO categoryDAO = new TblCategoryDAO();
                            TblCategoryDTO categoryDTO = categoryDAO.getCategoryByName(category);
                            String categoryID = categoryDTO.getCategoryID();
                            float bookPrice = Float.parseFloat(price);
                            int bookQuantity = Integer.parseInt(quantity);
                            TblBookDAO bookDAO = new TblBookDAO();
                            boolean result = bookDAO.createNewBook(bookID, title, newFile.getName(), description, bookPrice, author, categoryID, bookQuantity);

                            if (result) {
                                url = SUCCESS_PAGE;
                            }
                        } catch (IOException e) {
                            log.error(e);
                        } catch (SQLException e) {
                            log.error(e);
                        } catch (NamingException e) {
                            log.error(e);
                        } catch (NumberFormatException e) {
                            log.error(e);
                        }
                    } else {
                        request.setAttribute("createNewError", error);
                        url = INSERT_NEW_BOOK_PAGE;
                    }
                }
            }
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
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
            throws ServletException, IOException {
        processRequest(request, response);
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
            throws ServletException, IOException {
        processRequest(request, response);
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

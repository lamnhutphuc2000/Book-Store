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
import phucln.tblbook.TblBookDAO;
import phucln.tblbook.UpdateBookError;
import phucln.tblcategory.TblCategoryDAO;
import phucln.tblcategory.TblCategoryDTO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "UpdateBookServlet", urlPatterns = {"/UpdateBookServlet"})
public class UpdateBookServlet extends HttpServlet {

    private static Logger log;
    private final String ERROR_PAGE = "error.html";
    private final String SEARCH_CONTROLLER = "SearchBookServlet";

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
    private static UpdateBookError checkParameter(String title, String description, String price,
            String author, String quantity) {
        boolean flag = false;
        UpdateBookError error = new UpdateBookError();

        if (title.length() > 50 || title.length() == 0) {
            flag = true;
            error.setTitleLengthError("Title must > 0 and <= 50 characters");
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
        String bookUpdateID = request.getParameter("txtUpdateBookID");
        String bookUpdateTitle = request.getParameter("txtUpdateBookTitle");
        bookUpdateTitle = bookUpdateTitle.trim();
        String bookUpdateImage = request.getParameter("txtUpdateBookImage");
        bookUpdateImage = bookUpdateImage.trim();
        String bookOldImage = request.getParameter("txtBookImage");
        bookOldImage = bookOldImage.trim();
        if (bookUpdateImage.equals("")) {
            bookUpdateImage = bookOldImage;
        }
        String bookUpdateDescription = request.getParameter("txtUpdateBookDescription");
        bookUpdateDescription = bookUpdateDescription.trim();
        String bookUpdatePrice = request.getParameter("txtUpdateBookPrice");
        bookUpdatePrice = bookUpdatePrice.trim();
        String bookUpdateAuthor = request.getParameter("txtUpdateBookAuthor");
        bookUpdateAuthor = bookUpdateAuthor.trim();
        String bookUpdateCategory = request.getParameter("txtUpdateBookCategory");
        bookUpdateCategory = bookUpdateCategory.trim();
        String bookUpdateQuantity = request.getParameter("txtUpdateBookQuantity");
        bookUpdateQuantity = bookUpdateQuantity.trim();
        String bookUpdateStauts = request.getParameter("txtUpdateBookStatus");
        bookUpdateStauts = bookUpdateStauts.trim();
        String url = ERROR_PAGE;
        try {
            if (session != null) {
                String role = (String) session.getAttribute("role");
                TblAccountDTO account = (TblAccountDTO) session.getAttribute("account");
                if (role != null && account != null) {
                    if (role.equals("Admin")) {
                        UpdateBookError error
                                = checkParameter(bookUpdateTitle, bookUpdateDescription, bookUpdatePrice, bookUpdateAuthor, bookUpdateQuantity);
                        if (error == null) {
                            if (!bookOldImage.equals(bookUpdateImage)) {
                                File f;
                                File newFile;
                                f = new File(bookUpdateImage);
                                String extension = "";
                                int i = bookUpdateImage.lastIndexOf(".");
                                if (i > 0) {
                                    extension = bookUpdateImage.substring(i + 1);
                                }
                                String realPath = this.getClass().getClassLoader().getResource("").getPath();
                                String[] pathSplit = realPath.split("build");
                                String path = pathSplit[0] + "web" + "/Img/" + f.getName();
                                newFile = new File(path);
                                BufferedImage bi = ImageIO.read(f);
                                ImageIO.write(bi, extension, newFile);
                                newFile.createNewFile();
                                bookUpdateImage = newFile.getName();
                            } else {
                                String[] imageSplit = bookUpdateImage.split("/");
                                bookUpdateImage = imageSplit[2];
                            }

                            TblBookDAO bookDAO = new TblBookDAO();
                            float price = Float.parseFloat(bookUpdatePrice);
                            int quantity = Integer.parseInt(bookUpdateQuantity);
                            TblCategoryDAO categoryDAO = new TblCategoryDAO();
                            TblCategoryDTO categoryDTO = categoryDAO.getCategoryByName(bookUpdateCategory);
                            boolean result = bookDAO.updateBookByBookID(bookUpdateID, bookUpdateTitle, bookUpdateImage, bookUpdateDescription, price, bookUpdateAuthor, categoryDTO.getCategoryID(), quantity, bookUpdateStauts);
                            if (result) {
                                url = SEARCH_CONTROLLER;
                            }
                        } else {
                            error.setUpdateBookIDError(bookUpdateID);
                            request.setAttribute("updateBookError", error);
                            url = SEARCH_CONTROLLER;
                        }
                    }
                }
            }
        } catch (NamingException e) {
            log.error(e);
        } catch (SQLException e) {
            log.error(e);
        } catch (NumberFormatException e) {
            log.error(e);
        } catch (IOException e) {
            log.error(e);
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

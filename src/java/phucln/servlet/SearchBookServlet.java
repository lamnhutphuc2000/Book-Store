/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import phucln.tblbook.SearchBookError;
import phucln.tblbook.TblBookDAO;
import phucln.tblbook.TblBookDTO;
import phucln.tblcategory.TblCategoryDAO;
import phucln.tblcategory.TblCategoryDTO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "SearchBookServlet", urlPatterns = {"/SearchBookServlet"})
public class SearchBookServlet extends HttpServlet {

    private static Logger log = null;
    private final String ERROR_PAGE = "error.html";
    private final String MAIN_PAGE = "search.jsp";

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
    private static SearchBookError checkParameter(String title, String priceFrom, String priceTo) {
        boolean flag = false;
        SearchBookError error = new SearchBookError();

        float bookPriceFrom = 1, bookPriceTo = 10000;

        try {
            if (priceFrom.length() > 0) {
                bookPriceFrom = Float.parseFloat(priceFrom);
                if (bookPriceFrom <= 0) {
                    flag = true;
                    error.setPriceFromNegativeError("Price from must > 0");
                }
            }

        } catch (NumberFormatException e) {
            flag = true;
            error.setPriceFromFormatError("Price from must be number only");
        }

        try {
            if (priceTo.length() > 0) {
                bookPriceTo = Float.parseFloat(priceTo);
                if (bookPriceTo <= 0) {
                    flag = true;
                    error.setPriceToNegativeError("Price to must > 0");
                }
            }

        } catch (NumberFormatException e) {
            flag = true;
            error.setPriceToFormatError("Price to must be number only");
        }

        if (flag == false) {
            if (bookPriceFrom >= bookPriceTo) {
                flag = true;
                error.setPriceToSmallerPriceFrom("Price to must > price from");
            }
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
        String title = request.getParameter("txtBookName");
        title = title.trim();
        String priceFrom = request.getParameter("txtBookPriceFrom");
        priceFrom = priceFrom.trim();
        String priceTo = request.getParameter("txtBookPriceTo");
        priceTo = priceTo.trim();
        String category = request.getParameter("txtBookCategory");
        category = category.trim();
        String url = ERROR_PAGE;
        try {
            SearchBookError searchBookError = checkParameter(title, priceFrom, priceTo);
            if (searchBookError == null) {
                float bookPriceFrom = 1;
                if (priceFrom.length() > 0) {
                    bookPriceFrom = Float.parseFloat(priceFrom);
                }
                float bookPriceTo = 1000000;
                if (priceFrom.length() > 0) {
                    bookPriceTo = Float.parseFloat(priceTo);
                }
                String bookCategory = "";
                if (!category.equals("All")) {
                    TblCategoryDAO categoryDAO = new TblCategoryDAO();
                    TblCategoryDTO categoryDTO = categoryDAO.getCategoryByName(category);
                    bookCategory = categoryDTO.getCategoryID();
                }

                TblBookDAO bookDAO = new TblBookDAO();
                HttpSession session = request.getSession(false);
                if (session != null) {
                    String role = (String) session.getAttribute("role");
                    if (role != null) {
                        if (role.equals("Admin")) {
                            int quantity = -100;
                            String statusSearch = "";
                            bookDAO.searchBooksByAdmin(title, bookCategory, bookPriceFrom, quantity, bookPriceTo, statusSearch);
                        } else {
                            bookDAO.searchBooks(title, bookCategory, bookPriceFrom, bookPriceTo);
                        }
                    } else {
                        bookDAO.searchBooks(title, bookCategory, bookPriceFrom, bookPriceTo);
                    }
                } else {
                    bookDAO.searchBooks(title, bookCategory, bookPriceFrom, bookPriceTo);
                }

                List<TblBookDTO> listBooks = bookDAO.getListBooks();
                request.setAttribute("listBooks", listBooks);
                url = MAIN_PAGE;
            } else {
                request.setAttribute("searchBookError", searchBookError);
                url = MAIN_PAGE;
            }
            TblCategoryDAO categoryDAO = new TblCategoryDAO();
            categoryDAO.loadAllCategory();
            List<TblCategoryDTO> listCategory = categoryDAO.getListCategory();
            request.setAttribute("listCategory", listCategory);
        } catch (NamingException e) {
            log.error(e);
        } catch (SQLException e) {
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

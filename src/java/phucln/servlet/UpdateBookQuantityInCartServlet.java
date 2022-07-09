/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import phucln.cart.Cart;
import phucln.cart.UpdateBookInCartError;
import phucln.tblaccount.TblAccountDTO;
import phucln.tblbook.TblBookDAO;
import phucln.tblbook.TblBookDTO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "UpdateBookQuantityInCartServlet", urlPatterns = {"/UpdateBookQuantityInCartServlet"})
public class UpdateBookQuantityInCartServlet extends HttpServlet {

    private static Logger log;
    private final String ERROR_PAGE = "error.html";
    private final String CART_PAGE = "cartPage.jsp";

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
    private static UpdateBookInCartError checkParameter(String bookID, String quantity) {
        boolean flag = false;
        UpdateBookInCartError error = new UpdateBookInCartError();

        if (quantity.length() <= 0) {
            flag = true;
            error.setQuantityEmptyError("Quantity must not empty");
        }

        try {
            int bookQuantity = Integer.parseInt(quantity);
            if (bookQuantity <= 0) {
                flag = true;
                error.setQuantityNegativeError("Quantity must > 0");
            } else {
                TblBookDAO bookDAO = new TblBookDAO();
                TblBookDTO bookDTO = bookDAO.getBookByID(bookID);
                if (bookDTO.getQuantity() < bookQuantity || bookDTO.getStatus().equals("Inactive")) {
                    flag = true;
                    error.setBookOutOfStockError("Book " + bookDTO.getTitle() + " is out of stock");
                }
            }
        } catch (NumberFormatException e) {
            flag = true;
            error.setQuantityFormatError("Quantity must be number only");
        } catch (NamingException e) {
            log.error(e);
        } catch (SQLException e) {
            log.error(e);
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
        String url = ERROR_PAGE;
        HttpSession session = request.getSession(false);
        try {
            if (session != null) {
                String role = (String) session.getAttribute("role");
                TblAccountDTO account = (TblAccountDTO) session.getAttribute("account");
                if (role != null && account != null) {
                    if (!role.equals("Admin")) {
                        Cart cart = (Cart) session.getAttribute("cart");
                        if (cart != null) {
                            String bookID = request.getParameter("txtBookID");
                            String bookQuantity = request.getParameter("txtQuantity");

                            UpdateBookInCartError error = checkParameter(bookID, bookQuantity);
                            if (error == null) {
                                int quantity = Integer.parseInt(bookQuantity);
                                cart.updateBookInCart(bookID, quantity);
                                session.setAttribute("cart", cart);
                                url = CART_PAGE;
                            } else {
                                error.setBookID(bookID);
                                request.setAttribute("updateQuantityError", error);
                                url = CART_PAGE;
                            }
                        }
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
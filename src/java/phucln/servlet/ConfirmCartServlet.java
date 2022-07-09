/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import phucln.cart.BookInCart;
import phucln.cart.Cart;
import phucln.tblorder.CheckoutError;
import phucln.tblorder.TblOrderDAO;
import phucln.tblaccount.TblAccountDTO;
import phucln.tblbook.TblBookDAO;
import phucln.tblbook.TblBookDTO;
import phucln.tbldiscountcode.TblDiscountCodeDAO;
import phucln.tbldiscountcode.TblDiscountCodeDTO;
import phucln.tblorderdetail.TblOrderDetailDAO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "ConfirmCartServlet", urlPatterns = {"/ConfirmCartServlet"})
public class ConfirmCartServlet extends HttpServlet {

    private static Logger log;
    private final String SUCCESS_PAGE = "success.html";
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
    private static CheckoutError checkParameter(String discountID, String userID, Cart cart) {
        boolean flag = false;
        CheckoutError error = new CheckoutError();

        try {
            if (!discountID.equals("NONE")) {
                TblDiscountCodeDAO discountCodeDAO = new TblDiscountCodeDAO();
                TblDiscountCodeDTO discountCodeDTO = discountCodeDAO.getDiscountCodeByID(discountID);
                if (discountCodeDTO == null) {
                    flag = true;
                    error.setDiscountCodeNoExistError("Discount ID is not existed");
                } else {
                    if (discountCodeDTO.getDate().before(new Date(System.currentTimeMillis()))) {
                        flag = true;
                        error.setDiscountCodeIsExpiredError("Discount code is expired");
                    }
                }
            }
        } catch (NamingException e) {
            log.error(e);
        } catch (SQLException e) {
            log.error(e);
        }

        try {
            if (!discountID.equals("NONE")) {
                TblOrderDAO orderDAO = new TblOrderDAO();
                if (orderDAO.checkUserUseCode(userID, discountID)) {
                    flag = true;
                    error.setDiscountCodeAlreadyUsedError("Discount Code is already used");
                }
            }
        } catch (NamingException e) {
            log.error(e);
        } catch (SQLException e) {
            log.error(e);
        }

        try {
            ArrayList<BookInCart> listBooks = (ArrayList<BookInCart>) cart.getCart();
            for (BookInCart listBook : listBooks) {
                TblBookDAO bookDAO = new TblBookDAO();
                TblBookDTO bookDTO = bookDAO.getBookByID(listBook.getBookID());
                if (bookDTO.getQuantity() < listBook.getQuantity() || bookDTO.getStatus().equals("Inactive")) {
                    flag = true;
                    error.setBookIsOutOfStockError("Book " + listBook.getTitle() + " is out of stock");
                }
            }
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
        HttpSession session = request.getSession(false);
        String url = ERROR_PAGE;
        String totalPrice = request.getParameter("txtTotalPrice");
        String discountID = request.getParameter("txtDiscountCode");
        discountID = discountID.trim();
        if (discountID.equals("")) {
            discountID = "NONE";
        }

        try {
            if (session != null) {
                String role = (String) session.getAttribute("role");
                TblAccountDTO accountDTO = (TblAccountDTO) session.getAttribute("account");
                if (role != null && accountDTO != null) {
                    if (!role.equals("Admin")) {
                        String userID = accountDTO.getUserID();
                        Cart cart = (Cart) session.getAttribute("cart");
                        if (cart != null) {
                            CheckoutError checkoutError = checkParameter(discountID, userID, cart);
                            if (checkoutError == null) {
                                TblOrderDAO orderDAO = new TblOrderDAO();
                                int rows = orderDAO.getOrderRows();
                                String orderID = "" + rows;
                                TblDiscountCodeDAO discountCodeDAO = new TblDiscountCodeDAO();
                                TblDiscountCodeDTO discountCodeDTO = discountCodeDAO.getDiscountCodeByID(discountID);
                                float price = Float.parseFloat(totalPrice);
                                float total = price - (price * discountCodeDTO.getDiscountPercent() / 100);
                                Timestamp date = new Timestamp(System.currentTimeMillis());
                                boolean result = orderDAO.createNewOrder(orderID, total, userID, date, discountID);
                                if (result) {
                                    TblOrderDetailDAO orderDetailDAO = new TblOrderDetailDAO();
                                    ArrayList<BookInCart> listBooks = (ArrayList<BookInCart>) cart.getCart();
                                    for (BookInCart book : listBooks) {
                                        String bookID = book.getBookID();
                                        String bookTitle = book.getTitle();
                                        int quantity = book.getQuantity();
                                        float bookPrice = book.getPrice();
                                        if (!orderDetailDAO.createNewOrderDetail(orderID, bookID, bookTitle, quantity, bookPrice)) {
                                            result = false;
                                        } else {
                                            TblBookDAO bookDAO = new TblBookDAO();
                                            TblBookDTO bookDTO = bookDAO.getBookByID(bookID);
                                            bookDAO.updateBookQuantityByBookID(bookID, bookDTO.getQuantity() - quantity);
                                        }
                                    }
                                    if (result) {
                                        session.removeAttribute("cart");
                                        url = SUCCESS_PAGE;
                                    }
                                }
                            } else {
                                request.setAttribute("checkoutError", checkoutError);
                                url = CART_PAGE;
                            }
                        }

                    }
                }
            }
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

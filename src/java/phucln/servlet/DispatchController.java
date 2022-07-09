/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "DispatchController", urlPatterns = {"/DispatchController"})
public class DispatchController extends HttpServlet {

    private final String LOAD_CATEGORY_CONTROLLER = "LoadCategoryServlet";
    private final String LOGIN_CONTROLLER = "LoginServlet";
    private final String LOGOUT_CONTROLLER = "LogoutServlet";
    private final String CREATE_NEW_BOOK_CONTROLLER = "CreateNewBookServlet";
    private final String SEARCH_BOOK_CONTROLLER = "SearchBookServlet";
    private final String DELETE_BOOK_CONTROLLER = "DeleteBookServlet";
    private final String UPDATE_BOOK_CONTROLLER = "UpdateBookServlet";
    private final String ADD_BOOK_TO_CART_CONTROLLER = "AddBookToCartServlet";
    private final String VIEW_CART_PAGE = "cartPage.jsp";
    private final String UPDATE_BOOK_QUANTITY_IN_CART_CONTROLLER = "UpdateBookQuantityInCartServlet";
    private final String REMOVE_BOOK_IN_CART_CONTROLLER = "RemoveBookInCartServlet";
    private final String CREATE_DISCOUNT_CODE_PAGE = "createDiscountCodePage.jsp";
    private final String CREATE_DISCOUNT_CODE_CONTROLLER = "CreateDiscountCodeServlet";
    private final String CONFIRM_CART_CONTROLLER = "ConfirmCartServlet";
    private final String LOAD_SHOPPING_HISTORY_CONTROLLER = "LoadShoppingHistoryServlet";

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String button = request.getParameter("btAction");
        String url = LOAD_CATEGORY_CONTROLLER;
        try {
            if (button == null) {
                button = "";
            }
            if (button.equals("Log in")) {
                url = LOGIN_CONTROLLER;
            }
            if (button.equals("Log out")) {
                url = LOGOUT_CONTROLLER;
            }
            if (button.equals("Insert new book")) {
                url = LOAD_CATEGORY_CONTROLLER;
            }
            if (button.equals("Create new book")) {
                url = CREATE_NEW_BOOK_CONTROLLER;
            }
            if (button.equals("Search Book")) {
                url = SEARCH_BOOK_CONTROLLER;
            }
            if (button.equals("Delete this book")) {
                url = DELETE_BOOK_CONTROLLER;
            }
            if (button.equals("Update this book")) {
                url = UPDATE_BOOK_CONTROLLER;
            }
            if (button.equals("Add book to cart")) {
                url = ADD_BOOK_TO_CART_CONTROLLER;
            }
            if (button.equals("View cart")) {
                url = VIEW_CART_PAGE;
            }
            if (button.equals("Update quantity")) {
                url = UPDATE_BOOK_QUANTITY_IN_CART_CONTROLLER;
            }
            if (button.equals("Remove this boook")) {
                url = REMOVE_BOOK_IN_CART_CONTROLLER;
            }
            if (button.equals("Create discount code")) {
                url = CREATE_DISCOUNT_CODE_PAGE;
            }
            if (button.equals("Create")) {
                url = CREATE_DISCOUNT_CODE_CONTROLLER;
            }
            if (button.equals("Confirm")) {
                url = CONFIRM_CART_CONTROLLER;
            }
            if (button.equals("Shopping History")) {
                url = LOAD_SHOPPING_HISTORY_CONTROLLER;
            }
            if (button.equals("Search history")) {
                url = LOAD_SHOPPING_HISTORY_CONTROLLER;
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

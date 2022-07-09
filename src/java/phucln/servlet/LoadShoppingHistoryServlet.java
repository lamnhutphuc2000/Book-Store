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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import phucln.tblaccount.TblAccountDTO;
import phucln.tblorder.ShoppingHistoryError;
import phucln.tblorder.TblOrderDAO;
import phucln.tblorder.TblOrderDTO;
import phucln.tblorderdetail.TblOrderDetailDAO;
import phucln.tblorderdetail.TblOrderDetailDTO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "LoadShoppingHistoryServlet", urlPatterns = {"/LoadShoppingHistoryServlet"})
public class LoadShoppingHistoryServlet extends HttpServlet {

    private Logger log;
    private final String VIEW_HISTORY_PAGE = "historyPage.jsp";
    private final String ERROR_PAGE = "error.html";

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
    private static ShoppingHistoryError checkParameter(String searchDate) {
        boolean flag = false;
        ShoppingHistoryError error = new ShoppingHistoryError();

        if (!searchDate.equals("")) {
            SimpleDateFormat sdfm = new SimpleDateFormat("MM-dd-yyyy");
            sdfm.setLenient(false);
            try {
                Date date = new Date(sdfm.parse(searchDate).getTime());
            } catch (ParseException e) {
                flag = true;
                error.setDateFormatError("Date format is MM-dd-yyyy");
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
        HttpSession session = request.getSession(false);
        String searchDate = request.getParameter("txtShoppingHistoryDate");
        if (searchDate == null) {
            searchDate = "";
        }
        searchDate = searchDate.trim();
        String searchTitle = request.getParameter("txtShoppingHistoryTitle");
        if (searchTitle == null) {
            searchTitle = "";
        }
        searchTitle = searchTitle.trim();
        String url = ERROR_PAGE;
        try {
            if (session != null) {
                String role = (String) session.getAttribute("role");
                TblAccountDTO accountDTO = (TblAccountDTO) session.getAttribute("account");
                if (role != null && accountDTO != null) {
                    if (!role.equals("Admin")) {
                        ShoppingHistoryError error = checkParameter(searchDate);
                        if (error == null) {
                            TblOrderDetailDAO orderDetailDAO = new TblOrderDetailDAO();
                            TblOrderDAO orderDAO = new TblOrderDAO();
                            List<TblOrderDTO> listOrder;
                            List<TblOrderDetailDTO> listOrderDetail;
                            if (searchDate.equals("")) {
                                orderDAO.searchOrderHistory(accountDTO.getUserID());
                                listOrder = orderDAO.getListOrder();
                                if (listOrder != null) {
                                    for (TblOrderDTO tblOrderDTO : listOrder) {
                                        orderDetailDAO.searchOrderDetails(tblOrderDTO.getOrderID(), searchTitle);
                                    }
                                }
                                listOrderDetail = orderDetailDAO.getListOrderDetail();
                            } else {
                                SimpleDateFormat sdfm = new SimpleDateFormat("MM-dd-yyyy");
                                sdfm.setLenient(false);
                                Date date = new Date(sdfm.parse(searchDate).getTime());
                                orderDAO.searchOrderHistoryBySearchDate(accountDTO.getUserID(), date.toString());
                                listOrder = orderDAO.getListOrder();
                                if (listOrder != null) {
                                    for (TblOrderDTO tblOrderDTO : listOrder) {
                                        orderDetailDAO.searchOrderDetails(tblOrderDTO.getOrderID(), searchTitle);
                                    }
                                }

                                listOrderDetail = orderDetailDAO.getListOrderDetail();
                            }
                            request.setAttribute("listOrder", listOrder);
                            request.setAttribute("listOrderDetail", listOrderDetail);
                            url = VIEW_HISTORY_PAGE;
                        } else {
                            request.setAttribute("shoppingHistoryError", error);
                            url = VIEW_HISTORY_PAGE;
                        }
                    }
                }
            }
        } catch (NamingException e) {
            log.error(e);
        } catch (ParseException e) {
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

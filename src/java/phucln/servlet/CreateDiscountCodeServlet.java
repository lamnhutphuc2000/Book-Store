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
import phucln.tbldiscountcode.CreateNewDiscountCodeError;
import phucln.tbldiscountcode.TblDiscountCodeDAO;
import phucln.tbldiscountcode.TblDiscountCodeDTO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "CreateDiscountCodeServlet", urlPatterns = {"/CreateDiscountCodeServlet"})
public class CreateDiscountCodeServlet extends HttpServlet {

    private static Logger log;
    private final String ERROR_PAGE = "error.html";
    private final String CREATE_DISCOUNT_CODE_PAGE = "createDiscountCodePage.jsp";
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
    private static CreateNewDiscountCodeError checkParameter(String discountID, String discountDate) {
        boolean flag = false;
        CreateNewDiscountCodeError error = new CreateNewDiscountCodeError();

        if (discountID.length() <= 0 || discountID.length() > 10) {
            flag = true;
            error.setDiscountIDLengthError("Discount ID must > 0 and <= 10 characters");
        }
        try {
            TblDiscountCodeDAO discountCodeDAO = new TblDiscountCodeDAO();
            TblDiscountCodeDTO discountCodeDTO = discountCodeDAO.getDiscountCodeByID(discountID);
            if (discountCodeDTO != null) {
                flag = true;
                error.setDiscountIDDuplicateError("Discount ID is duplicated");
            }
        } catch (NamingException e) {
            log.error(e);
        } catch (SQLException e) {
            log.error(e);
        }

        SimpleDateFormat sdfm = new SimpleDateFormat("MM-dd-yyyy");
        sdfm.setLenient(false);
        try {
            Date date = new Date(sdfm.parse(discountDate).getTime());
            if (date.before(new Date(System.currentTimeMillis()))) {
                flag = true;
                error.setDateBeforeTodayError("Discount code date can not before today");
            }
        } catch (ParseException e) {
            flag = true;
            error.setDateFormatError("Date format is MM-dd-yyyy");
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
        String discountID = request.getParameter("txtDiscountID");
        String discountPercent = request.getParameter("txtDiscountPercent");
        String discountDate = request.getParameter("txtDiscountDate");
        String url = ERROR_PAGE;
        try {
            if (session != null) {
                String role = (String) session.getAttribute("role");
                TblAccountDTO accountDTO = (TblAccountDTO) session.getAttribute("account");
                if (role != null && accountDTO != null) {
                    if (role.equals("Admin")) {
                        CreateNewDiscountCodeError error = checkParameter(discountID, discountDate);
                        if (error == null) {
                            TblDiscountCodeDAO discountCodeDAO = new TblDiscountCodeDAO();
                            int percent = Integer.parseInt(discountPercent);
                            SimpleDateFormat sdfm = new SimpleDateFormat("MM-dd-yyyy");
                            sdfm.setLenient(false);
                            Date date = new Date(sdfm.parse(discountDate).getTime());
                            boolean result = discountCodeDAO.createNewDiscountCode(discountID, percent, date);
                            if (result) {
                                url = SUCCESS_PAGE;
                            }
                        } else {
                            url = CREATE_DISCOUNT_CODE_PAGE;
                            request.setAttribute("createNewError", error);
                        }
                    }
                }
            }
        } catch (ParseException e) {
            log.error(e);
        } catch (NamingException e) {
            log.error(e);
        } catch (NumberFormatException e) {
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.tblorderdetail;

import java.io.Serializable;
import java.sql.Connection;
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
public class TblOrderDetailDAO implements Serializable {

    public boolean createNewOrderDetail(String orderID, String bookID, String bookTitle, int quantity, float price) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "INSERT INTO tblOrderDetail (orderID, bookID, bookTitle, quantity, price) "
                        + "VALUES (?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, orderID);
                stm.setString(2, bookID);
                stm.setString(3, bookTitle);
                stm.setInt(4, quantity);
                stm.setFloat(5, price);
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
    List<TblOrderDetailDTO> listOrderDetail;

    public List<TblOrderDetailDTO> getListOrderDetail() {
        return listOrderDetail;
    }

    public void searchOrderDetails(String orderID, String titleSearch) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "SELECT orderID, bookID, bookTitle, quantity, price "
                        + "FROM tblOrderDetail "
                        + "WHERE bookTitle LIKE ? AND orderID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + titleSearch + "%");
                stm.setString(2, orderID);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (listOrderDetail == null) {
                        listOrderDetail = new ArrayList<>();
                    }
                    String bookID = rs.getString("bookID");
                    String bookTitle = rs.getString("bookTitle");
                    int quantity = rs.getInt("quantity");
                    float price = rs.getFloat("price");
                    TblOrderDetailDTO orderDetailDTO = new TblOrderDetailDTO(orderID, bookID, bookTitle, quantity, price);
                    listOrderDetail.add(orderDetailDTO);
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
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.tblorder;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import phucln.utils.DBHelpers;

/**
 *
 * @author ASUS
 */
public class TblOrderDAO implements Serializable {

    public boolean checkUserUseCode(String userID, String discountID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "SELECT orderID, totalPrice, date "
                        + "FROM tblOrder "
                        + "WHERE userID = ? AND discountID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, userID);
                stm.setString(2, discountID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = true;
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

    public int getOrderRows() throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int result = 0;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "SELECT userID, orderID, totalPrice, date, discountID "
                        + "FROM tblOrder";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    result++;
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

    public boolean createNewOrder(String orderID, float totalPrice, String userID, Timestamp date, String discountID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "INSERT INTO tblOrder (orderID, totalPrice, userID, date, discountID) "
                        + "VALUES (?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, orderID);
                stm.setFloat(2, totalPrice);
                stm.setString(3, userID);
                stm.setTimestamp(4, date);
                stm.setString(5, discountID);
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
    List<TblOrderDTO> listOrder;

    public List<TblOrderDTO> getListOrder() {
        return listOrder;
    }

    public void searchOrderHistoryBySearchDate(String userID, String searchDate) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "SELECT orderID, totalPrice, date, discountID "
                        + "FROM tblOrder "
                        + "WHERE userID = ? AND ( CONVERT(VARCHAR(25), date, 126) LIKE ? )";
                stm = con.prepareStatement(sql);
                stm.setString(1, userID);
                stm.setString(2, searchDate + "%");
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (listOrder == null) {
                        listOrder = new ArrayList<>();
                    }
                    String orderID = rs.getString("orderID");
                    Timestamp date = rs.getTimestamp("date");
                    float totalPrice = rs.getFloat("totalPrice");
                    String discountID = rs.getString("discountID");
                    TblOrderDTO orderDTO = new TblOrderDTO(orderID, totalPrice, userID, date, discountID);
                    listOrder.add(orderDTO);
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

    public void searchOrderHistory(String userID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "SELECT orderID, totalPrice, date, discountID "
                        + "FROM tblOrder "
                        + "WHERE userID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, userID);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (listOrder == null) {
                        listOrder = new ArrayList<>();
                    }
                    String orderID = rs.getString("orderID");
                    Timestamp date = rs.getTimestamp("date");
                    float totalPrice = rs.getFloat("totalPrice");
                    String discountID = rs.getString("discountID");
                    TblOrderDTO orderDTO = new TblOrderDTO(orderID, totalPrice, userID, date, discountID);
                    listOrder.add(orderDTO);
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

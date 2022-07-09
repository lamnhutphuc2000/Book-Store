/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.tbldiscountcode;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import phucln.utils.DBHelpers;

/**
 *
 * @author ASUS
 */
public class TblDiscountCodeDAO implements Serializable {

    public TblDiscountCodeDTO getDiscountCodeByID(String discountID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblDiscountCodeDTO result = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "SELECT discountPercent, date "
                        + "FROM tblDiscountCode "
                        + "WHERE discountID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, discountID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    int discountPercent = rs.getInt("discountPercent");
                    Date date = rs.getDate("date");
                    result = new TblDiscountCodeDTO(discountID, discountPercent, date);
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

    public boolean createNewDiscountCode(String discountID, int discountPercent, Date date) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "INSERT INTO tblDiscountCode (discountID, discountPercent, date) "
                        + "VALUES (?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, discountID);
                stm.setInt(2, discountPercent);
                stm.setDate(3, date);
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
}

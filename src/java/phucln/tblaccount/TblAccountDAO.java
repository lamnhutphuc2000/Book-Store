/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.tblaccount;

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
public class TblAccountDAO implements Serializable {

    public TblAccountDTO checkLogin(String userID, String password) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblAccountDTO result = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "SELECT name, roleID "
                        + "FROM tblAccount "
                        + "WHERE userID = ? AND password = ? AND status = 'Active'";
                stm = con.prepareStatement(sql);
                stm.setString(1, userID);
                stm.setString(2, password);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("name");
                    String roleID = rs.getString("roleID");
                    result = new TblAccountDTO(userID, name, roleID, "Active");
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
    List<TblAccountDTO> listAccounts;

    public List<TblAccountDTO> getListAccounts() {
        return listAccounts;
    }
    
    public void loadAllAccount() throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null; 
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "SELECT userID, name, roleID, status "
                        + "FROM tblAccount ";
                stm = con.prepareStatement(sql); 
                rs = stm.executeQuery();
                while (rs.next()) {
                    if(listAccounts == null) listAccounts = new ArrayList<>();
                    String userID = rs.getString("userID");
                    String name = rs.getString("name");
                    String roleID = rs.getString("roleID");
                    String status = rs.getString("status");
                    TblAccountDTO account
                            = new TblAccountDTO(userID, name, roleID, status);
                    listAccounts.add(account);
                    
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

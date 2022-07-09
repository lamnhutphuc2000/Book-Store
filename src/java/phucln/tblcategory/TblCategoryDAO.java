/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.tblcategory;

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
public class TblCategoryDAO implements Serializable {

    List<TblCategoryDTO> listCategory;

    public List<TblCategoryDTO> getListCategory() {
        return listCategory;
    }

    public void loadAllCategory() throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "SELECT categoryID, categoryName "
                        + "FROM tblCategory";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (listCategory == null) {
                        listCategory = new ArrayList<>();
                    }
                    String categoryID = rs.getString("categoryID");
                    String categoryName = rs.getString("categoryName");
                    TblCategoryDTO dto = new TblCategoryDTO(categoryID, categoryName);
                    listCategory.add(dto);
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

    public TblCategoryDTO getCategoryByName(String categoryName) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblCategoryDTO result = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "SELECT categoryID "
                        + "FROM tblCategory "
                        + "WHERE categoryName = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, categoryName);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String categoryID = rs.getString("categoryID");
                    result = new TblCategoryDTO(categoryID, categoryName);
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
}

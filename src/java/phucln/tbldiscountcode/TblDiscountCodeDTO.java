/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.tbldiscountcode;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author ASUS
 */
public class TblDiscountCodeDTO implements Serializable {

    private String discountID;
    private int discountPercent;
    private Date date;

    public TblDiscountCodeDTO() {
    }

    public TblDiscountCodeDTO(String discountID, int discountPercent, Date date) {
        this.discountID = discountID;
        this.discountPercent = discountPercent;
        this.date = date;
    }

    /**
     * @return the discountID
     */
    public String getDiscountID() {
        return discountID;
    }

    /**
     * @param discountID the discountID to set
     */
    public void setDiscountID(String discountID) {
        this.discountID = discountID;
    }

    /**
     * @return the discountPercent
     */
    public int getDiscountPercent() {
        return discountPercent;
    }

    /**
     * @param discountPercent the discountPercent to set
     */
    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.tbldiscountcode;

import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class CreateNewDiscountCodeError implements Serializable {

    private String discountIDDuplicateError;
    private String discountIDLengthError;
    private String dateBeforeTodayError;
    private String dateFormatError;

    public CreateNewDiscountCodeError() {
    }

    public CreateNewDiscountCodeError(String discountIDDuplicateError, String discountIDLengthError, String dateBeforeTodayError, String dateFormatError) {
        this.discountIDDuplicateError = discountIDDuplicateError;
        this.discountIDLengthError = discountIDLengthError;
        this.dateBeforeTodayError = dateBeforeTodayError;
        this.dateFormatError = dateFormatError;
    }

    /**
     * @return the discountIDLengthError
     */
    public String getDiscountIDLengthError() {
        return discountIDLengthError;
    }

    /**
     * @param discountIDLengthError the discountIDLengthError to set
     */
    public void setDiscountIDLengthError(String discountIDLengthError) {
        this.discountIDLengthError = discountIDLengthError;
    }

    /**
     * @return the dateBeforeTodayError
     */
    public String getDateBeforeTodayError() {
        return dateBeforeTodayError;
    }

    /**
     * @param dateBeforeTodayError the dateBeforeTodayError to set
     */
    public void setDateBeforeTodayError(String dateBeforeTodayError) {
        this.dateBeforeTodayError = dateBeforeTodayError;
    }

    /**
     * @return the dateFormatError
     */
    public String getDateFormatError() {
        return dateFormatError;
    }

    /**
     * @param dateFormatError the dateFormatError to set
     */
    public void setDateFormatError(String dateFormatError) {
        this.dateFormatError = dateFormatError;
    }

    /**
     * @return the discountIDDuplicateError
     */
    public String getDiscountIDDuplicateError() {
        return discountIDDuplicateError;
    }

    /**
     * @param discountIDDuplicateError the discountIDDuplicateError to set
     */
    public void setDiscountIDDuplicateError(String discountIDDuplicateError) {
        this.discountIDDuplicateError = discountIDDuplicateError;
    }

}

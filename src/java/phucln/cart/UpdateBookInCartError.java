/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.cart;

import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class UpdateBookInCartError implements Serializable {

    private String bookID;
    private String bookOutOfStockError;
    private String quantityFormatError;
    private String quantityEmptyError;
    private String quantityNegativeError;

    public UpdateBookInCartError() {
    }

    public UpdateBookInCartError(String bookID, String bookOutOfStockError, String quantityFormatError, String quantityEmptyError, String quantityNegativeError) {
        this.bookID = bookID;
        this.bookOutOfStockError = bookOutOfStockError;
        this.quantityFormatError = quantityFormatError;
        this.quantityEmptyError = quantityEmptyError;
        this.quantityNegativeError = quantityNegativeError;
    }

    /**
     * @return the quantityFormatError
     */
    public String getQuantityFormatError() {
        return quantityFormatError;
    }

    /**
     * @param quantityFormatError the quantityFormatError to set
     */
    public void setQuantityFormatError(String quantityFormatError) {
        this.quantityFormatError = quantityFormatError;
    }

    /**
     * @return the quantityNegativeError
     */
    public String getQuantityNegativeError() {
        return quantityNegativeError;
    }

    /**
     * @param quantityNegativeError the quantityNegativeError to set
     */
    public void setQuantityNegativeError(String quantityNegativeError) {
        this.quantityNegativeError = quantityNegativeError;
    }

    /**
     * @return the quantityEmptyError
     */
    public String getQuantityEmptyError() {
        return quantityEmptyError;
    }

    /**
     * @param quantityEmptyError the quantityEmptyError to set
     */
    public void setQuantityEmptyError(String quantityEmptyError) {
        this.quantityEmptyError = quantityEmptyError;
    }

    /**
     * @return the bookID
     */
    public String getBookID() {
        return bookID;
    }

    /**
     * @param bookID the bookID to set
     */
    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    /**
     * @return the bookOutOfStockError
     */
    public String getBookOutOfStockError() {
        return bookOutOfStockError;
    }

    /**
     * @param bookOutOfStockError the bookOutOfStockError to set
     */
    public void setBookOutOfStockError(String bookOutOfStockError) {
        this.bookOutOfStockError = bookOutOfStockError;
    }

}

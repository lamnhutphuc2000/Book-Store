/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.tblbook;

import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class CreateNewBookError implements Serializable {

    private String bookIDDuplicateError;
    private String bookIDLengthError;
    private String titleLengthError;
    private String imageEmptyError;
    private String descriptionLengthError;
    private String priceNegativeError;
    private String priceEmtpyError;
    private String priceFormatError;
    private String authorLengthError;
    private String authorFormatError;
    private String quantityLengthError;
    private String quantityFormatError;
    private String quantityNegativeError;

    public CreateNewBookError() {
    }

    public CreateNewBookError(String bookIDDuplicateError, String bookIDLengthError, String titleLengthError, String imageEmptyError, String descriptionLengthError, String priceNegativeError, String priceEmtpyError, String priceFormatError, String authorLengthError, String authorFormatError, String quantityLengthError, String quantityFormatError, String quantityNegativeError) {
        this.bookIDDuplicateError = bookIDDuplicateError;
        this.bookIDLengthError = bookIDLengthError;
        this.titleLengthError = titleLengthError;
        this.imageEmptyError = imageEmptyError;
        this.descriptionLengthError = descriptionLengthError;
        this.priceNegativeError = priceNegativeError;
        this.priceEmtpyError = priceEmtpyError;
        this.priceFormatError = priceFormatError;
        this.authorLengthError = authorLengthError;
        this.authorFormatError = authorFormatError;
        this.quantityLengthError = quantityLengthError;
        this.quantityFormatError = quantityFormatError;
        this.quantityNegativeError = quantityNegativeError;
    }

    /**
     * @return the bookIDDuplicateError
     */
    public String getBookIDDuplicateError() {
        return bookIDDuplicateError;
    }

    /**
     * @param bookIDDuplicateError the bookIDDuplicateError to set
     */
    public void setBookIDDuplicateError(String bookIDDuplicateError) {
        this.bookIDDuplicateError = bookIDDuplicateError;
    }

    /**
     * @return the titleLengthError
     */
    public String getTitleLengthError() {
        return titleLengthError;
    }

    /**
     * @param titleLengthError the titleLengthError to set
     */
    public void setTitleLengthError(String titleLengthError) {
        this.titleLengthError = titleLengthError;
    }

    /**
     * @return the imageEmptyError
     */
    public String getImageEmptyError() {
        return imageEmptyError;
    }

    /**
     * @param imageEmptyError the imageEmptyError to set
     */
    public void setImageEmptyError(String imageEmptyError) {
        this.imageEmptyError = imageEmptyError;
    }

    /**
     * @return the descriptionLengthError
     */
    public String getDescriptionLengthError() {
        return descriptionLengthError;
    }

    /**
     * @param descriptionLengthError the descriptionLengthError to set
     */
    public void setDescriptionLengthError(String descriptionLengthError) {
        this.descriptionLengthError = descriptionLengthError;
    }

    /**
     * @return the priceNegativeError
     */
    public String getPriceNegativeError() {
        return priceNegativeError;
    }

    /**
     * @param priceNegativeError the priceNegativeError to set
     */
    public void setPriceNegativeError(String priceNegativeError) {
        this.priceNegativeError = priceNegativeError;
    }

    /**
     * @return the priceEmtpyError
     */
    public String getPriceEmtpyError() {
        return priceEmtpyError;
    }

    /**
     * @param priceEmtpyError the priceEmtpyError to set
     */
    public void setPriceEmtpyError(String priceEmtpyError) {
        this.priceEmtpyError = priceEmtpyError;
    }

    /**
     * @return the priceFormatError
     */
    public String getPriceFormatError() {
        return priceFormatError;
    }

    /**
     * @param priceFormatError the priceFormatError to set
     */
    public void setPriceFormatError(String priceFormatError) {
        this.priceFormatError = priceFormatError;
    }

    /**
     * @return the authorLengthError
     */
    public String getAuthorLengthError() {
        return authorLengthError;
    }

    /**
     * @param authorLengthError the authorLengthError to set
     */
    public void setAuthorLengthError(String authorLengthError) {
        this.authorLengthError = authorLengthError;
    }

    /**
     * @return the authorFormatError
     */
    public String getAuthorFormatError() {
        return authorFormatError;
    }

    /**
     * @param authorFormatError the authorFormatError to set
     */
    public void setAuthorFormatError(String authorFormatError) {
        this.authorFormatError = authorFormatError;
    }

    /**
     * @return the quantityLengthError
     */
    public String getQuantityLengthError() {
        return quantityLengthError;
    }

    /**
     * @param quantityLengthError the quantityLengthError to set
     */
    public void setQuantityLengthError(String quantityLengthError) {
        this.quantityLengthError = quantityLengthError;
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
     * @return the bookIDLengthError
     */
    public String getBookIDLengthError() {
        return bookIDLengthError;
    }

    /**
     * @param bookIDLengthError the bookIDLengthError to set
     */
    public void setBookIDLengthError(String bookIDLengthError) {
        this.bookIDLengthError = bookIDLengthError;
    }

}

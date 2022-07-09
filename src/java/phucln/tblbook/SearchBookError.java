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
public class SearchBookError implements Serializable {

    private String priceFromNegativeError;
    private String priceFromFormatError;
    private String priceToNegativeError;
    private String priceToFormatError;
    private String priceToSmallerPriceFrom;

    public SearchBookError() {
    }

    public SearchBookError(String priceFromNegativeError, String priceFromFormatError, String priceToNegativeError, String priceToFormatError, String priceToSmallerPriceFrom) {
        this.priceFromNegativeError = priceFromNegativeError;
        this.priceFromFormatError = priceFromFormatError;
        this.priceToNegativeError = priceToNegativeError;
        this.priceToFormatError = priceToFormatError;
        this.priceToSmallerPriceFrom = priceToSmallerPriceFrom;
    }

    /**
     * @return the priceFromNegativeError
     */
    public String getPriceFromNegativeError() {
        return priceFromNegativeError;
    }

    /**
     * @param priceFromNegativeError the priceFromNegativeError to set
     */
    public void setPriceFromNegativeError(String priceFromNegativeError) {
        this.priceFromNegativeError = priceFromNegativeError;
    }

    /**
     * @return the priceFromFormatError
     */
    public String getPriceFromFormatError() {
        return priceFromFormatError;
    }

    /**
     * @param priceFromFormatError the priceFromFormatError to set
     */
    public void setPriceFromFormatError(String priceFromFormatError) {
        this.priceFromFormatError = priceFromFormatError;
    }

    /**
     * @return the priceToNegativeError
     */
    public String getPriceToNegativeError() {
        return priceToNegativeError;
    }

    /**
     * @param priceToNegativeError the priceToNegativeError to set
     */
    public void setPriceToNegativeError(String priceToNegativeError) {
        this.priceToNegativeError = priceToNegativeError;
    }

    /**
     * @return the priceToFormatError
     */
    public String getPriceToFormatError() {
        return priceToFormatError;
    }

    /**
     * @param priceToFormatError the priceToFormatError to set
     */
    public void setPriceToFormatError(String priceToFormatError) {
        this.priceToFormatError = priceToFormatError;
    }

    /**
     * @return the priceToSmallerPriceFrom
     */
    public String getPriceToSmallerPriceFrom() {
        return priceToSmallerPriceFrom;
    }

    /**
     * @param priceToSmallerPriceFrom the priceToSmallerPriceFrom to set
     */
    public void setPriceToSmallerPriceFrom(String priceToSmallerPriceFrom) {
        this.priceToSmallerPriceFrom = priceToSmallerPriceFrom;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.cart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class Cart implements Serializable {

    private List<BookInCart> cart;

    public List<BookInCart> getCart() {
        return cart;
    }

    public void addBookToCart(String bookID, String bookTitle, float price) {
        if (this.cart == null) {
            this.cart = new ArrayList<>();
        }
        int quantity;
        boolean flag = false;
        for (int i = 0; i < this.cart.size(); i++) {
            if (this.cart.get(i).getBookID().equals(bookID)) {
                flag = true;
                this.cart.get(i).setPrice(price);
                quantity = this.cart.get(i).getQuantity() + 1;
                this.cart.get(i).setQuantity(quantity);
            }
        }
        if (flag == false) {
            BookInCart bookInCart = new BookInCart(bookID, bookTitle, price, 1);
            this.cart.add(bookInCart);
        }
    }

    public void removeBookInCart(String bookID) {
        if (this.cart == null) {
            return;
        }
        if (this.cart.isEmpty()) {
            return;
        }
        if (this.cart.size() == 1) {
            if (this.cart.get(0).getBookID().equals(bookID)) {
                this.cart.remove(0);
                if (this.cart.isEmpty()) {
                    this.cart = null;
                }
            }
        } else {
            for (int i = 0; i < this.cart.size(); i++) {
                if (this.cart.get(i).getBookID().equals(bookID)) {
                    this.cart.remove(i);
                    if (this.cart.isEmpty()) {
                        this.cart = null;
                    }
                }
            }
        }
    }

    public void updateBookInCart(String bookID, int quantity) {
        if (this.cart == null) {
            return;
        }
        if (this.cart.isEmpty()) {
            return;
        }
        if (this.cart.size() == 1) {
            if (this.cart.get(0).getBookID().equals(bookID)) {
                this.cart.get(0).setQuantity(quantity);
            }
        } else {
            for (int i = 0; i < this.cart.size(); i++) {
                if (this.cart.get(i).getBookID().equals(bookID)) {
                    this.cart.get(i).setQuantity(quantity);
                }
            }
        }
    }
}

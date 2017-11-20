/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deposit.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author gustavo
 */
public class ProductProperty {
    
    private StringProperty id;  // Id of product.
    private StringProperty name; //Name of product.
    private StringProperty price;   // Price of product.
    private StringProperty amount; // Amount of product.

    public ProductProperty(String id, String name, String price, int amount) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleStringProperty(price);
        this.amount = new SimpleStringProperty(Integer.toString(amount));
    }

    /**
     * Return the name of product.
     *
     * @return the name
     */
    public StringProperty getName() {
        return name;
    }

    /**
     * Change the name of product.
     *
     * @param name the name to set
     */
    public void setName(StringProperty name) {
        this.name = name;
    }

    /**
     * Return the id of product.
     *
     * @return the id
     */
    public StringProperty getId() {
        return id;
    }

    /**
     * Change the id of product.
     *
     * @param id the id to set
     */
    public void setId(StringProperty id) {
        this.id = id;
    }

    /**
     * Return the price of product.
     *
     * @return the price
     */
    public StringProperty getPrice() {
        return price;
    }

    /**
     * Change the price of product.
     *
     * @param price the price to set
     */
    public void setPrice(StringProperty price) {
        this.price = price;
    }

    /**
     * Return the amount of product.
     *
     * @return the amount
     */
    public StringProperty getAmount() {
        return amount;
    }

    /**
     * Change the amount of product.
     *
     * @param amount the amount to set
     */
    public void setAmount(StringProperty amount) {
        this.amount = amount;
    }
}

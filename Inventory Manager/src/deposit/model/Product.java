/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deposit.model;

/**
 * This class store the data of the products in deposit.
 *
 * @author Gustavo Henrique.
 */
public class Product {

    private String id;  // Id of product.
    private String name; //Name of product.
    private String price;   // Price of product.
    private int amount; // Amount of product.

    public Product(String id, String name, String price, int amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    /**
     * Return the name of product.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Change the name of product.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the id of product.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Change the id of product.
     *
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Return the price of product.
     *
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * Change the price of product.
     *
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * Return the amount of product.
     *
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Change the amount of product.
     *
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

}

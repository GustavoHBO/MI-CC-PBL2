/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deposit.util;

/**
 *
 * @author Gustavo Henrique.
 */
public interface IProduct {
    
    /**
     * Return the name of product.
     *
     * @return the name
     */
    public String getName();

    /**
     * Change the name of product.
     *
     * @param name the name to set
     */
    public void setName(String name);

    /**
     * Return the id of product.
     *
     * @return the id
     */
    public String getId();

    /**
     * Change the id of product.
     *
     * @param id the id to set
     */
    public void setId(String id);

    /**
     * Return the price of product.
     *
     * @return the price
     */
    public String getPrice();

    /**
     * Change the price of product.
     *
     * @param price the price to set
     */
    public void setPrice(String price);

    /**
     * Return the amount of product.
     *
     * @return the amount
     */
    public int getAmount();

    /**
     * Change the amount of product.
     *
     * @param amount the amount to set
     */
    public void setAmount(int amount);

}

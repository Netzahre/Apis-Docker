package org.example.clientegraficoapis.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {
    @SerializedName("id")
    private Integer idProduct;

    private String name;

    private String description;

    private double price;

    private List<Purchases> purchases;

    /**
     * No args constructor for use in serialization
     *
     */
    public Product() {}

    /**
     * Args constructor
     * @param id id of the product
     * @param name name of the product
     * @param description description of the product
     * @param price price of the product
     */
    public Product(Integer id, String name, String description, double price) {
        this.idProduct = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    /**
     * Get the id of the product
     * @return id of the product
     */
    public Integer getId() {
        return idProduct;
    }

    /**
     * Set the id of the product
     * @param idProduct id of the product
     */
    public void setId(Integer idProduct) {
        this.idProduct = idProduct;
    }

    /**
     * Get the name of the product
     * @return name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the product
     * @param name name of the product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the description of the product
     * @return description of the product
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the product
     * @param description description of the product
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the price of the product
     * @return price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set the price of the product
     * @param price price of the product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get the list of purchases of the product
     * @return list of purchases of the product
     */
    public List<Purchases> getPurchases() {
        return purchases;
    }

    /**
     * Set the list of purchases of the product
     * @param purchases list of purchases of the product
     */
    public void setPurchases(List<Purchases> purchases) {
        this.purchases = purchases;
    }
}

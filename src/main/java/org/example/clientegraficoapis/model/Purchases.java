package org.example.clientegraficoapis.model;


import java.util.List;

public class Purchases {
    private int idPurchase;

    private String purchaser;

    private List<Product> productList;

    public Purchases() {}

    /**
     * Args constructor
     * @param purchaser username of the buyer
     * @param products list of products purchased
     */
    public Purchases(String purchaser, List<Product> products) {
        this.purchaser = purchaser;
        this.productList = products;
    }

    /**
     * Gets the id of the purchase
     * @return id of the purchase
     */
    public int getIdPurchase() {
        return idPurchase;
    }

    /**
     * Sets the id of the purchase
     * @param idPurchase id of the purchase
     */
    public void setIdPurchase(int idPurchase) {
        this.idPurchase = idPurchase;
    }

    /**
     * Gets the username of the buyer
     * @return username of the buyer
     */
    public String getPurchaser() {
        return purchaser;
    }

    /**
     * Sets the username of the buyer
     * @param purchaser username of the buyer
     */
    public void setPurchaser(String purchaser) {
        this.purchaser = purchaser;
    }

    /**
     * Gets the list of products purchased
     * @return list of products purchased
     */
    public List<Product> getProductList() {
        return productList;
    }

    /**
     * Sets the list of products purchased
     * @param productList list of products purchased
     */
    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}

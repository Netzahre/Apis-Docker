package org.example.clientegraficoapis.model;


import java.util.List;

public class Purchases {
    private int idPurchase;

    private String purchaser;

    private List<Product> productList;

    public Purchases(){}

    public Purchases(String purchaser, List<Product> products){
        this.purchaser = purchaser;
        this.productList = products;
    }

    public int getIdPurchase() {
        return idPurchase;
    }

    public void setIdPurchase(int idPurchase) {
        this.idPurchase = idPurchase;
    }

    public String getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(String purchaser) {
        this.purchaser = purchaser;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

}

package org.example.api_productos.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Purchases {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPurchase;

    @Column(name = "purchaser")
    private String purchaser;

    // La lista de productos ya no tiene relación directa con ManyToMany
    @Transient
    private transient List<Product> productList;

    public Purchases() {}

    public Purchases(String purchaser, List<Product> products) {
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

package org.example.clientegraficoapis.model;


import java.util.List;

public class Product {
    private Integer idProduct;

    private String name;

    private String description;

    private double price;

    private List<Purchases> purchases;

    public Product() {}

    public Product(Integer id, String name, String description, double price) {
        this.idProduct = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Integer getIdProduct() {
        return idProduct;
    }


    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String nombre) {
        this.name = nombre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descripcion) {
        this.description = descripcion;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double precio) {
        this.price = precio;
    }

    public List<Purchases> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchases> purchases) {
        this.purchases = purchases;
    }
}

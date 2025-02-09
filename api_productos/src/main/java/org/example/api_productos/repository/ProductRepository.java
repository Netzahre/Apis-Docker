package org.example.api_productos.repository;

import org.example.api_productos.model.Product;
import org.example.api_productos.model.Purchases;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByNameContaining(String name);
    List<Product> findByPriceGreaterThanEqual(Double price);
    List<Product> findByPriceLessThanEqual(Double price);
    List<Product> findByPriceBetween(Double min, Double max);
    List<Product> findByNameContainingAndPriceBetween(String name, Double min, Double max);
    List<Product> findByNameContainingAndPriceGreaterThanEqual(String name, Double min);
    List<Product> findByNameContainingAndPriceLessThanEqual(String name, Double max);
}

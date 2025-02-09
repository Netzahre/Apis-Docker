package org.example.api_productos.repository;

import org.example.api_productos.model.Product;
import org.example.api_productos.model.Purchases;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}

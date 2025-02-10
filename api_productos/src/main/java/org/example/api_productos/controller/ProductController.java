package org.example.api_productos.controller;


import org.example.api_productos.model.Product;
import org.example.api_productos.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        if (name != null && !name.isEmpty()) {

            if (minPrice != null && maxPrice != null) {
                    return productRepository.findByNameContainingAndPriceBetween(name, minPrice, maxPrice);
            }
            else if (minPrice != null) {
                    return productRepository.findByNameContainingAndPriceGreaterThanEqual(name, minPrice);
            }

            else if (maxPrice != null) {
                    return productRepository.findByNameContainingAndPriceLessThanEqual(name, maxPrice);
            }
            else {
                    return productRepository.findByNameContaining(name);
            }
        } else {
            if (minPrice != null && maxPrice != null) {
                return productRepository.findByPriceBetween(minPrice, maxPrice);
            } else if (minPrice != null) {
                return productRepository.findByPriceGreaterThanEqual(minPrice);
            } else if (maxPrice != null) {
                return productRepository.findByPriceLessThanEqual(maxPrice);
            } else {
                return productRepository.findAll();
            }
        }
    }

    @PostMapping
    public ResponseEntity<?> newProduct(@RequestBody Product product){
        Product newProd = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProd);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Integer id) {
        if(productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.ok("Producto eliminado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Integer id, @RequestBody Product product) {
        Optional<Product> actualProduct = productRepository.findById(id);
        if(actualProduct.isPresent()) {
            Product tempProduct = actualProduct.get();
            tempProduct.setName(product.getName());
            tempProduct.setDescription(product.getDescription());
            tempProduct.setPrice(product.getPrice());
            productRepository.save(tempProduct);
            return ResponseEntity.ok(tempProduct);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
        }
    }
}

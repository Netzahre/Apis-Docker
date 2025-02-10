package org.example.api_productos.controller;

import org.example.api_productos.model.Purchases;
import org.example.api_productos.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @PostMapping
    public ResponseEntity<?> newPurchase(@RequestBody Purchases purchase) {
        Purchases newPurch = purchaseRepository.save(purchase);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPurch);
    }

}

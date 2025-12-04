package com.app.ecom.controller;


import com.app.ecom.dto.ProductRequest;
import com.app.ecom.dto.ProductResponse;
import com.app.ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/api/products")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(service.getAllProducts());
    }

    @PostMapping("api/products")
    public String createProduct(@RequestBody ProductRequest productRequest) {
        service.createProduct(productRequest);
        return "Product created successfully";
    }

    @GetMapping("/api/products/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable int id) {
        return ResponseEntity.ok(service.getProductById(id));
    }

    @PutMapping("/api/products/{id}")
    public String updateProduct(@PathVariable int id, @RequestBody ProductRequest productRequest) {
        service.updateProduct(id, productRequest);
        return "Product updated successfully";
    }

    @DeleteMapping("/api/products/{id}")
    public String deleteProduct(@PathVariable int id){
        return service.deleteProduct(id);
    }

}

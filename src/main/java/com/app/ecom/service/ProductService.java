package com.app.ecom.service;

import com.app.ecom.dto.ProductRequest;
import com.app.ecom.dto.ProductResponse;
import com.app.ecom.model.Product;
import com.app.ecom.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<ProductResponse> getAllProducts(){
        return productRepository.findAll()
                .stream()
                .map(ProductService::mapProductResponse)
                .toList();
    }

    public ProductResponse getProductById(int id) {
        return mapProductResponse(productRepository.findById(id).orElse(null));
    }

    public void createProduct(ProductRequest productRequest){
        Product product = new Product();
        mapProductFromReq(product, productRequest);
        productRepository.save(product);
    }

    public void updateProduct(int id, ProductRequest productRequest){
        productRepository.findById(id).ifPresent(existingProduct -> {
            mapProductFromReq(existingProduct, productRequest);
            productRepository.save(existingProduct);
        });
    }

    public String deleteProduct(int id){
        if(productRepository.existsById(id)){
            productRepository.deleteById(id);
            return "Product deleted successfully";
        } else {
            return "Product not found";
        }
    }
    

    public static Product mapProductFromReq(Product product, ProductRequest req){
        product.setName(req.getName());
        product.setCategory(req.getCategory());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setQuantity(req.getQuantity());
        return product;
    }

    public static ProductResponse mapProductResponse(Product product){
        if (null == product) {
            return null;
        }
        return ProductResponse.builder()
                .id(product.getId())
                .active(product.isActive())
                .name(product.getName())
                .category(product.getCategory())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }
}

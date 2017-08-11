package io.swagger.service;

import io.swagger.model.NewProduct;
import io.swagger.model.Product;
import io.swagger.model.Products;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    public ResponseEntity all();
    public ResponseEntity createProduct(NewProduct product);
    public ResponseEntity getById(String id);
    public ResponseEntity updateProduct(NewProduct product,String id);
    public ResponseEntity deleteProduct(String id);
}

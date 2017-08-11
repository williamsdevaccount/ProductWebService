package io.swagger.service;

import io.swagger.model.*;
import io.swagger.model.Error;
import io.swagger.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
public class LocalProductService implements ProductService {
    private ProductRepository repo;

    @Autowired
    public LocalProductService(ProductRepository repo){
        this.repo = repo;
    }

    @Override
    public ResponseEntity all() {

        List<Product> products = this.repo.getAll();
        InlineResponse200 res = new InlineResponse200();
        res.setProducts(products);
        return new  ResponseEntity<>(res, HttpStatus.OK);
    }

    @Override
    public ResponseEntity createProduct(NewProduct newProduct) {
        Error error = null;

        if (!validate(newProduct.getName())){
            error = Error.factory("request body key name not present or empty, name is required","InvalidInput");
        }
        else if(!validate(newProduct.getDescription())){
            error = Error.factory("request body key description not present or empty, description is required","InvalidInput");
        }
        else if (this.repo.getByName(newProduct.getName()) !=null){
            error = Error.factory(
                    "product with name : " + newProduct.getName() + " already exists. names are required to be unique.",
                    "ProductExists");
        }
        if (error!=null) return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
        Product p = this.repo.save(newProduct);
        return new ResponseEntity<>(p,HttpStatus.CREATED);
    }
    private boolean validate(String str){
        return str !=null && !str.isEmpty();
    }
    private boolean validateName(NewProduct product){
        return product.getName() != null && !product.getName().isEmpty();
    }

    @Override
    public ResponseEntity getById(String id) {
        Product product = this.repo.getOne(id);
        if (product!=null) {
            return new ResponseEntity<>(this.repo.getOne(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(LocalProductService.NotFoundError(),HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity updateProduct(NewProduct updateProduct,String id){
        Product product = this.repo.getOne(id);
        if (product==null){
            return new ResponseEntity<>(LocalProductService.NotFoundError(),HttpStatus.NOT_FOUND);
        }
        else if (updateProduct.getName().equals(product.getName()) && updateProduct.getDescription().equals(product.getDescription())){
            return new ResponseEntity<>(product,HttpStatus.OK);
        }
        Error error = null;
        if (!product.getName().equals(updateProduct.getName())){
            if (!validate(updateProduct.getName())){
                error = Error.factory("request body key name cannot be empty to update products existing name","InvalidInput");
            }
            else if(this.repo.getByName(updateProduct.getName())!=null){
                error = Error.factory(
                        "product with name : " + updateProduct.getName() + " already exists. names are required to be unique.",
                        "ProductExists");
            }
        }
        if(error!=null){
            return new ResponseEntity<>(error,HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (!product.getDescription().equals(updateProduct.getDescription())){
            if (!validate(updateProduct.getDescription())){
                error = Error.factory("request body key description cannot be empty to update products existing description","InvalidInput");
            }
        }
        if(error!=null){
            return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        product.setName(updateProduct.getName());
        product.setDescription(updateProduct.getDescription());
        Product updated = this.repo.update(product);
        if (updated!=null){
            return new ResponseEntity<>(updated,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(Error.factory("Server Error", "InternalError"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private static Error NotFoundError (){
        return Error.factory("Product not found with provided identifier","NotFound");
    }
    @Override
    public ResponseEntity deleteProduct(String id) {
        boolean deleted = this.repo.delete(id);
        if (deleted){
            return new ResponseEntity<>(HttpStatus.valueOf(204));
        }
        return new ResponseEntity<>(LocalProductService.NotFoundError(),HttpStatus.NOT_FOUND);
    }
}

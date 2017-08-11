package io.swagger.repository;

import io.swagger.model.NewProduct;
import io.swagger.model.Product;

import java.util.List;

public interface ProductRepository {
    public List<Product> getAll();
    public Product save(NewProduct product);
    public Product getOne(String id);
    public Product getByName(String name);
    public Product update(Product product);
    public boolean delete(String id);
}

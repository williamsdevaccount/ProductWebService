package io.swagger.repository;

import io.swagger.model.NewProduct;
import io.swagger.model.Product;
import io.swagger.model.Products;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.util.*;

public class HashMapProductRepository implements ProductRepository {
    private HashMap<String,Product> products;
    private static final DateFormatter formatter = new DateFormatter(DateFormat.getDateTimeInstance());;
    public HashMapProductRepository(){
        this.products = new HashMap<>();
    }
    private String generateId (){
        return UUID.randomUUID().toString();
    }
    private String generateDateTime(){
        try {
            return HashMapProductRepository.formatter.valueToString(new Date());
        }
        catch(Exception e){
            return null;
        }
    }
    @Override
    public List<Product> getAll() {
        return new ArrayList<>(this.products.values());
    }

    @Override
    public Product save(NewProduct product) {
        Product p = new Product();
        String id = this.generateId();
        p.created(this.generateDateTime());
        p.updated(this.generateDateTime());
        p.name(product.getName());
        p.description(product.getDescription());
        p.id(id);
        this.products.put(id,p);
        return p;
    }

    @Override
    public Product getOne(String id) {
        if (this.products.containsKey(id)){
            return this.products.get(id);
        }
        return null;
    }

    @Override
    public Product getByName(String name) {
        List<Product> all = this.getAll();
        for (Product p : all){
            if (p.getName().equals(name)){
                return p;
            }
        }
        return null;
    }

    @Override
    public Product update(Product product) {
        Product update = new Product();
        update.setUpdated(this.generateDateTime());
        update.setName(product.getName());
        update.setDescription(product.getDescription());
        update.setCreated(product.getCreated());
        update.setId(product.getId());
        this.products.put(product.getId(),update);
        return update;
    }

    @Override
    public boolean delete(String id) {
        if (this.products.containsKey(id)){
            this.products.remove(id);
            return true;
        }
        return false;
    }
}

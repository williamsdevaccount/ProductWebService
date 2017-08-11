package test.io.swagger.repository; 

import io.swagger.model.NewProduct;
import io.swagger.model.Product;
import io.swagger.repository.HashMapProductRepository;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/** 
* HashMapProductRepository Tester. 
* 
* @author <William Stowers>
* @since <pre>Aug 10, 2017</pre> 
* @version 1.0 
*/ 
public class HashMapProductRepositoryTest { 
private HashMapProductRepository repo;
private ArrayList<Product> products;


public void populateTestData(int size){
    for(int i = 0; i < size;i++){
        NewProduct newProduct = new NewProduct();
        newProduct.name("Product "+i);
        newProduct.description("Product "+i);
        products.add(this.repo.save(newProduct));
    }
}
@Before
public void before() throws Exception {
    this.repo = new HashMapProductRepository();
    this.products = new ArrayList<>();
} 

@After
public void after() throws Exception {
    this.repo = null;
    this.products = null;
} 

/** 
* 
* Method: getAll() 
* 
*/ 
@Test
public void testGetAll() throws Exception {
    this.populateTestData(10);
    List<Product> all= this.repo.getAll();
    assertEquals("assert size equals input size",this.products.size(),all.size());
}

/** 
* 
* Method: save(NewProduct product) 
* 
*/ 
@Test
public void testSave() throws Exception {
    NewProduct newProduct = new NewProduct();
    newProduct.name("New Product");
    newProduct.description("cool new product");
    Product p = this.repo.save(newProduct);
    System.out.println(p);
    assertNotNull("should have returned valid product object",p);
    assertEquals("names should equal",newProduct.getName().equals(p.getName()),true);
    assertEquals("descriptions should equal",newProduct.getDescription().equals(p.getDescription()),true);
} 

/** 
* 
* Method: getOne(String id) 
* 
*/ 
@Test
public void testGetOne() throws Exception {
    this.populateTestData(100);
    Product select = this.products.get(new Random().nextInt(100));
    Product one = this.repo.getOne(select.getId());
    assertNotNull("product fetched from repo is not null",one);
    assertEquals("assert products equal",select,one);

} 

/** 
* 
* Method: update(Product product) 
* 
*/ 
@Test
public void testUpdate() throws Exception {
    this.populateTestData(10);
    Product update = this.products.get(0);
    Thread.sleep(2000);
    System.out.println(update);
    update.setName("My Random Product");
    update.setDescription("my description updated");
    Product updated = this.repo.update(update);
    System.out.println(updated);
    assertEquals("names equal",true,update.getName().equals(updated.getName()));
    assertEquals("assert dates are not equal",false,update.getUpdated().equals(updated.getUpdated()));
} 

/** 
* 
* Method: delete(String id) 
* 
*/ 
@Test
public void testDelete() throws Exception {
    this.populateTestData(10);
    Product p = this.products.get(0);
    boolean deleted = this.repo.delete(p.getId());
    assertTrue("should be deleted",deleted);
    assertNull("get product should return null",this.repo.getOne(p.getId()));
}

@Test
public void testGetByName () throws Exception{
    this.populateTestData(10);
    Product p = this.products.get(0);
    assertEquals("assert products are equal when fetch by name",p,this.repo.getByName(p.getName()));
}

/** 
* 
* Method: generateId() 
* 
*/ 
@Test
public void testGenerateId() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = HashMapProductRepository.getClass().getMethod("generateId"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: generateDateTime() 
* 
*/ 
@Test
public void testGenerateDateTime() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = HashMapProductRepository.getClass().getMethod("generateDateTime"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 

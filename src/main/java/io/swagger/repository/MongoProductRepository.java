package io.swagger.repository;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import io.swagger.model.NewProduct;
import io.swagger.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static com.mongodb.client.model.Filters.*;
import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.util.*;

@Component
public class MongoProductRepository implements ProductRepository {
    private MongoClient client;
    private MongoDatabase db;
    private MongoCollection collection;
    private static final DateFormatter formatter = new DateFormatter(DateFormat.getDateTimeInstance());
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String generateId (){
        return UUID.randomUUID().toString();
    }
    private String generateDateTime(){
        try {
            return MongoProductRepository.formatter.valueToString(new Date());
        }
        catch(Exception e){
            return null;
        }
    }

    @Autowired
    public MongoProductRepository(MongoClient client){
        this.client = client;
        this.db = this.client.getDatabase("local");
        this.db.createCollection("product");
        this.collection = this.db.getCollection("product");
        IndexOptions indexOptions = new IndexOptions().unique(true);
        this.collection.createIndex(Indexes.ascending("name"), indexOptions);
    }
    @Override
    public List<Product> getAll() {
        final List<Product>products = new ArrayList<>();
        Block<Document> printBlock = new Block<Document>() {
            @Override
            public void apply(final Document document) {
                products.add(MongoProductRepository.fromDocument(document));
            }
        };
        this.collection.find().forEach(printBlock);
        return products;
    }

    private static Document fromProduct(Product product){
        try {
            return new Document("_id", new ObjectId(product.getId()))
                    .append("name", product.getName())
                    .append("description", product.getDescription())
                    .append("created", product.getCreated())
                    .append("updated", product.getUpdated());
        }
        catch(Exception e){
            return null;
        }
    }
    private static Product fromDocument(Document doc){
        try {
            return new Product().id(doc.getObjectId("_id").toString())
                    .name(doc.getString("name"))
                    .description(doc.getString("description"))
                    .created(doc.getString("created"))
                    .updated(doc.getString("updated"));
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public Product save(NewProduct product) {
        ObjectId id = new ObjectId();
        Product p = new Product()
                .id(id.toString())
                .name(product.getName())
                .description(product.getDescription())
                .created(this.generateDateTime())
                .updated(this.generateDateTime());
         this.collection.insertOne(MongoProductRepository.fromProduct(p));
        return p;
    }

    @Override
    public Product getOne(String id) {
        try {
            ObjectId pId = new ObjectId(id);
            Document product = (Document) this.collection.find(eq("_id", pId)).first();
            return MongoProductRepository.fromDocument(product);
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public Product getByName(String name) {
        Document product = (Document) this.collection.find(eq("name",name)).first();
        return MongoProductRepository.fromDocument(product);
    }

    @Override
    public Product update(Product product) {
        try {
            ObjectId pId = new ObjectId(product.getId());
            product.updated(this.generateDateTime());
            logger.info("updating product : " + product.getId());
            Document doc = MongoProductRepository.fromProduct(product);
            doc.remove("_id");
            UpdateResult res = this.collection.replaceOne(eq("_id", pId), doc);
            logger.info(res.toString());
            if (res.getMatchedCount() > 0) {
                logger.info("matched count greater than zero");
                return product;
            }
            logger.info("matched count not greater than zero returning");
            return null;
        }
        catch(Exception e){
            logger.info("exception thrown : " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean delete(String id) {
        logger.info("id to delete : " + id);
        try {
            DeleteResult res = this.collection.deleteOne(eq("_id", new ObjectId(id)));
            return res.getDeletedCount() > 0;
        }
        catch(Exception e){
            return false;
        }
    }
}

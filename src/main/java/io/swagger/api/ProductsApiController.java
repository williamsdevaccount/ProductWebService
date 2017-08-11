package io.swagger.api;

import io.swagger.model.*;

import io.swagger.annotations.*;
import io.swagger.model.Error;
import io.swagger.repository.MongoProductRepository;
import io.swagger.service.LocalProductService;
import io.swagger.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.validation.constraints.*;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-08-09T18:55:15.380Z")

@Controller
public class ProductsApiController implements ProductsApi {

    @Autowired
     ProductService service;

    public ResponseEntity<Products> productsGet() {
        return this.service.all();
    }

    public ResponseEntity productsIdDelete(@ApiParam(value = "",required=true ) @PathVariable("id") String id) {
        return this.service.deleteProduct(id);
    }

    public ResponseEntity<InlineResponse200Products> productsIdGet(@ApiParam(value = "",required=true ) @PathVariable("id") String id) {
        // do some magic!
        return this.service.getById(id);
    }

    public ResponseEntity<InlineResponse200Products> productsIdPatch(@ApiParam(value = "",required=true ) @PathVariable("id") String id,
        @ApiParam(value = "" ,required=true )  @Valid @RequestBody NewProduct newProduct) {
        return this.service.updateProduct(newProduct,id);
    }

    public ResponseEntity<InlineResponse200Products> productsPost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody NewProduct newProduct) {
        // do some magic!
        return  this.service.createProduct(newProduct);
    }

}

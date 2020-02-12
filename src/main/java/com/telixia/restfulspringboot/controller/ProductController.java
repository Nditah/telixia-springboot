package com.telixia.springboot.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telixia.springboot.domain.Product;
import com.telixia.springboot.repository.ProductRepository;

@RestController
@RequestMapping("/api/products")
public class ProductController extends BaseController{
    @Autowired
    ProductRepository productRepository;

    // GET /products
    @GetMapping("")
    public Iterable<Product> findAll(){
        List<Product> products = new ArrayList<>();
        Iterator<Product> iterator = productRepository.findAll().iterator();
        //iterator.forEachRemaining(products::add);
        iterator.forEachRemaining(product -> {
            product.setImage(getApiUrl() + product.getImage());
            products.add(product);
        });
        Collections.reverse(products);
        return products;
    }

    // GET /products/5
    @GetMapping("/{id}")
    public ResponseEntity<Product> findOne(@PathVariable(value = "id") long id) {
        Product product = productRepository.findOne(id);
        if(product == null) {
            return ResponseEntity.notFound().build();
        }
        product.setImage(getApiUrl() + product.getImage());
        return ResponseEntity.ok().body(product);
    }

    // POST /products
    @PostMapping("")
    public ResponseEntity<Product> create(@Valid @RequestBody Product product){
        product.setImage(product.getImage().replace(getApiUrl(), ""));
        Product newProduct = productRepository.save(product);
        return ResponseEntity.ok(newProduct);
    }

    // PUT /products/5
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable(value = "id") Long id, 
                                          @Valid @RequestBody Product product) {
        Product oldProduct = productRepository.findOne(id);
        if(oldProduct == null) {
            return ResponseEntity.notFound().build();
        }
        oldProduct.setProductName(product.getProductName());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setImage(product.getImage().replace(getApiUrl(), ""));

        Product updProduct = productRepository.save(oldProduct);
        return ResponseEntity.ok(updProduct);
    }

    // DELETE /products/5
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable(value = "id") long id) {
        Product product = productRepository.findOne(id);
        if(product == null) {
            return ResponseEntity.notFound().build();
        }

        productRepository.delete(product);
        return ResponseEntity.ok().build();
    }
}

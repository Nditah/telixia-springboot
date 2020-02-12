package com.telixia.springboot.repository;

import org.springframework.data.repository.CrudRepository;

import com.telixia.springboot.domain.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}

package com.manav.daytoday.repository;

import com.manav.daytoday.db_model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    Optional<Product> findByProductName(String productName);

}

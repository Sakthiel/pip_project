package com.thoughtworks.sample.product.repository;

import com.thoughtworks.sample.version.repository.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT DISTINCT CATEGORY FROM PRODUCT" , nativeQuery = true)
    List<String> findAllCategories();
}

package com.thoughtworks.sample.product;

import com.thoughtworks.sample.product.repository.Product;
import com.thoughtworks.sample.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product add(Product product){
        product.setId(0);
        return productRepository.save(product);
    }
}

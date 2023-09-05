package com.thoughtworks.sample.product;

import com.thoughtworks.sample.product.repository.Product;
import com.thoughtworks.sample.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public String delete(int id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            throw new RuntimeException("Given product not found");
        }
        productRepository.delete(product.get());
        return "Deleted product with id " + id;
    }

    public Product update(int id , Product updatedProductInfo){
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            throw new RuntimeException("Given product not found");
        }
        Product updatedProduct = updateExistingProduct(product.get() ,updatedProductInfo);

        return productRepository.save(updatedProduct);
    }

    private Product updateExistingProduct(Product product , Product updatedProductInfo) {
        product.setProductName(updatedProductInfo.getProductName());
        product.setCategory(updatedProductInfo.getCategory());
        product.setUnitPrice(updatedProductInfo.getUnitPrice());
        return product;
    }
}

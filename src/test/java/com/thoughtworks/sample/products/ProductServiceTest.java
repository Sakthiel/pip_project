package com.thoughtworks.sample.products;

import com.thoughtworks.sample.product.ProductService;
import com.thoughtworks.sample.product.repository.Product;
import com.thoughtworks.sample.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    private ProductService productService;
    private ProductRepository productRepository;
    private Product product;

    @BeforeEach
    public void beforeEach() {
        productRepository = mock(ProductRepository.class);

        productService = new ProductService(productRepository);

        product = new Product("Apple" , "Fruit" , BigDecimal.valueOf(100));

    }
    @Test
    public void should_save_product(){
         Product mockProduct = mock(Product.class);
         when(productRepository.save(product)).thenReturn(mockProduct);

         Product actualProduct = productService.add(product);

         verify(productRepository).save(product);
         assertThat(actualProduct , is(equalTo(mockProduct)));

    }

    @Test
    public void should_return_all_products(){
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        when(productRepository.findAll()).thenReturn(productList);

        List<Product> actualList = productService.getAllProducts();

        verify(productRepository).findAll();
        assertThat(actualList , is(equalTo(productList))) ;


    }

    @Test
    public void should_delete_given_product(){
        int id = 1;
       Product mockProduct = mock(Product.class);
        when(productRepository.findById(id)).thenReturn(Optional.of(mockProduct));

        String message = productService.delete(id);

        verify(productRepository).delete(mockProduct);
        assertThat(message , is(equalTo("Deleted product with id " + id)));
    }
    @Test
    public void should_update_product_with_given_id(){
        int id = 1;
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        Product updatedProduct = new Product("banana" , "Fruit" , BigDecimal.valueOf(100));
        productService.update(id , updatedProduct);

        verify(productRepository).findById(id);
        verify(productRepository).save(product);
    }


}

package com.thoughtworks.sample.products;

import com.thoughtworks.sample.product.ProductService;
import com.thoughtworks.sample.product.repository.Product;
import com.thoughtworks.sample.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
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

    }
}

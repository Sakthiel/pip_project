package com.thoughtworks.sample.product.view;

import com.thoughtworks.sample.product.ProductService;
import com.thoughtworks.sample.product.repository.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
@PostMapping
@ResponseStatus(code = HttpStatus.CREATED)
    public Product add(@Valid @RequestBody Product product){
            return productService.add(product);
    }

    @GetMapping()
    @ResponseStatus(code = HttpStatus.OK)
    public List<Product> products(){
        return productService.getAllProducts();
    }
@DeleteMapping("/{productId}")
    public String delete(@PathVariable int productId){
        return productService.delete(productId);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Product update(@PathVariable int productId , @RequestBody Product product){
        return productService.update(productId , product);
    }

    @GetMapping("/category")
    @ResponseStatus(code = HttpStatus.OK)
    public List<String> category(){
        return productService.getCategories();
    }
}

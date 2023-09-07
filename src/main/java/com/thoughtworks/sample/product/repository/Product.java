package com.thoughtworks.sample.product.repository;



import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "product name should not be null")
    @Column(name = "name")
    private String productName;
    @NotNull
    private String category;
    @Column(name = "unit_price")
    @DecimalMin(value = "0" , inclusive = true , message = "price should not be less than {value}")
    @NotNull
    private BigDecimal unitPrice;



    public Product() {
    }

    public Product(String productName, String category, BigDecimal unitPrice) {
        this.productName = productName;
        this.category = category;
        this.unitPrice = unitPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}

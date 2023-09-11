package com.thoughtworks.sample.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart , Integer> {
    @Query(value = "select * from cart where user_id = ?1 AND product_id = ?2" , nativeQuery = true)
    Optional<Cart> getCartByUserNameAndProductId(Long userId, Integer productId);


    Optional<Cart> findById(Long Id);
    @Query(value = "select count(*) from cart" , nativeQuery = true)
    Integer findNoOfCartItems();
    @Query(value = "select * from cart where user_id = ?1" , nativeQuery = true)
    List<Cart> findByUserId(Long userId);
    @Modifying
    @Transactional
    @Query(value = "delete from cart where user_id = ?1" , nativeQuery = true)
    void deleteItemsByUserId(Long userId);
}

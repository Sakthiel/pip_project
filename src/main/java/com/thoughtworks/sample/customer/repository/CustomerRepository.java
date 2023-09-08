package com.thoughtworks.sample.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer , Long> {
    @Query(value = "Select * from customer where user_id=?1",nativeQuery=true)
    List<Customer> getDetailsByUserid(Long user_id);
}

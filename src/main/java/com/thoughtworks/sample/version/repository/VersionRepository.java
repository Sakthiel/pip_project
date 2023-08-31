package com.thoughtworks.sample.version.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VersionRepository extends JpaRepository<Version , Integer> {
    @Query(value = "SELECT * FROM VERSION" , nativeQuery = true)
    List<Version> currentVersion();

}

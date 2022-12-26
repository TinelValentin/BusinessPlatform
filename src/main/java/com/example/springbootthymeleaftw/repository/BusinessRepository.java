package com.example.springbootthymeleaftw.repository;

import com.example.springbootthymeleaftw.model.entity.BusinessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<BusinessEntity, Long> {
    @Query(value = "select * from business b where b.id_code like 'INVALID'",
    nativeQuery = true)
    BusinessEntity findNonExistentBusiness();

    @Query(value = "select * from business b where b.approved = false",
            nativeQuery = true)
    Optional<List<BusinessEntity>> findAllNotApprovedBusinesses();
}

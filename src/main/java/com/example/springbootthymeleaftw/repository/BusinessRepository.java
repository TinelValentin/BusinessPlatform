package com.example.springbootthymeleaftw.repository;

import com.example.springbootthymeleaftw.model.entity.BusinessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<BusinessEntity, Long> {
    @Query(value = "select * from business b where b.id_code like 'INVALID'",
    nativeQuery = true)
    BusinessEntity findNonExistentBusiness();
}

package com.example.springbootthymeleaftw.repository;

import com.example.springbootthymeleaftw.model.entity.BusinessEntity;
import com.sun.xml.bind.v2.TODO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
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

    @Query(value = "select distinct  business.company_name  from business where business.business_type like 'BC' ",
            nativeQuery = true)
    Optional<List<String>> findAllBBBusinesses();

    @Query(value = "select * from business b inner join app_user a on a.business_id=b.id where a.username like :username",
            nativeQuery = true)
    Optional<BusinessEntity> findBusinessEntitiesByUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @Query(value = "update business set approved = true where id = :id",
            nativeQuery = true)
    void approveBusinessWithId(@Param("id") Long id);
}

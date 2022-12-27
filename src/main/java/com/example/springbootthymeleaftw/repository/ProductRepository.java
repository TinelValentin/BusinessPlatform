package com.example.springbootthymeleaftw.repository;

import com.example.springbootthymeleaftw.model.entity.BusinessEntity;
import com.example.springbootthymeleaftw.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Query(value = "select p.id,p.description,p.name,p.stock,p.business_id from product p " +
            "inner join business b on p.business_id = b.id " +
            "inner join app_user a on a.business_id = b.id " +
            "where a.username like :username",
            nativeQuery = true)
    Optional<List<ProductEntity>> findAllByUsername(@Param("username") String username);

    //The repository save method is acting really weird so this is the backup
    @Transactional
    @Modifying
    @Query(value = "Insert into product(name,description,stock,business_id) Values(:name , :description ,:stock ,:business_id)",
    nativeQuery = true)
    void insertProduct(@Param("name") String name, @Param("description")
    String description, @Param("stock") int stock, @Param("business_id") Long business_id);
}

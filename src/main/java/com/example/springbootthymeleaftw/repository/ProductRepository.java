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

    Optional<ProductEntity> findProductEntityById(Long id);

    @Query(value = "select p.id,p.description,p.name,p.stock,p.approved,p.business_id from product p " +
            "inner join business b on p.business_id = b.id " +
            "inner join app_user a on a.business_id = b.id " +
            "where a.username like :username and p.approved is NULL",
            nativeQuery = true)
    Optional<List<ProductEntity>> findAllByUsernameApproved(@Param("username") String username);

    @Query(value = "select p.id,p.description,p.name,p.stock,p.approved,p.business_id from product p " +
            "inner join business b on p.business_id = b.id " +
            "inner join app_user a on a.business_id = b.id " +
            "where a.username like :username",
            nativeQuery = true)
    Optional<List<ProductEntity>> findAllByUsername(@Param("username") String username);

    //The repository save method is acting really weird so this is the backup
    @Transactional
    @Modifying
    @Query(value = "Insert into product(name,description,stock,approved,business_id) Values(:name , :description ,:stock, :approved ,:business_id)",
            nativeQuery = true)
    void insertProduct(@Param("name") String name, @Param("description") String description,
                       @Param("stock") int stock, @Param("business_id") Long business_id,
                       @Param("approved") String approved);

    @Modifying
    @Transactional
    @Query(value = "Update product p set stock = :stock where p.id = :id ", nativeQuery = true)
    void updateStock(@Param("stock") int stock, @Param("id") Long id);

    @Query(value = "Select p.stock from product p where p.id = :id ", nativeQuery = true)
    int getStockWithId(@Param("id") Long id);

    @Query(value = "Select * from product p inner join business b " +
            "on p.business_id = b.id " +
            "where business_type like 'BB'; ", nativeQuery = true)
    Optional<List<ProductEntity>> findAllProductsFromBB();

    @Transactional
    @Modifying
    @Query(value = "Update product p set stock = stock- :amount where p.id = :id", nativeQuery = true)
    void sellStocksWithId(@Param("id") Long productId, @Param("amount") int amount);

    @Query(value = "select p.id,p.name,p.description,p.stock,p.business_id from product p " +
            "inner join business b on p.business_id = b.id " +
            "where b.company_name like :companyName and p.name like :productName", nativeQuery = true)
    Optional<List<ProductEntity>> checkStoreHasProduct(@Param("companyName") String companyName, @Param("productName") String productName);


}

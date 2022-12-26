package com.example.springbootthymeleaftw.repository;

import com.example.springbootthymeleaftw.model.entity.RoleEntity;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    @Query(value = "select r.name from role r inner join app_users_roles a\n" +
            "on r.id = a.roles_id \n" +
            "inner join app_user ap\n" +
            "on a.app_user_id = ap.id\n" +
            "where ap.email like :email",
            nativeQuery = true)
    Optional<String> findRoleByEmail(@Param("email") String email);

    @Query(value = "select b.approved from app_user a inner join business b on a.business_id = b.id " +
            "where a.email like :email",
            nativeQuery = true)
    Optional<Boolean> isTheAccountApproved(@Param("email")String email);

}

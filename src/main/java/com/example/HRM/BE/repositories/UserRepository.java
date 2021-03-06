package com.example.HRM.BE.repositories;

import com.example.HRM.BE.entities.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findAllByEmail(String email);

    @Query(
            value = "SELECT * FROM users\n" +
                    "where email like CONCAT('%', :keyword , '%')\n" +
                    "or first_name like CONCAT('%', :keyword ,'%')\n" +
                    "or last_name like CONCAT('%', :keyword ,'%')\n" +
                    "or address like CONCAT('%', :keyword ,'%')",
            nativeQuery = true
    )
    List<UserEntity> findAllUserByKeyWord(@Param("keyword") String keyword);

    @Query(
            value = "SELECT * FROM users\n" +
                    "where email like CONCAT('%', :keyword , '%')\n" +
                    "or first_name like CONCAT('%', :keyword ,'%')\n" +
                    "or last_name like CONCAT('%', :keyword ,'%')\n" +
                    "or address like CONCAT('%', :keyword ,'%')",
            nativeQuery = true
    )
    List<UserEntity> findAllUserByKeywordFollowPageable(@Param("keyword") String keyword, Pageable pageable);

//    @Query(
//            value = "DELETE from requests where user_entity_id = id",
//            nativeQuery = true
//    )
//    List<UserEntity> deleteUserIdInRequestTable(@Param("id") int id);
}

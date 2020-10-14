package com.example.HRM.BE.repositories;

import com.example.HRM.BE.DTO.Request;
import com.example.HRM.BE.entities.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestRepository extends JpaRepository<RequestEntity, Integer> {

    List<RequestEntity> findByUserEntityEmail(String email);

    List<RequestEntity> findByAddress(String keyword);

    List<RequestEntity> findByStatus(String keyword);

    List<RequestEntity> findByReason(String keyword);

    @Query(
            value = "SELECT * from requests\n" +
                    "where address like CONCAT('%', :keyword, '%')\n" +
                    "or status like CONCAT('%', :keyword, '%')\n" +
                    "or reason like CONCAT('%', :keyword, '%')\n",
            nativeQuery = true
    )
    List<RequestEntity> findAllRequestsByKeyword(@Param("keyword") String keyword);
}

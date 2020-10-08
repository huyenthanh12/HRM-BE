package com.example.HRM.BE.repositories;

import com.example.HRM.BE.entities.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<RequestEntity, Integer> {

    List<RequestEntity> findByRequestTypeEntityName(String problemType);

    List<RequestEntity> findByUserEntityEmail(String email);

    List<RequestEntity> findByUserEntityEmailContainingOrStatusOrRequestTypeEntityNameContainingOrReasonContainingOrderByUserEntityEmailAsc(String email, String status, String requestType, String reason);

    List<RequestEntity> findByUserEntityEmailContainingOrStatusOrRequestTypeEntityNameContainingOrReasonContainingOrderByStatusAsc(String email, String status, String requestType, String reason);

    List<RequestEntity> findByUserEntityEmailContainingOrStatusOrRequestTypeEntityNameContainingOrReasonContainingOrderByRequestTypeEntityNameAsc(String email, String status, String requestType, String reason);

    List<RequestEntity> findByUserEntityEmailContainingOrStatusOrRequestTypeEntityNameContainingOrReasonContainingOrderByCreateAtAsc(String email, String status, String requestType, String reason);

    List<RequestEntity> findByOrderByCreateAtAsc();

    List<RequestEntity> findByUserEntityEmailContainingOrStatusOrRequestTypeEntityNameContainingOrReasonContaining(String email, String status, String requestType, String reason);
}

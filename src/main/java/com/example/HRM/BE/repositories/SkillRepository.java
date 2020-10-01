package com.example.HRM.BE.repositories;

import com.example.HRM.BE.entities.SkillEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<SkillEntity, Integer> {
    Optional<SkillEntity> findByName(String name);

    @Query(
            value = "SELECT * FROM skills\n" +
                    "where name like CONCAT('%', :valueSearch , '%')",
            nativeQuery = true
    )
    List<SkillEntity> getSkillFollowValueSearch(@Param("valueSearch") String valueSearch, Pageable pageable);
}
package com.example.HRM.BE.repositories;

import com.example.HRM.BE.entities.DayOffEntity;
import com.example.HRM.BE.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DayOffRepository extends JpaRepository<DayOffEntity, Integer> {

    List<DayOffEntity> findByOrderByStatusAsc();

    List<DayOffEntity> findByUserEntity(UserEntity userEntity);

    List<DayOffEntity> findByUserEntityEmail(String email);

    List<DayOffEntity> findByDayOffTypeEntityId(int id);

    List<DayOffEntity> findByUserEntityId(int id);

    @Query(
            value = "SELECT * from day_off\n" +
                    "where EXTRACT(YEAR from day_start) = ?1 and user_entity_id=?2",
            nativeQuery = true
    )
    List<DayOffEntity> getDayOffByYear(int year, int idUser);
}

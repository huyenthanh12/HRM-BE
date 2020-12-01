package com.example.HRM.BE.services;

import com.example.HRM.BE.DTO.DatePayrollDetail;
import com.example.HRM.BE.DTO.Profile;
import com.example.HRM.BE.converters.DatePayrollDetail.DatePayrollDetailEntityToDatePayrollDetail;
import com.example.HRM.BE.converters.DatePayrollDetail.DatePayrollDetailToDatePayrollDetailEntity;
import com.example.HRM.BE.entities.DatePayrollDetailEntity;
import com.example.HRM.BE.entities.UserEntity;
import com.example.HRM.BE.exceptions.UserException.UserNotFoundException;
import com.example.HRM.BE.repositories.DateDetailPayrollRepository;
import com.example.HRM.BE.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DatePayrollDetailService {

    @Autowired
    DateDetailPayrollRepository dateDetailPayrollRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DatePayrollDetailEntityToDatePayrollDetail datePayrollDetailEntityToDatePayrollDetail;

    @Autowired
    DatePayrollDetailToDatePayrollDetailEntity datePayrollDetailToDatePayrollDetailEntity;

    public List<DatePayrollDetail> getAllDatesPayroll() {
        return datePayrollDetailEntityToDatePayrollDetail.convert(dateDetailPayrollRepository.findAll());
    }

    public List<DatePayrollDetail> getAllDatesPayrollByUserID(int id) {

        List<DatePayrollDetailEntity> datePayrollDetailEntityList = dateDetailPayrollRepository.findByUserEntityId(id);
        List<DatePayrollDetail> datePayrollDetailList = new ArrayList<>();

        for(DatePayrollDetailEntity datePayrollDetailEntity : datePayrollDetailEntityList) {

            DatePayrollDetail datePayrollDetail = datePayrollDetailEntityToDatePayrollDetail.convert(datePayrollDetailEntity);

            datePayrollDetailList.add(datePayrollDetail);
        }

        return datePayrollDetailList;
    }

    public List<DatePayrollDetail> getMyDatesPayrollDetail() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(authentication.getName());

        if (!userEntityOptional.isPresent()) {
            throw new UserNotFoundException();
        }

        return datePayrollDetailEntityToDatePayrollDetail.convert(dateDetailPayrollRepository.findByUserEntityEmail(userEntityOptional.get().getEmail()));
    }

    public DatePayrollDetail addNewDatePayrollFollowUserEmail(DatePayrollDetail datePayrollDetail, String email) {

        datePayrollDetail.setUser(Profile.builder().id(this.userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(email)
        ).getId()
        ).build());

        DatePayrollDetailEntity datePayrollDetailEntity = datePayrollDetailToDatePayrollDetailEntity.convert(datePayrollDetail);

        dateDetailPayrollRepository.save(datePayrollDetailEntity);

        return datePayrollDetailEntityToDatePayrollDetail.convert(datePayrollDetailEntity);
    }
}

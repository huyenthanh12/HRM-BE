package com.example.HRM.BE.services;

import com.example.HRM.BE.DTO.DayOff;
import com.example.HRM.BE.DTO.DayOffType;
import com.example.HRM.BE.DTO.NumberDayOff;
import com.example.HRM.BE.DTO.Profile;
import com.example.HRM.BE.common.CommonMethods;
import com.example.HRM.BE.common.Email;
import com.example.HRM.BE.controllers.EmailController;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.entities.DayOffEntity;
import com.example.HRM.BE.entities.DayOffTypeEntity;
import com.example.HRM.BE.entities.UserEntity;
import com.example.HRM.BE.exceptions.DayOffException.DayOffNotFound;
import com.example.HRM.BE.exceptions.DayOffTypeException.DayOffTypeNotFound;
import com.example.HRM.BE.exceptions.UserException.BadRequestException;
import com.example.HRM.BE.exceptions.UserException.UserNotFoundException;
import com.example.HRM.BE.repositories.DayOffRepository;
import com.example.HRM.BE.repositories.DayOffTypeRepository;
import com.example.HRM.BE.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.example.HRM.BE.common.Constants.*;

@Service
@Slf4j
public class DayOffService {

    @Autowired
    private DayOffRepository dayOffRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Converter<DayOffTypeEntity, DayOffType> dayOffTypeEntityDayOffTypeConverter;

    @Autowired
    private Converter<DayOffEntity, DayOff> dayOffEntityDayOffConverter;

    @Autowired
    private CommonMethods commonMethods;

    @Autowired
    private DayOffTypeRepository dayOffTypeRepository;

    @Autowired
    private Converter<UserEntity, Profile> userEntityProfileConverter;

    @Autowired
    private Converter<DayOff, DayOffEntity> dayOffDayOffEntityConverter;

    @Autowired
    private EmailController emailController;

    @Value("#{'${emailAdmins}'.split(',')}")
    private List<String> emailAdmins;

    public List<DayOff> getAllDaysOff() {
        return dayOffEntityDayOffConverter.convert(dayOffRepository.findAll());
    }

    public List<DayOff> getMyDayOffs() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return dayOffEntityDayOffConverter.convert(dayOffRepository.findByUserEntityEmail(authentication.getName()));
    }

    public DayOff getDayOffFollowID(int id) {

        Optional<DayOffEntity> dayOffEntityOptional = dayOffRepository.findById(id);

        if (!dayOffEntityOptional.isPresent()) {
            throw new DayOffNotFound();
        }
        return dayOffEntityDayOffConverter.convert(dayOffEntityOptional.get());
    }

    public int getUserID() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserEntity userEntity = userRepository.findByEmail(email).get();

        return userEntity.getId();
    }

    public long getNumberDayOffsByUser(int id, int year) {

        Optional<UserEntity> userEntityOptional = userRepository.findById(id);

        if (!userEntityOptional.isPresent()) {
            throw new UserNotFoundException();
        }

        Date startingDate = userEntityOptional.get().getStartingDay();
        if (startingDate == null) {
            throw new BadRequestException("Start bi null");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startingDate);

        int startingYear = calendar.get(Calendar.YEAR);
        if (startingYear > year) {
            throw new BadRequestException("Bad Request Converter!");
        }

        LocalDate startingLocalDate = startingDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.of(year, 12, 31);

        return ChronoUnit.YEARS.between(startingLocalDate, now) + DAY_OFF_BY_RULES;
    }

    public float getNumberDayOffsUsed(int id, int year) {

        Optional<UserEntity> userEntityOptional = userRepository.findById(id);

        if (!userEntityOptional.isPresent()) {
            throw new UserNotFoundException();
        }

        List<DayOffEntity> dayOffList = dayOffRepository.getDayOffByYear(year, id);
        float result = 0;
        for (DayOffEntity dayOffEntity : dayOffList) {
            result += commonMethods.calculateDaysBetweenTwoDate(dayOffEntity.getDayStart(), dayOffEntity.getDayEnd());
        }

        return result;
    }

    public DayOffEntity requestNewDayOff(DayOff dayOff, String emailUser) {

        //number of day off register in request converter
        float numberDayOffs = commonMethods.calculateDaysBetweenTwoDate(dayOff.getDayStart(), dayOff.getDayEnd());
        LocalDate localDateStart = dayOff.getDayStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int yearStart =  localDateStart.getYear();

        //number of day off remaining in this year
        float numberDayOffRemainingThisYear = getNumberDayOffsByUserRemaining(getUserID(), yearStart);

        if (numberDayOffs > numberDayOffRemainingThisYear) {
            throw new BadRequestException("The number of days left is not enough!");
        }

        LocalDate localDateEnd = dayOff.getDayStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int yearEnd = localDateStart.getYear();
        if (yearStart != yearEnd && yearEnd != Calendar.getInstance().get(Calendar.YEAR)) {
            throw new BadRequestException("Please register day off for this year");
        }

        Optional<DayOffTypeEntity> dayOffTypeEntityOptional = dayOffTypeRepository.findById(dayOff.getDayOffType().getId());
        if (!dayOffTypeEntityOptional.isPresent()) {
            throw new DayOffTypeNotFound();
        }

        Calendar calendarStart = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();

        calendarStart.setTime(dayOff.getDayStart());
        calendarEnd.setTime(dayOff.getDayEnd());


        long dateStart = dayOff.getDayStart().getTime();
        long dateEnd = dayOff.getDayEnd().getTime();


        if (dateStart > dateEnd) {
            throw new BadRequestException("Incorrect information");
        }
        Date date = new Date(System.currentTimeMillis());
        dayOff.setCreateAt(date);
        dayOff.setStatus(PENDING);
        dayOff.setUser(Profile.builder().id(
                this.userRepository.findByEmail(emailUser).orElseThrow(
                        () -> new UsernameNotFoundException(emailUser)
                ).getId()
                ).build());

        DayOffEntity dayOffEntity = dayOffDayOffEntityConverter.convert(dayOff);

        Email email = new Email();
        email.setSendToEmail(emailAdmins);
        email.setSubject(SUBJECT_DAY_OFF);

        String[] titles = {"Day off by email", "Day off type", "Create At", "Day start", "Day end", "Description"};
        String[] content = {dayOffEntity.getUserEntity().getEmail(), dayOffEntity.getDayOffTypeEntity().getName(),
                            dayOffEntity.getCreateAt().toString(), dayOffEntity.getDayStart().toString(),
                            dayOffEntity.getDayEnd().toString(), dayOffEntity.getDescription()};

        email.setText(commonMethods.formatContentEmail(titles, content, POINT_PAGE_MANAGEMENT_DAY_OFF, POINT_CONTENT_MANAGEMENT_DAY_OFF));
        emailController.sendEmail(email);

        return dayOffRepository.save(dayOffEntity);
    }


    private float getNumberDayOffsByUserRemaining(int id, int year) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);

        if (!userEntityOptional.isPresent()) {
            throw new UserNotFoundException();
        }

        return getNumberDayOffsByUser(id, year) - getNumberDayOffsUsed(id, year);
    }

    public NumberDayOff getNumberDayOff(int id, int year) {

        NumberDayOff numberDayOff = new NumberDayOff();

        numberDayOff.setUsed(getNumberDayOffsUsed(id, year));
        numberDayOff.setRemaining(getNumberDayOffsByUserRemaining(id, year));

        return numberDayOff;
    }

    public void deleteDayOff(int id) {

        Optional<DayOffEntity> dayOffEntityOptional = dayOffRepository.findById(id);

        if (!dayOffEntityOptional.isPresent()) {
            throw new DayOffNotFound();
        }
        dayOffRepository.delete(dayOffEntityOptional.get());
    }

    public DayOffEntity acceptDayOff(int id) {

        Optional<DayOffEntity> dayOffEntityOptional = dayOffRepository.findById(id);

        if (!dayOffEntityOptional.isPresent()) {
            throw new DayOffNotFound();
        }

        if (!dayOffEntityOptional.get().getStatus().equals(PENDING)) {
            throw new BadRequestException("This request has resolved");
        }
        dayOffEntityOptional.get().setStatus(APPROVED);

        Email email = new Email();
        email.setSendToEmail(emailAdmins);
        email.setSubject(RESPONSE_DAY_OFF);
        email.setText("Request for " + dayOffEntityOptional.get().getDescription() + " is accepted!");
        emailController.sendEmail(email);

        return dayOffRepository.save(dayOffEntityOptional.get());
    }

    public DayOffEntity rejectDayOff(int id) {

        Optional<DayOffEntity> dayOffEntityOptional = dayOffRepository.findById(id);

        if (!dayOffEntityOptional.isPresent()) {
            throw new DayOffNotFound();
        }

        if (!dayOffEntityOptional.get().getStatus().equals(PENDING)) {
            throw new BadRequestException("This request has resolved");
        }
        dayOffEntityOptional.get().setStatus(REJECTED);

        Email email = new Email();
        email.setSendToEmail(emailAdmins);
        email.setSubject(RESPONSE_DAY_OFF);
        email.setText("Request for " + dayOffEntityOptional.get().getDescription() + " is rejected!");
        emailController.sendEmail(email);

        return dayOffRepository.save(dayOffEntityOptional.get());
    }

    public List<DayOff> getListDayOffUsed(Integer id, Integer year) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (!userEntity.isPresent()) {
            throw new UserNotFoundException();
        }
        if (year == null) {
            return dayOffEntityDayOffConverter.convert(dayOffRepository.findByUserEntity(userEntity.get()));
        }
        List<DayOffEntity> dayOffs = dayOffRepository.getDayOffByYear(year, id);
        return dayOffEntityDayOffConverter.convert(dayOffs);
    }
}

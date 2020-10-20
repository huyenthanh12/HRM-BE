package com.example.HRM.BE.common;

import com.example.HRM.BE.services.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CommonMethods {

    @Autowired
    private HolidayService holidayService;

    public float calculateDaysBetweenTwoDate(Date date1, Date date2) {

        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        calendar1.setTime(date1);
        calendar2.setTime(date2);

        float numberOfDays = 0;

        int year = calendar1.get(Calendar.YEAR);
        List<Date> dates = new ArrayList<>();

        dates = holidayService.getHolidayThisYear(calendar1.get(Calendar.YEAR));
        while (calendar1.before(calendar2)) {
            if ((Calendar.SATURDAY != calendar1.get(Calendar.DAY_OF_WEEK))
                && (Calendar.SUNDAY != calendar1.get(Calendar.DAY_OF_WEEK))
                    && isContainsHolidays(dates, calendar1)) {

                numberOfDays++;
            }
            calendar1.add(Calendar.DATE, 1);
        }

        if (calendar1.get(Calendar.HOUR_OF_DAY) == 12 && calendar2.get(Calendar.HOUR_OF_DAY) == 12
            && calendar1.get(Calendar.DAY_OF_MONTH) != calendar2.get(Calendar.DAY_OF_MONTH)) {

            numberOfDays--;
        } else if (calendar1.get(Calendar.HOUR_OF_DAY) == 8 && calendar2.get(Calendar.HOUR_OF_DAY) == 12
                || calendar1.get(Calendar.HOUR_OF_DAY) == 12 && calendar2.get(Calendar.HOUR_OF_DAY) == 18) {

            numberOfDays = numberOfDays - 0.5f;
        }
        return numberOfDays;
    }

    private boolean isContainsHolidays(List<Date> dates, Calendar calendar1) {

        for (Date date : dates) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            if (calendar.get(Calendar.YEAR) == calendar1.get(Calendar.YEAR)
                    && calendar.get(Calendar.DAY_OF_MONTH) == calendar1.get(Calendar.DAY_OF_MONTH)) {
                return false;
            }
        }

        return true;
    }

    public List<Date> getListDateBetweenTwoDates(Date date1, Date date2) {

        List<Date> dates =  new ArrayList<>();
        Calendar calendar = new GregorianCalendar();

        calendar.setTime(date1);
        while (calendar.getTime().before(date2)) {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        dates.add(date2);

        return dates;
    }

    public static String formatContentEmail(String []titles, String []contents, String point, String pointContent) {

        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < titles.length; ++i) {
            stringBuffer.append("<b>" + titles[i] + ":</b>");
            stringBuffer.append("<span>" + contents[i] + "</span><br>");
        }
        stringBuffer.append("<a href="+ point +" target=_blank>"+ pointContent +"</a>");

        return stringBuffer.toString();
    }
}

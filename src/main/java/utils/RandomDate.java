package utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class RandomDate {

    private String date;


    public String generateRandomDate() {
        GregorianCalendar calendar = new GregorianCalendar();
        int year = 2023;
        calendar.set(Calendar.YEAR, year);

        int dayOfYear = randBetween(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));

        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);

        return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
                + calendar.get(Calendar.DAY_OF_MONTH);
    }


    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

}

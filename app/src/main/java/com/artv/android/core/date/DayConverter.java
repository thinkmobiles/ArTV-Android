package com.artv.android.core.date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by
 * mRogach on 21.08.2015.
 */
public final class DayConverter {

    public Day getDayOfWeek(final int _currentDay) {

        switch (_currentDay) {
            case 1:
                return Day.SUNDAY;
            case 2:
                return Day.MONDAY;
            case 3:
                return Day.TUESDAY;
            case 4:
                return Day.WEDNESDAY;
            case 5:
                return Day.THURSDAY;
            case 6:
                return Day.FRIDAY;
            case 7:
                return Day.SATURDAY;
        }
        return null;
    }

    public List<Day> getDays(final String _playDays) {
        List<Day> days = new ArrayList<>();
        for (int i = 0; i < _playDays.length(); i++) {
            if (_playDays.charAt(i) == '1') {
                days.add(getDayOfWeek(i));
            }
        }
        return days;
    }

    public Day getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
       return getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
    }
}

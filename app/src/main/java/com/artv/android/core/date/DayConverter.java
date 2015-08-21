package com.artv.android.core.date;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by
 * mRogach on 21.08.2015.
 */
public final class DayConverter {

    public Day getDayOfWeek(final int _currentDay) {

        switch (_currentDay) {
            case 0:
                return Day.SUNDAY;
            case 1:
                return Day.MONDAY;
            case 2:
                return Day.TUESDAY;
            case 3:
                return Day.WEDNESDAY;
            case 4:
                return Day.THURSDAY;
            case 5:
                return Day.FRIDAY;
            case 6:
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

    public final Day getClosestDayFrom(final Day _currentDay, final List<Day> _days) {
        if (_days.size() == 1) return _days.get(0);

        final Day maxDay = Collections.max(_days);
        final int compare =_currentDay.compareTo(maxDay);
        if (compare >= 0) {
            return Collections.min(_days);
        } else {
            return getClosestBiggestDayFrom(_currentDay, _days);
        }
    }

    /**
     * Returns closest biggest day from current day.
     * @param _currentDay current day.
     * @param _days must not be empty.
     * @return closest biggest day.
     */
    public final Day getClosestBiggestDayFrom(final Day _currentDay, final List<Day> _days) {
        for (final Day day : _days) {
            if (day.compareTo(_currentDay) > 0) return day;
        }

        throw new RuntimeException("Days must not be empty");
    }

    public final long getTimeToDay(final Day _day) {
        return -1;
    }
}

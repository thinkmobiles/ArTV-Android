package com.artv.android.core.date;

import junit.framework.Assert;

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

    /**
     * Returns next playing day after current day.
     *
     * @param _currentDay current day.
     * @param _days       playing days. List must not be empty, must not contains current day,
     *                    should contain 2 or more elements.
     * @return next closest day.
     */
    public final Day getNextPlayingDay(Day _currentDay, final List<Day> _days) {
        Assert.assertTrue("List must not be empty", !_days.isEmpty());
        Assert.assertTrue("List must not contains current day", !_days.contains(_currentDay));
        Assert.assertTrue("List should contain 2 or more elements", _days.size() >= 2);

        while (!_days.contains(_currentDay)) {
            _currentDay = _currentDay.getNext();
        }

        return _currentDay;
    }

    public final long getMillisToNextDay() {
        final Calendar calendar = Calendar.getInstance();
        final long currMillis = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        final long nextMillis = calendar.getTimeInMillis();
        return nextMillis - currMillis;
    }

    public final long getMillisInDay() {
        return 1000 * 60 * 60 * 24;
    }

    public final long getMillisInHour() {
        return 1000 * 60 * 60;
    }
}

package com.artv.android.core.date;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by
 * mRogach on 21.08.2015.
 */
public final class DayConverter {

    private Calendar mCalendar;

    public final void setCalendar(final Calendar _calendar) {
        mCalendar = _calendar;
    }

    public final Calendar getCalendar() {
        return mCalendar != null ? mCalendar : Calendar.getInstance();
    }

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

    public final Day getCurrentDay() {
        final Calendar calendar = getCalendar();
        return getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK) - 1);
    }

    public final List<Day> getDays(final String _playDays) {
        final List<Day> days = new ArrayList<>();
        for (int i = 0; i < _playDays.length(); i++) {
            if (_playDays.charAt(i) == '1') {
                days.add(getDayOfWeek(i));
            }
        }
        return days;
    }

    /**
     * Returns next playing day after current day.
     *
     * @param _currentDay current day.
     * @param _days       playing days. List must not be empty, must not contains current day.
     * @return next closest day.
     */
    public final Day getPlayDay(Day _currentDay, final List<Day> _days) {
        Assert.assertTrue("List must not be empty", !_days.isEmpty());
        Assert.assertTrue("List must not contains current day", !_days.contains(_currentDay));
        if (_days.size() == 1) return _days.get(0);

        while (!_days.contains(_currentDay)) {
            _currentDay = _currentDay.getNext();
        }

        return _currentDay;
    }

    public final long getMillisBetweenDays(Day _first, final Day _last) {
        Assert.assertFalse("Days must no be equals", _first.equals(_last));

        long millis = getMillisToNextDay();
        _first = _first.getNext();
        while (!_first.equals(_last)) {
            millis += getMillisInDay();
            _first = _first.getNext();
        }

        return millis;
    }

    public final long getMillisToNextDay() {
        final Calendar calendar = (Calendar) getCalendar().clone();
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

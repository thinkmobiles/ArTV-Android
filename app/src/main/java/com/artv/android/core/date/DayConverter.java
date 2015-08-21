package com.artv.android.core.date;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by
 * mRogach on 21.08.2015.
 */
public final class DayConverter {

    public static Day getCurrentDay(final int _currentDay) {

        switch (_currentDay) {
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
            case 7:
                return Day.SUNDAY;
        }
        return null;
    }

    public static List<Day> getDaysToPlay(final String _playDays) {
        List<Day> days = new ArrayList<>();
        for (int i = 0; i < _playDays.length(); i++) {
            if (_playDays.charAt(i) == '1') {
                days.add(getCurrentDay(i));
            }
        }
        return days;
    }
}

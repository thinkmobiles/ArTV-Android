package com.artv.android.core.date;

/**
 * Created by
 * mRogach on 21.08.2015.
 */
public enum Day {

    SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
    THURSDAY, FRIDAY, SATURDAY;

    private static final Day[] mDays = Day.values();

    public final Day getNext() {
        return mDays[(ordinal() + 1) % mDays.length];
    }

}


package com.artv.android.core.date;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ZOG on 8/11/2015.
 */
public final class DateWorker {

    //f.e. 24/07/2015 1:17
    private static final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy k:mm", Locale.getDefault());

    public final String getCurrentFormattedDate() {
        return format.format(new Date());
    }

}

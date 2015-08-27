package com.artv.android.app.playback;

import com.artv.android.core.date.Day;
import com.artv.android.core.date.DayConverter;
import com.artv.android.core.model.Campaign;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by
 * mRogach on 17.08.2015.
 */
public class PlayModeManager {
    private DayConverter mDayConverter;

    public void setDayConverter(DayConverter mDayConverter) {
        this.mDayConverter = mDayConverter;
    }

    private Date getDateFromString(final String _date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");
        try {
            return dateFormat.parse(_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Date getTimeFromString(final String _date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);
        try {
            return dateFormat.parse(_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Date getCurrentDate() {
        return new Date();
    }


    private boolean hasPlayCampaignInCurrentDate(final List<Campaign> _campaigns) {
        long startDate = 0, endDate = 0, currentDate;
        currentDate = getCurrentDate().getTime();
        Date dateStart, dateEnd;
        if (!_campaigns.isEmpty()) {
            for (Campaign campaign : _campaigns) {
                if (campaign.startDate != null) {
                    dateStart = getDateFromString(campaign.startDate);
                    if (dateStart != null) {
                        startDate = dateStart.getTime();
                    }
                }
                if (campaign.endDate != null) {
                    dateEnd = getDateFromString(campaign.endDate);
                    if (dateEnd != null) {
                        endDate = dateEnd.getTime();
                    }
                }
                if (startDate != 0 && endDate != 0 && startDate <= currentDate && currentDate <= endDate) {
                    return true;
                } else if (startDate != 0 && startDate <= currentDate) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasPlayCampaignInCurentDay(final List<Campaign> _campaigns) {
        List<Day> daysToDplay;
        if (mDayConverter != null) {
            Day day = mDayConverter.getCurrentDay();
            if (!_campaigns.isEmpty()) {
                for (Campaign campaign : _campaigns) {
                    if (!campaign.playDay.isEmpty()) {
                        daysToDplay = mDayConverter.getDays(campaign.playDay);
                        if (!daysToDplay.isEmpty() && daysToDplay.contains(day)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public long getTimeInMills(final Date _time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(_time);
        return calendar.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000 + calendar.get(Calendar.MINUTE) * 60 * 1000;
    }

    public int campainToPlay(final List<Campaign> _campaigns) {
        if (hasPlayCampaignInCurrentDate(_campaigns) && hasPlayCampaignInCurentDay(_campaigns)) {
            return 1;
        }
        return 0;
    }


}

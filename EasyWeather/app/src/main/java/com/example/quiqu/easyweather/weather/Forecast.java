package com.example.quiqu.easyweather.weather;

import com.example.quiqu.easyweather.R;

/**
 * Created by quiqu on 11/10/2016.
 *
 *
 *
 **/

public class Forecast {
    private Current mCurrent;
    private Hour[] mHourlyforecast;
    private Day[] mDailyForecast;

    public Current getCurrent() {
        return mCurrent;
    }

    public void setCurrent(Current mCurrent) {
        this.mCurrent = mCurrent;
    }

    public Hour[] getHourlyforecast() {
        return mHourlyforecast;
    }

    public void setHourlyforecast(Hour[] mHourlyforecast) {
        this.mHourlyforecast = mHourlyforecast;
    }

    public Day[] getDailyForecast() {
        return mDailyForecast;
    }

    public void setDailyForecast(Day[] mDailyForecast) {
        this.mDailyForecast = mDailyForecast;
    }

    // to be used by each additional class that will call it (for example the Day class
    // in order for this to be run, we need to return the icon id from the calling class(Day as an example) by passing in
    // a parameter that is already defoined on all classes from the JSON response, in this case it is mIcon
    public static int getIconId(String iconString) {
        // clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night.
        int iconId = R.drawable.clear_day;

        if (iconString.equals("clear-day")) {
            iconId = R.drawable.clear_day;
        }
        else if (iconString.equals("clear-night")) {
            iconId = R.drawable.clear_night;
        }
        else if (iconString.equals("rain")) {
            iconId = R.drawable.rain;
        }
        else if (iconString.equals("snow")) {
            iconId = R.drawable.snow;
        }
        else if (iconString.equals("sleet")) {
            iconId = R.drawable.sleet;
        }
        else if (iconString.equals("wind")) {
            iconId = R.drawable.wind;
        }
        else if (iconString.equals("fog")) {
            iconId = R.drawable.fog;
        }
        else if (iconString.equals("cloudy")) {
            iconId = R.drawable.cloudy;
        }
        else if (iconString.equals("partly-cloudy-day")) {
            iconId = R.drawable.partly_cloudy;
        }
        else if (iconString.equals("partly-cloudy-night")) {
            iconId = R.drawable.cloudy_night;
        }

        return iconId;
    }
}

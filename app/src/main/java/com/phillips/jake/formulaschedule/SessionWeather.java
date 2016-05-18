package com.phillips.jake.formulaschedule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Jake on 6/12/2015.
 */
public class SessionWeather {

    String country, sessionType, weatherType, date;
    int sessionStartTime;
    double temp, rain;

    public SessionWeather(String country, int sessionStartTime, String sessionType){
        this.country = country;
        this.sessionStartTime = sessionStartTime;
        this.sessionType = sessionType;

        setDate();
    }

    private void setDate(){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(sessionStartTime * 1000L);
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        date = monthNames[cal.get(Calendar.MONTH)] + " " + cal.get(Calendar.DAY_OF_MONTH);
    }
}

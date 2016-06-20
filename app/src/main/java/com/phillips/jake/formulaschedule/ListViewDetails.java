package com.phillips.jake.formulaschedule;

import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Jake on 6/10/2015.
 */
public class ListViewDetails {
    public String country;
    public int fp1, fp2, fp3, qualy, race;
    public long id;
    public String dates = "Implement this";

    public ListViewDetails(String country, int fp1, int fp2, int fp3, int qualy, int race, long id){
        this.country = country;
        this.fp1 = fp1;
        this.fp2 = fp2;
        this.fp3 = fp3;
        this.qualy = qualy;
        this.race = race;
        this.id = id;

        setDates();
    }

    private void setDates(){
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String startMonth, endMonth, startDay, endDay;
        Calendar local = new GregorianCalendar(TimeZone.getDefault());
        local.setTimeInMillis(fp1 * 1000L);

        startMonth = monthNames[local.get(Calendar.MONTH)];
        startDay = "" + local.get(Calendar.DAY_OF_MONTH);

        local.setTimeInMillis(race * 1000L);

        endMonth = monthNames[local.get(Calendar.MONTH)];
        endDay = "" + local.get(Calendar.DAY_OF_MONTH);

        if(startMonth.equals(endMonth)){
            dates = startMonth + " " + startDay + "-" + endDay;
        }
        else{
            dates = startMonth + " " + startDay + " - " + endMonth + " " + endDay;
        }
    }


    /*
    * returns a number based on which session is next to occur
    * 1 = fp1
    * 2 = fp2
    * 3 = fp3
    * 4 = qualy
    * 5 = race
    * 0 = all sessions have occured
    * */
    public int nextSession(){
        Calendar current = Calendar.getInstance();
        Calendar test = Calendar.getInstance();

        test.setTimeInMillis(fp1 * 1000L);
        if(current.compareTo(test) < 0){
            return 1;
        }

        test.setTimeInMillis(fp2 * 1000L);
        if(current.compareTo(test) < 0){
            return 2;
        }

        test.setTimeInMillis(fp3 * 1000L);
        if(current.compareTo(test) < 0){
            return 3;
        }

        test.setTimeInMillis(qualy * 1000L);
        if(current.compareTo(test) < 0){
            return 4;
        }

        test.setTimeInMillis(race * 1000L);
        if(current.compareTo(test) < 0){
            return 5;
        }

        return 0;
    }
}



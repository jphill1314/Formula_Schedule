package com.phillips.jake.formulaschedule;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by jphil on 6/23/2016.
 */
public class SeasonScheduleAdapter extends CursorAdapter {

    public SeasonScheduleAdapter(Context context, Cursor c, int flags){
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor c, ViewGroup parent){
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_schedule, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor){
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        String country = cursor.getString(SeasonScheduleFragment.COL_COUNTRY);
        viewHolder.tvCountry.setText(country);

        int start = cursor.getInt(SeasonScheduleFragment.COL_FP1);
        int end = cursor.getInt(SeasonScheduleFragment.COL_RACE);
        String dates = getEventDates(start, end);
        viewHolder.tvDates.setText(dates);

        switch(country){
            case "Australia": viewHolder.flagIcon.setImageResource(R.drawable.australia);
                break;
            case "Austria": viewHolder.flagIcon.setImageResource(R.drawable.austria);
                break;
            case "Europe": viewHolder.flagIcon.setImageResource(R.drawable.azerbaijan);
                break;
            case "Bahrain": viewHolder.flagIcon.setImageResource(R.drawable.bahrain);
                break;
            case "Belgium": viewHolder.flagIcon.setImageResource(R.drawable.belgium);
                break;
            case "Brazil": viewHolder.flagIcon.setImageResource(R.drawable.brazil);
                break;
            case "Canada": viewHolder.flagIcon.setImageResource(R.drawable.canada);
                break;
            case "China": viewHolder.flagIcon.setImageResource(R.drawable.china);
                break;
            case "England": viewHolder.flagIcon.setImageResource(R.drawable.england);
                break;
            case "Germany": viewHolder.flagIcon.setImageResource(R.drawable.germany);
                break;
            case "Hungary": viewHolder.flagIcon.setImageResource(R.drawable.hungary);
                break;
            case "Italy": viewHolder.flagIcon.setImageResource(R.drawable.italy);
                break;
            case "Japan": viewHolder.flagIcon.setImageResource(R.drawable.japan);
                break;
            case "Malaysia": viewHolder.flagIcon.setImageResource(R.drawable.malaysia);
                break;
            case "Mexico": viewHolder.flagIcon.setImageResource(R.drawable.mexico);
                break;
            case "Monaco": viewHolder.flagIcon.setImageResource(R.drawable.monaco);
                break;
            case "Russia": viewHolder.flagIcon.setImageResource(R.drawable.russia);
                break;
            case "Singapore": viewHolder.flagIcon.setImageResource(R.drawable.singapore);
                break;
            case "Spain": viewHolder.flagIcon.setImageResource(R.drawable.spain);
                break;
            case "Abu Dhabi": viewHolder.flagIcon.setImageResource(R.drawable.uae);
                break;
            case "United States": viewHolder.flagIcon.setImageResource(R.drawable.usa);
        }

    }

    public String getEventDates(int start, int end){
        String dates;

        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String startMonth, endMonth, startDay, endDay;
        Calendar local = new GregorianCalendar(TimeZone.getDefault());
        local.setTimeInMillis(start * 1000L);

        startMonth = monthNames[local.get(Calendar.MONTH)];
        startDay = "" + local.get(Calendar.DAY_OF_MONTH);

        local.setTimeInMillis(end * 1000L);

        endMonth = monthNames[local.get(Calendar.MONTH)];
        endDay = "" + local.get(Calendar.DAY_OF_MONTH);

        if(startMonth.equals(endMonth)){
            dates = startMonth + " " + startDay + "-" + endDay;
        }
        else{
            dates = startMonth + " " + startDay + " - " + endMonth + " " + endDay;
        }

        return dates;
    }

    public static class ViewHolder{
        public final ImageView flagIcon;
        public final TextView tvCountry;
        public final TextView tvDates;

        public ViewHolder(View view){
            flagIcon = (ImageView) view.findViewById(R.id.country_flag);
            tvCountry = (TextView) view.findViewById(R.id.country_name);
            tvDates = (TextView) view.findViewById(R.id.dates);
        }
    }
}

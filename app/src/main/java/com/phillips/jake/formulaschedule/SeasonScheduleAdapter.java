package com.phillips.jake.formulaschedule;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

        String dates = Integer.toString(cursor.getInt(SeasonScheduleFragment.COL_RACE));
        viewHolder.tvDates.setText(dates);
        

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

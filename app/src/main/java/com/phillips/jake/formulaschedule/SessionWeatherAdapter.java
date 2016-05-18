package com.phillips.jake.formulaschedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Jake on 6/12/2015.
 */
public class SessionWeatherAdapter extends ArrayAdapter<SessionWeather> {

    public SessionWeatherAdapter(Context context, ArrayList<SessionWeather> sessionWeather){
        super(context, 0, sessionWeather);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        SessionWeather session = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_race_session, parent, false);
        }

        TextView tvSessionName = (TextView) convertView.findViewById(R.id.session_name);
        tvSessionName.setText(session.sessionType);

        TextView tvDate = (TextView) convertView.findViewById(R.id.session_date);
        tvDate.setText(session.date);

        //TextView tvTemp = (TextView) convertView.findViewById(R.id.temp);
        //tvTemp.setText("" + session.temp);

        //TextView tvPrecip = (TextView) convertView.findViewById(R.id.precip_chance);
        //tvPrecip.setText("" + session.rain);

        TextView tvStart = (TextView) convertView.findViewById(R.id.start_time);
        DateFormat format = new SimpleDateFormat("h:mm a");
        tvStart.setText(format.format(session.sessionStartTime * 1000L));

        return convertView;
    }
}

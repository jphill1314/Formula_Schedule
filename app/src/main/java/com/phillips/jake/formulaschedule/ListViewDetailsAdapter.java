package com.phillips.jake.formulaschedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jake on 6/10/2015.
 */
public class ListViewDetailsAdapter extends ArrayAdapter<ListViewDetails> {
    public ListViewDetailsAdapter(Context context, ArrayList<ListViewDetails> listViewDetails){
        super(context, 0, listViewDetails);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ListViewDetails detail = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_schedule, parent, false);
        }

        TextView tvCountry = (TextView) convertView.findViewById(R.id.country_name);
        TextView tvDates = (TextView) convertView.findViewById((R.id.dates));
        ImageView flag = (ImageView) convertView.findViewById(R.id.country_flag);

        tvCountry.setText(detail.country);
        tvDates.setText(detail.dates);

        switch(detail.country){
            case "Australia": flag.setImageResource(R.drawable.australia);
                break;
            case "Austria": flag.setImageResource(R.drawable.austria);
                break;
            case "Europe": flag.setImageResource(R.drawable.azerbaijan);
                break;
            case "Bahrain": flag.setImageResource(R.drawable.bahrain);
                break;
            case "Belgium": flag.setImageResource(R.drawable.belgium);
                break;
            case "Brazil": flag.setImageResource(R.drawable.brazil);
                break;
            case "Canada": flag.setImageResource(R.drawable.canada);
                break;
            case "China": flag.setImageResource(R.drawable.china);
                break;
            case "England": flag.setImageResource(R.drawable.england);
                break;
            case "Germany": flag.setImageResource(R.drawable.germany);
                break;
            case "Hungary": flag.setImageResource(R.drawable.hungary);
                break;
            case "Italy": flag.setImageResource(R.drawable.italy);
                break;
            case "Japan": flag.setImageResource(R.drawable.japan);
                break;
            case "Malaysia": flag.setImageResource(R.drawable.malaysia);
                break;
            case "Mexico": flag.setImageResource(R.drawable.mexico);
                break;
            case "Monaco": flag.setImageResource(R.drawable.monaco);
                break;
            case "Russia": flag.setImageResource(R.drawable.russia);
                break;
            case "Singapore": flag.setImageResource(R.drawable.singapore);
                break;
            case "Spain": flag.setImageResource(R.drawable.spain);
                break;
            case "Abu Dhabi": flag.setImageResource(R.drawable.uae);
                break;
            case "United States": flag.setImageResource(R.drawable.usa);
        }




        return convertView;
    }
}
package com.phillips.jake.formulaschedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

        tvCountry.setText(detail.country);
        tvDates.setText(detail.dates);

        return convertView;
    }
}
package com.phillips.jake.formulaschedule;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.phillips.jake.formulaschedule.Data.ScheduleDataSource;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    ListViewDetailsAdapter scheduleAdapter;
    ScheduleDataSource scheduleDataSource;
    ArrayList<ListViewDetails> details;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        scheduleDataSource = new ScheduleDataSource(getActivity());

        try {
            scheduleDataSource.open();
        }
        catch (SQLException e){}

        details = scheduleDataSource.getAllComments();

        if(details.size() == 0){
            scheduleDataSource.createDatabaseItem("Australia", 1426213800, 1426228200, 1426305600, 1426316400, 1426399200);
            scheduleDataSource.createDatabaseItem("Maylasia", 1427421600, 1427436000, 1427522400, 1427533200, 1427612400);
            scheduleDataSource.createDatabaseItem("China", 1428631200, 1428645600, 1428724800, 1428735600, 1428818400);
            scheduleDataSource.createDatabaseItem("Bahrain", 1429268400, 1429282800, 1429358400, 1429369200, 1429455600);
            scheduleDataSource.createDatabaseItem("Spain", 1431072000, 1431086400, 1431162000, 1431172800, 1431259200);
            scheduleDataSource.createDatabaseItem("Monaco", 1432195200, 1432209600, 1432371600, 1432382400, 1432468800);
            scheduleDataSource.createDatabaseItem("Canada", 1433512800, 1433527200, 1433599200, 1433610000, 1433700000);
            scheduleDataSource.createDatabaseItem("Austria", 1434700800, 1434715200, 1434790800, 1434801600, 1434888000);
            scheduleDataSource.createDatabaseItem("England", 1435914000, 1435928400, 1436000400, 1436011200, 1436097600);
            scheduleDataSource.createDatabaseItem("Hungary", 1437724800, 1437739200, 1437814800, 1437825600, 1437912000);
            scheduleDataSource.createDatabaseItem("Belgium", 1440144000, 1440158400, 1440234000, 1440244800, 1440331200);
            scheduleDataSource.createDatabaseItem("Italy", 1441353600, 1441368000, 1441443600, 1441454400, 1441540800);
            scheduleDataSource.createDatabaseItem("Singapore", 1442570400, 1442583000, 1442656800, 1442667600, 1442750400);
            scheduleDataSource.createDatabaseItem("Japan", 1443142800, 1443157200, 1443236400, 1443247200, 1443330000);
            scheduleDataSource.createDatabaseItem("Russia", 1444374000, 1444388400, 1444467600, 1444478400, 1444561200);
            scheduleDataSource.createDatabaseItem("United States", 1445612400, 1445626800, 1445698800, 1445709600, 1445799600);
            scheduleDataSource.createDatabaseItem("Mexico", 1446217200, 1446231600, 1446303600, 1446314400, 1446400800);
            scheduleDataSource.createDatabaseItem("Brazil", 1447419600, 1447434000, 1447509600, 1447520400, 1447606800);
            scheduleDataSource.createDatabaseItem("Abu Dhabi", 1448614800, 1448625600, 1448704800, 1448712000, 1448798401);
        }

        scheduleAdapter = new ListViewDetailsAdapter(getActivity(), details);

        View rootView = inflater.inflate(R.layout.fragment_main_schedule, container, false);

        final ListView listView = (ListView) rootView.findViewById(R.id.listview_schedule);
        listView.setAdapter(scheduleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l){
                Intent i = new Intent(getActivity(), MoreInfoActivity.class);

                TextView tvName = (TextView)view.findViewById(R.id.country_name);
                String name = tvName.getText().toString();

                for(ListViewDetails item : details){
                    if(name.equals(item.country)){
                        i.putExtra(MoreInfoActivity.COUNTRY_KEY, item.country);
                        i.putExtra(MoreInfoActivity.FP1_KEY, item.fp1);
                        i.putExtra(MoreInfoActivity.FP2_KEY, item.fp2);
                        i.putExtra(MoreInfoActivity.FP3_KEY, item.fp3);
                        i.putExtra(MoreInfoActivity.QUALY_KEY, item.qualy);
                        i.putExtra(MoreInfoActivity.RACE_KEY, item.race);
                    }
                }


                startActivity(i);
            }
        });

        populateTopView(rootView);

        return rootView;
    }

    private void populateTopView(View view){
        Calendar current = Calendar.getInstance();
        Calendar race = Calendar.getInstance();

        for(ListViewDetails item : details){
            race.setTimeInMillis(item.race * 1000L);
            if(current.compareTo(race) < 0){
                TextView country = (TextView) view.findViewById(R.id.race_country);
                country.setText(item.country);

                DateFormat format = new SimpleDateFormat("h:mm a");
                TextView raceTime = (TextView) view.findViewById(R.id.race_time);
                raceTime.setText(format.format(item.race * 1000L));

                TextView qualyTime = (TextView) view.findViewById(R.id.qualy_time);
                qualyTime.setText(format.format(item.qualy * 1000L));

                TextView fp1Time = (TextView) view.findViewById(R.id.fp1_time);
                fp1Time.setText(format.format(item.fp1 * 1000L));

                TextView fp2Time = (TextView) view.findViewById(R.id.fp2_time);
                fp2Time.setText(format.format(item.fp2 * 1000L));

                TextView fp3Time = (TextView) view.findViewById(R.id.fp3_time);
                fp3Time.setText(format.format(item.fp3 * 1000L));

                return;
            }
        }

    }
}

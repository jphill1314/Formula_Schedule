package com.phillips.jake.formulaschedule;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
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
    long timeToNextRace = 0;


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
            // All times are converted from UK time
            scheduleDataSource.createDatabaseItem("Australia", 1458264600, 1458279000, 1458356400, 1458367200, 1458450000);
            scheduleDataSource.createDatabaseItem("Bahrain", 1459512000, 1459526400, 1459602000, 1459612800, 1459699200);
            scheduleDataSource.createDatabaseItem("China", 1460689200, 1460703600, 1460782800, 1460793600, 1460876400);
            scheduleDataSource.createDatabaseItem("Russia", 1461916800, 1461931200, 1462010400, 1462021200, 1462107600);
            scheduleDataSource.createDatabaseItem("Spain", 1463130000, 1463144400, 1463220000, 1463230800, 1463317200);
            scheduleDataSource.createDatabaseItem("Monaco", 1464253200, 1464267600, 1464429600, 1464440400, 1464526800);
            scheduleDataSource.createDatabaseItem("Canada", 1465570800, 1465585200, 1465657200, 1465668000, 1465758000);
            scheduleDataSource.createDatabaseItem("Europe", 1466157600, 1466172000, 1466247600, 1466258400, 1466344800);
            scheduleDataSource.createDatabaseItem("Austria", 1467363600, 1467378000, 1467453600, 1467464400, 1467550800);
            scheduleDataSource.createDatabaseItem("England", 1467972000, 1467986400, 1468058400, 1468069200, 1468155600);
            scheduleDataSource.createDatabaseItem("Hungary", 1469178000, 1469192400, 1469268000, 1469278800, 1469365200);
            scheduleDataSource.createDatabaseItem("Germany", 1469782800, 1469797200, 1469872800, 1469883600, 1469970000);
            scheduleDataSource.createDatabaseItem("Belgium", 1472202000, 1472216400, 1472292000, 1472302800, 1472389200);
            scheduleDataSource.createDatabaseItem("Italy", 1472806800, 1472821200, 1472896800, 1472907600, 1472994000);
            scheduleDataSource.createDatabaseItem("Singapore", 1474023600, 1474036200, 1474110000, 1474120800, 1474203600);
            scheduleDataSource.createDatabaseItem("Malaysia", 1475204400, 1475218800, 1475305200, 1475316000, 1475395200);
            scheduleDataSource.createDatabaseItem("Japan", 1475805600, 1475820000, 1475899200, 1475910000, 1475992800);
            scheduleDataSource.createDatabaseItem("United States", 1477065600, 1477080000, 1477152000, 1477162800, 1477252800);
            scheduleDataSource.createDatabaseItem("Mexico", 1477670400, 1477684800, 1477756800, 1477767600, 1477854000);
            scheduleDataSource.createDatabaseItem("Brazil", 1478865600, 1478880000, 1478955600, 1478966400, 1479052800);
            scheduleDataSource.createDatabaseItem("Abu Dhabi", 1480064400, 1480078800, 1480154400, 1480165200, 1480251600);
            details = scheduleDataSource.getAllComments();
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

        final TextView tvDays = (TextView) rootView.findViewById(R.id.num_days_to_next_session);
        final TextView tvHours = (TextView) rootView.findViewById(R.id.num_hours_to_next_session);
        final TextView tvMins = (TextView) rootView.findViewById(R.id.num_min_to_next_session);
        final TextView tvSecs = (TextView) rootView.findViewById(R.id.num_seconds_to_next_session);
        new CountDownTimer(timeToNextRace, 1000){
            public void onTick(long millSecondsLeft){
                int days = (int) (millSecondsLeft / (1000 * 3600 * 24));
                millSecondsLeft -= days * 24 * 3600 * 1000;
                int hours = (int) (millSecondsLeft / (1000 * 3600));
                millSecondsLeft -= hours * 3600 * 1000;
                int minutes = (int) (millSecondsLeft / (1000 * 60));
                millSecondsLeft -= minutes * 60 * 1000;
                int seconds = (int) (millSecondsLeft / (1000));

                tvDays.setText(days + "");
                tvHours.setText(hours + "");
                tvMins.setText(minutes + "");
                tvSecs.setText(seconds + "");
            }

            public void onFinish(){

            }
        }.start();

        return rootView;
    }

    private void populateTopView(View view){
        Calendar current = Calendar.getInstance();
        Calendar race = Calendar.getInstance();

        for(ListViewDetails item : details){
            race.setTimeInMillis(item.race * 1000L);
            if(current.compareTo(race) < 0){
                TextView country = (TextView) view.findViewById(R.id.next_race_country);
                country.setText(item.country);

                TextView session = (TextView) view.findViewById(R.id.next_session_name);

                switch (item.nextSession()){
                    case 1:
                        session.setText("FP1");
                        timeToNextRace = (item.fp1 * 1000L) - current.getTimeInMillis();
                        break;
                    case 2:
                        session.setText("FP2");
                        timeToNextRace = (item.fp2 * 1000L) - current.getTimeInMillis();
                        break;
                    case 3:
                        session.setText("FP3");
                        timeToNextRace = (item.fp3 * 1000L) - current.getTimeInMillis();
                        break;
                    case 4:
                        session.setText("Qualifying");
                        timeToNextRace = (item.qualy * 1000L) - current.getTimeInMillis();
                        break;
                    case 5:
                        session.setText("Race");
                        timeToNextRace = (item.race * 1000L) - current.getTimeInMillis();
                        break;
                    default:
                        timeToNextRace = 0;
                        session.setText("No Upcoming sessions");
                }
                return;
            }
        }

    }
}

package com.phillips.jake.formulaschedule;

import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phillips.jake.formulaschedule.Data.ScheduleContract;

import java.util.Calendar;

/**
 * Created by jphil on 6/27/2016.
 */
public class CountdownFragment extends Fragment {

    private static final String[] SCHEDULE_COLUMNS = {
            ScheduleContract.ScheduleEntry.TABLE_NAME + "." + ScheduleContract.ScheduleEntry._ID,
            ScheduleContract.ScheduleEntry.COLUMN_COUNTRY,
            ScheduleContract.ScheduleEntry.COLUMN_FP1,
            ScheduleContract.ScheduleEntry.COLUMN_FP2,
            ScheduleContract.ScheduleEntry.COLUMN_FP3,
            ScheduleContract.ScheduleEntry.COLUMN_QUALY,
            ScheduleContract.ScheduleEntry.COLUMN_RACE
    };

    static final int COL_ID = 0;
    static final int COL_COUNTRY = 1;
    static final int COL_FP1 = 2;
    static final int COL_FP2 = 3;
    static final int COL_FP3 = 4;
    static final int COL_QUALY = 5;
    static final int COL_RACE = 6;

    private String nextRaceCountry;
    private String nextSession;
    private int timeToNextRace;

    public CountdownFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_session_countdown, container, false);

        populateTopView(rootView);

        return rootView;
    }

    private void populateTopView(View rootView){
        Cursor cursor = getContext().getContentResolver().query(
                ScheduleContract.ScheduleEntry.CONTENT_URI,
                SCHEDULE_COLUMNS,
                null,
                null,
                null
        );

        findNextSession(cursor);

        TextView tvCountry = (TextView) rootView.findViewById(R.id.next_race_country);
        TextView tvSession = (TextView) rootView.findViewById(R.id.next_session_name);

        final TextView tvHours = (TextView) rootView.findViewById(R.id.num_hours_to_next_session);
        final TextView tvMins = (TextView) rootView.findViewById(R.id.num_min_to_next_session);
        final TextView tvSecs = (TextView) rootView.findViewById(R.id.num_seconds_to_next_session);
        final TextView tvDays = (TextView) rootView.findViewById(R.id.num_days_to_next_session);

        tvCountry.setText(nextRaceCountry);
        tvSession.setText(nextSession);

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
    }

    private void findNextSession(Cursor details){
        Calendar current = Calendar.getInstance();
        Calendar race = Calendar.getInstance();

        details.moveToFirst();

        do{
            race.setTimeInMillis(details.getInt(COL_RACE) * 1000L);
            if(current.compareTo(race) < 0){
                nextRaceCountry = details.getString(COL_COUNTRY);

                race.setTimeInMillis(details.getInt(COL_FP1) * 1000L);
                if(current.compareTo(race) < 0){
                    timeToNextRace = (int)(race.getTimeInMillis() - current.getTimeInMillis());
                    nextSession = "FP1";
                    return;
                }

                race.setTimeInMillis(details.getInt(COL_FP2) * 1000L);
                if(current.compareTo(race) < 0){
                    timeToNextRace = (int)(race.getTimeInMillis() - current.getTimeInMillis());
                    nextSession = "FP2";
                    return;
                }

                race.setTimeInMillis(details.getInt(COL_FP3) * 1000L);
                if(current.compareTo(race) < 0){
                    timeToNextRace = (int)(race.getTimeInMillis() - current.getTimeInMillis());
                    nextSession = "FP3";
                    return;
                }

                race.setTimeInMillis(details.getInt(COL_QUALY) * 1000L);
                if(current.compareTo(race) < 0){
                    timeToNextRace = (int)(race.getTimeInMillis() - current.getTimeInMillis());
                    nextSession = "Qualifying";
                    return;
                }

                race.setTimeInMillis(details.getInt(COL_RACE) * 1000L);
                timeToNextRace = (int) (race.getTimeInMillis() - current.getTimeInMillis());
                nextSession = "Race";
                return;
            }


        }while(details.moveToNext());
    }
}

package com.phillips.jake.formulaschedule;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.phillips.jake.formulaschedule.Data.ScheduleContract;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class MoreInfoActivity extends ActionBarActivity {

    MoreInfoActivityFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        if(savedInstanceState == null){
            fragment = new MoreInfoActivityFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_more_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class MoreInfoActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

        private static final int SCHEDULE_LOADER = 0;

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

        String countryFromUri;

        private TextView mCountry, mRace, mFP1, mFP2, mFP3, mQualy;

        private static final int DETAIL_LOADER = 0;

        public MoreInfoActivityFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_more_info, container, false);
            mCountry = (TextView) view.findViewById(R.id.host_country);
            mFP1 = (TextView) view.findViewById(R.id.fp1_textView);
            mFP2 = (TextView) view.findViewById(R.id.fp2_textView);
            mFP3 = (TextView) view.findViewById(R.id.fp3_textView);
            mQualy = (TextView) view.findViewById(R.id.qualy_textView);
            mRace = (TextView) view.findViewById(R.id.race_textView);

            return view;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState){
            getLoaderManager().initLoader(DETAIL_LOADER, null, this);
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args){
            Intent intent = getActivity().getIntent();
            if (intent == null || intent.getData() == null) {
                return null;
            }

            Log.d("MoreInfoFrag ", intent.getData().toString());
            Log.d("MoroInfoFrag ", ScheduleContract.ScheduleEntry.getCountryFromUri(intent.getData()));

            Uri uri = ScheduleContract.ScheduleEntry.CONTENT_URI;
            countryFromUri = ScheduleContract.ScheduleEntry.getCountryFromUri(intent.getData());

            return new CursorLoader(
                    getActivity(),
                    uri,
                    SCHEDULE_COLUMNS,
                    null,
                    null,
                    null
            );
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data){
            Log.d("MoreInfoFrag ", data.toString());
            data.moveToFirst();

            while(!data.getString(COL_COUNTRY).equals(countryFromUri)){
                data.moveToNext();
            }

            String country = data.getString(COL_COUNTRY);
            mCountry.setText(country);

            String raceTime = eventDateAndTime(data.getInt(COL_RACE));
            String qualyTime = eventDateAndTime(data.getInt(COL_QUALY));
            String fp1Time = eventDateAndTime(data.getInt(COL_FP1));
            String fp2Time = eventDateAndTime(data.getInt(COL_FP2));
            String fp3Time = eventDateAndTime(data.getInt(COL_FP3));

            mRace.setText("Race: " + raceTime);
            mQualy.setText("Qualifying: " + qualyTime);
            mFP1.setText("Free Practice 1: " + fp1Time);
            mFP2.setText("Free Practice 2: " + fp2Time);
            mFP3.setText("Free Practice 3: " + fp3Time);

        }

        private String eventDateAndTime(int event){
            String dateAndTime;

            DateFormat format = new SimpleDateFormat("h:mm a");
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(event * 1000L);
            String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

            dateAndTime = monthNames[cal.get(Calendar.MONTH)] + " " + cal.get(Calendar.DAY_OF_MONTH) + ", " + format.format(event * 1000L);

            return dateAndTime;
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader){}
    }
}

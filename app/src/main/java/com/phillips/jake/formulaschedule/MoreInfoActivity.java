package com.phillips.jake.formulaschedule;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MoreInfoActivity extends ActionBarActivity {

    public static final String COUNTRY_KEY = "country";
    public static final String FP1_KEY = "fp1_key";
    public static final String FP2_KEY = "fp2_key";
    public static final String FP3_KEY = "fp3_key";
    public static final String QUALY_KEY = "qualy_key";
    public static final String RACE_KEY = "race_key";

    String country;
    int fp1, fp2, fp3, qualy, race;

    ArrayList<SessionWeather> sessions;

    MoreInfoActivityFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        if(savedInstanceState == null){
            fragment = new MoreInfoActivityFragment();
            fragment.setArguments(getIntent().getExtras());

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

    public static class MoreInfoActivityFragment extends Fragment {

        String country;
        int fp1, fp2, fp3, qualy, race;
        ArrayList<SessionWeather> sessions;

        public MoreInfoActivityFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            sessions = new ArrayList<>();
            Bundle i = getArguments();


            if(i != null) {
                country = i.getString(COUNTRY_KEY);
                fp1 = i.getInt(FP1_KEY);
                fp2 = i.getInt(FP2_KEY);
                fp3 = i.getInt(FP3_KEY);
                qualy = i.getInt(QUALY_KEY);
                race = i.getInt(RACE_KEY);

                sessions.add(new SessionWeather(country, race, "Race"));
                sessions.add(new SessionWeather(country, qualy, "Qualifying"));
                sessions.add(new SessionWeather(country, fp3, "Practice 3"));
                sessions.add(new SessionWeather(country, fp2, "Practice 2"));
                sessions.add(new SessionWeather(country, fp1, "Practice 1"));
            }

            SessionWeatherAdapter adapter = new SessionWeatherAdapter(getActivity(), sessions);
            View view = inflater.inflate(R.layout.fragment_more_info, container, false);
            final ListView listView = (ListView) view.findViewById(R.id.listview_race_info);
            listView.setAdapter(adapter);

            TextView tvCountry = (TextView) view.findViewById(R.id.host_country);
            tvCountry.setText(country);

            return view;
        }
    }
}

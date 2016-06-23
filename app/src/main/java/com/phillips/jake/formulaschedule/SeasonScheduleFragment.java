package com.phillips.jake.formulaschedule;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.phillips.jake.formulaschedule.Data.ScheduleContract;

/**
 * Created by jphil on 6/23/2016.
 */
public class SeasonScheduleFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private SeasonScheduleAdapter mScheduleAdapter;
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

    public SeasonScheduleFragment(){}

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        mScheduleAdapter = new SeasonScheduleAdapter(getActivity(), null, 0);

        View rootView = inflater.inflate(R.layout.fragment_main_schedule, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_schedule);
        listView.setAdapter(mScheduleAdapter);

        //createSchedule();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView adapterView, View view, int position, long l){
                Cursor c = (Cursor) adapterView.getItemAtPosition(position);
                if(c != null){
                    Intent intent = new Intent(getActivity(), MoreInfoActivity.class)
                            .setData(ScheduleContract.ScheduleEntry
                                    .buildCountryScheduleUri(c.getString(COL_COUNTRY)));
                    startActivity(intent);
                }
            }
        });

        return  rootView;
    }

    @Override
    public void onActivityCreated(Bundle saveInstanceState){
        getLoaderManager().initLoader(SCHEDULE_LOADER, null, this);
        super.onActivityCreated(saveInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle){
        String sortOrder = ScheduleContract.ScheduleEntry.COLUMN_FP1 + " ASC";
        Uri seasonUri = ScheduleContract.ScheduleEntry.CONTENT_URI;

        return new CursorLoader(getActivity(),
                seasonUri,
                SCHEDULE_COLUMNS,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor){
        mScheduleAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader){
        mScheduleAdapter.swapCursor(null);
    }

    private void createSchedule(){
        ContentValues[] scheduleValues = new ContentValues[21];
        String[] county = new String[]{
                "Australia", "Bahrain", "China",
                "Russia", "Spain", "Monaco", "Canada",
                "Europe", "Austria", "England", "Hungary",
                "Germany", "Belgium", "Italy", "Singapore",
                "Malaysia", "Japan", "United States",
                "Mexico", "Brazil", "Abu Dhabi"
        };

        int[] fp1 = new int[]{ 1458264600, 1459512000, 1460689200,
                1461916800, 1463130000, 1464253200, 1465570800, 1466157600,
                1467363600, 1467972000, 1469178000, 1469782800, 1472202000,
                1472806800, 1474023600, 1475204400, 1475805600, 1477065600,
                1477670400, 1478865600, 1480064400
        };

        int[] fp2 = new int[]{ 1458279000, 1459526400, 1460703600,
                1461931200, 1463144400, 1464267600, 1465585200, 1466172000,
                1467378000, 1467986400, 1469192400, 1469797200, 1472216400,
                1472821200, 1474036200, 1475218800, 1475820000, 1477080000,
                1477684800, 1478880000, 1480078800
        };

        int[] fp3 = new int[]{ 1458356400, 1459602000, 1460782800,
                1462010400, 1463220000, 1464429600, 1465657200, 1466247600,
                1467453600, 1468058400, 1469268000, 1469872800, 1472292000,
                1472896800, 1474110000, 1475305200, 1475899200, 1477152000,
                1477756800, 1478955600, 1480154400
        };

        int[] qualy = new int[]{ 1458367200, 1459612800, 1460793600,
                1462021200, 1463230800, 1464440400, 1465668000, 1466258400,
                1467464400, 1468069200, 1469278800, 1469883600, 1472302800,
                1472907600, 1474120800, 1475316000, 1475910000, 1477162800,
                1477767600, 1478966400, 1480165200
        };

        int[] race = new int[]{ 1458450000, 1459699200, 1460876400,
                1462107600, 1463317200, 1464526800, 1465758000, 1466344800,
                1467550800, 1468155600, 1469365200, 1469970000, 1472389200,
                1472994000, 1474203600, 1475395200, 1475992800, 1477252800,
                1477854000, 1479052800, 1480251600
        };

        for(int x = 0; x < 21; x++){
            scheduleValues[x] = new ContentValues();
            scheduleValues[x].put(ScheduleContract.ScheduleEntry.COLUMN_COUNTRY, county[x]);
            scheduleValues[x].put(ScheduleContract.ScheduleEntry.COLUMN_FP1, fp1[x]);
            scheduleValues[x].put(ScheduleContract.ScheduleEntry.COLUMN_FP2, fp2[x]);
            scheduleValues[x].put(ScheduleContract.ScheduleEntry.COLUMN_FP3, fp3[x]);
            scheduleValues[x].put(ScheduleContract.ScheduleEntry.COLUMN_QUALY, qualy[x]);
            scheduleValues[x].put(ScheduleContract.ScheduleEntry.COLUMN_RACE, race[x]);
        }

        getContext().getContentResolver().bulkInsert(ScheduleContract.ScheduleEntry.CONTENT_URI, scheduleValues);
    }

}
